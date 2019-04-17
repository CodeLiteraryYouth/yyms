package com.leanin.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leanin.domain.dto.AdminUserDto;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.response.ReturnFomart;
import com.leanin.domain.vo.AdminUserVo;
import com.leanin.domain.vo.LoginRequestVo;
import com.leanin.domain.vo.RoleInfoVo;
import com.leanin.domain.vo.UserRoleVo;
import com.leanin.oauth.mapper.UserMapper;
import com.leanin.oauth.mapper.UserRoleMapper;
import com.leanin.oauth.service.UserService;
import com.leanin.utils.LyOauth2Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${csms.accessKeyId}")
    String accessKeyId;

    @Value("${csms.accessKeySecret}")
    String accessKeySecret;

    @Value("${csms.templateUrl}")
    String requestUrl;

    @Value("${csms.signId}")
    long signId;

    @Value("${csms.templateId}")
    long templateId;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private LyOauth2Util.UserJwt getUser(HttpServletRequest request) {
        LyOauth2Util lyOauth2Util = new LyOauth2Util();
        LyOauth2Util.UserJwt userJwt = lyOauth2Util.getUserJwtFromHeader(request);
        return userJwt;
    }

    @Override
    @Transactional
    public DataOutResponse addUser(AdminUserVo adminUserVo, HttpServletRequest request) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        AdminUserDto user = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        if (user != null) {
            return ReturnFomart.retParam(300, "用户已存在请勿重复添加");
        }
        adminUserVo.setPassword(bCryptPasswordEncoder.encode(adminUserVo.getPassword()));
        userMapper.addUser(adminUserVo);
        AdminUserDto userByWorkNum = userMapper.findUserByWorkNum(adminUserVo.getWorkNum());
        List<Long> roleIds = adminUserVo.getRoleIds();

        LyOauth2Util.UserJwt userJwt = getUser(request);
        if (roleIds.size() > 0) {
            UserRoleVo userRoleVo = new UserRoleVo();
            for (Long roleId : roleIds) {
//                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(userByWorkNum.getAdminId(), roleId);
//                if (userRoleVo == null) {
//                    userRoleVo = new UserRoleVo();
                userRoleVo.setId(null);
                userRoleVo.setUserId(userByWorkNum.getAdminId());
                userRoleVo.setRoleId(roleId);
                userRoleVo.setCreateTime(new Date());
                userRoleVo.setCreator(userJwt.getId());
                userRoleMapper.addUserRole(userRoleVo);
//                } else {
//                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
//                }

            }
        }
        return ReturnFomart.retParam(200, "用户添加成功");
    }

    @Override
    @Transactional
    public DataOutResponse uptUserStatus(Long adminUserId, Integer status) {
        AdminUserVo adminUserVo = userMapper.findUserId(adminUserId);
        if (adminUserVo == null) {
            return ReturnFomart.retParam(400, "用户不存在");
        }
        adminUserVo.setAdminState(status);
        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(200, "修改成功");
    }

    @Override
    @Transactional
    public DataOutResponse updateUser(AdminUserVo adminUserVo, HttpServletRequest request) {
        AdminUserDto user = userMapper.findUserDtoId(adminUserVo.getAdminId());
        if (user == null) {
            return ReturnFomart.retParam(1010, "用户不存在");
        }
        List<Long> roleIds = adminUserVo.getRoleIds();
        List<RoleInfoVo> roleList = user.getRoleList();
        LyOauth2Util.UserJwt userJwt = getUser(request);
        if (roleIds.size() > 0) {//判断是否有角色
            for (Long roleId : roleIds) {
                UserRoleVo userRoleVo = userRoleMapper.findByUidAndRid(adminUserVo.getAdminId(), roleId);
                if (userRoleVo == null) {
                    userRoleVo = new UserRoleVo();
                    userRoleVo.setId(null);
                    userRoleVo.setUserId(adminUserVo.getAdminId());
                    userRoleVo.setRoleId(roleId);
                    userRoleVo.setCreateTime(new Date());
                    userRoleVo.setCreator(userJwt.getId());
                    userRoleMapper.addUserRole(userRoleVo);
                }
//                    ExceptionCast.cast(AuthCode.ROLE_REPETITION);
            }
        }
        for (RoleInfoVo roleInfoDto : roleList) {
            if (!roleIds.contains(roleInfoDto.getRoleId())) {
                userRoleMapper.deleteByUidAndRid(user.getAdminId(), roleInfoDto.getRoleId());
            }
        }

        userMapper.updateUser(adminUserVo);
        return ReturnFomart.retParam(200, "用户修改成功");
    }

    @Override
    public DataOutResponse findUserById(Long adminId) {
        AdminUserVo adminUserVo = userMapper.findUserId(adminId);
        return ReturnFomart.retParam(200, adminUserVo);
    }

    @Override
    public DataOutResponse findUserPage(int currentPage, int pageSize, String userName, String workNum) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        PageHelper.startPage(currentPage, pageSize);
        Page<AdminUserVo> page = (Page<AdminUserVo>) userMapper.findUserPage(userName, workNum);

        Map dataMap = new HashMap();
        dataMap.put("totalCount", page.getTotal());
        dataMap.put("list", page.getResult());
        return ReturnFomart.retParam(200, dataMap);
    }

    @Override
    public String findUserName(Long adminId) {
        return userMapper.findUserName(adminId);
    }

    @Override
    public DataOutResponse findAllUser() {
        return ReturnFomart.retParam(200, userMapper.findAllUser());
    }

    @Override
    public AdminUserDto findUserByWorkNum(String username) {
        return userMapper.findUserByWorkNum(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updatePassWord(LoginRequestVo loginRequestVo) {
        AdminUserVo user = userMapper.findUserId(loginRequestVo.getUserId());
        if (user == null) {
            return ReturnFomart.retParam(1010, "用户未注册");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(loginRequestVo.getPassword(), user.getPassword());
        if (!matches) {//原密码不正确
            return ReturnFomart.retParam(1016, "原密码错误，请输入正确的原密码");
        }
        String newPassword = bCryptPasswordEncoder.encode(loginRequestVo.getNewPassword());
        user.setPassword(newPassword);
        userMapper.updateUserPassword(newPassword, user.getAdminId());
        return ReturnFomart.retParam(200, "密码修改成功");
    }


    @Override
    public DataOutResponse sendCheckCode(String phone) {
//        Map check = (Map) redisTemplate.boundHashOps("check").get(phone);
        String checkStr = stringRedisTemplate.opsForValue().get(phone);
        Map check = new HashMap();
        if (checkStr != null) {//获取缓存中的验证码
            check = JSON.parseObject(checkStr, Map.class);
            Integer count = Integer.parseInt(check.get("count").toString());
            if (count > 3) {
                log.info("异常手机号：{}",phone);
                return ReturnFomart.retParam(3302, "请不要频繁获取验证码");
            }
            check.put("count", count + 1);
        } else {
            check.put("count", 1);
        }

        Random random = new Random();
        String checkCode = "";
        for (int i = 0; i < 6; i++) {
            int anInt = random.nextInt(10);
            checkCode = checkCode + anInt;
        }
        boolean flag = sendmsg(checkCode, phone);
        if (!flag) {
            return ReturnFomart.retParam(3301, "验证码发送失败");
        }
        check.put("code", checkCode);
        //存对应手机号发送的验证码到缓存中 设置过期时间 为60秒
        stringRedisTemplate.opsForValue().set(phone, JSON.toJSONString(check), 300, TimeUnit.SECONDS);
        return ReturnFomart.retParam(200, "验证码发送成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataOutResponse updatePhone(Long userId, String phone, String checkCode) {
        String string = stringRedisTemplate.opsForValue().get(phone);
        if (string == null ){
            return ReturnFomart.retParam(3303,"验证码已失效，请重新获取验证码");
        }
        Map map = JSON.parseObject(string, Map.class);
        String code = (String) map.get("code");
        if (!code.equals(checkCode)){
            return ReturnFomart.retParam(3304,"请输入正确的验证码");
        }
        AdminUserVo user = userMapper.findUserId(userId);
        if (user == null){
            return ReturnFomart.retParam(1010,"用户未注册");
        }
        user.setPhone(phone);
        userMapper.updateUser(user);
        return ReturnFomart.retParam(200,"手机号修改成功");
    }


    private boolean sendmsg(String content, String mobile) {
        try {

//            HttpClient httpClient = new HttpClient(requestUrl);
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("Account", accessKeyId);
            dataMap.put("Pwd", accessKeySecret);
            dataMap.put("Content", content);
            dataMap.put("Mobile", mobile);
            dataMap.put("SignId", signId);
            dataMap.put("TemplateId", templateId);
            //请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity entity = new HttpEntity(dataMap, headers);
            ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(requestUrl, entity, String.class);
            String result = stringResponseEntity.getBody();
//            httpClient.setHttps(false);
//            httpClient.setParameter(dataMap);
//            httpClient.post();

//            String result = httpClient.getContent();
            Map map = JSON.parseObject(result, Map.class);
            String message = (String) map.get("Message");
            System.out.println(map);
            if (message.equals("OK")){
                log.info("验证码发送成功：{}","手机号："+mobile+"验证码："+content);
                return true;
            }else {
                log.info("验证码发送失败：{}","手机号："+mobile+"验证码："+content);
                return false;
            }
        } catch (Exception e) {
            log.error("短信发送的异常：{}",e.getMessage());
            return false;
        }
    }
}
