package com.leanin.domain.response;

import java.util.HashMap;
import java.util.Map;

/**
 * 格式化返回客户端数据格式（json）
 * @author Administrator
 */
public class ReturnFomart {

	private static Map<String,String> messageMap =new HashMap<>();
    //初始化状态码与文字说明
    static {
        messageMap.put("0", "系统异常，请联系管理员");

        messageMap.put("1", "信息不存在");
        messageMap.put("2", "参数不存在或者错误");

        messageMap.put("200", "Success");
        messageMap.put("400", "Bad Request!");
        messageMap.put("401", "NotAuthorization");
        messageMap.put("405", "Method Not Allowed");
        messageMap.put("406", "Not Acceptable");
        messageMap.put("500", "Internal Server Error");

        messageMap.put("1000", "[服务器]运行时异常");
        messageMap.put("1001", "[服务器]空值异常");
        messageMap.put("1002", "[服务器]数据类型转换异常");
        messageMap.put("1003", "[服务器]IO异常");
        messageMap.put("1004", "[服务器]未知方法异常");
        messageMap.put("1005", "[服务器]数组越界异常");
        messageMap.put("1006", "[服务器]网络异常");

        messageMap.put("96", "操作失败，数据为空");
        messageMap.put("4000", "数据重复，请重新输入");
        messageMap.put("1010", "用户未注册");
        messageMap.put("1011", "用户已注册");
        messageMap.put("1012", "用户名或密码错误");
        messageMap.put("1013", "用户帐号冻结");
        messageMap.put("1014", "用户信息编辑失败");
        messageMap.put("1015", "用户信息失效，请重新获取");
        messageMap.put("1016", "原密码错误");

        messageMap.put("2010", "缺少参数或值为空");
        messageMap.put("2011", "数据不存在");
        messageMap.put("2029", "参数不合法");
        messageMap.put("2020", "无效的Token");
        messageMap.put("2021", "无操作权限");
        messageMap.put("2022", "MD5解密失败,密文数据已损坏");
        messageMap.put("2023", "请重新登录");
        messageMap.put("2024", "文件过大，无法上传");
        messageMap.put("2025", "该订单已配送，已经无法退订");
        messageMap.put("2026", "文件不存在");
        
        messageMap.put("3001", "excel文件中的餐次不能为空");
        messageMap.put("3002", "excel文件中的菜品不能为空");
        messageMap.put("3003","数据重复，请勿重复插入");

        messageMap.put("3101","权限存在，请勿重复添加");
        messageMap.put("3102","权限不存在");

        messageMap.put("3201","角色不存在");

        messageMap.put("3301","验证码发送失败");
        messageMap.put("3302","请不要频繁获取验证码");
        messageMap.put("3303","验证码已失效，请重新获取验证码");
        messageMap.put("3304","请输入正确的验证码");
        messageMap.put("3305","患者手机号码不存在");
        messageMap.put("3306","患者手机号码不存在或者手机号码错误");


        messageMap.put("3401","绑定患者失败");
        messageMap.put("3402","计划不存在或者规则不存在或者患者信息已绑定");


        messageMap.put("3501","短信信息不存在");
        messageMap.put("3502","短信信息已被使用不能删除或者禁用");


        messageMap.put("3601","满意度表单已被使用不能删除或者禁用");

        messageMap.put("3701","随访规则已被使用不能删除或者禁用");

        messageMap.put("3801","随访表单已被使用不能删除或者禁用");

        messageMap.put("3901","分类下有随访表单不能删除或者禁用");
        messageMap.put("3902","分类下有宣教表单不能删除或者禁用");
        messageMap.put("3903","分类下有随访规则不能删除或者禁用");
        messageMap.put("3904","分类下有宣教规则不能删除或者禁用");
        messageMap.put("3905","分类下有满意度表单不能删除或者禁用");
        messageMap.put("3906","分类下有短信模板不能删除或者禁用");


        messageMap.put("4001","科室码重复请勿添加相同的科室");
        messageMap.put("5000","病人已关注，不需要再次关注");







    }
    public static DataOutResponse retParam(int status,Object data) {
        DataOutResponse json = new DataOutResponse(status, messageMap.get(String.valueOf(status)), data);
        return json;
    }
}
