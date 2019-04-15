package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.NoticeInfoVo;
import com.leanin.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value="notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PreAuthorize("hasAnyAuthority('root','findNotice')")
    @GetMapping("findNoticeList")
    public DataOutResponse findNoticeList(@RequestParam int page,@RequestParam int pageSize,@RequestParam(required = false) String noticeName) {
        return noticeService.findNoticeList(page, pageSize, noticeName);
    }

    @PreAuthorize("hasAnyAuthority('root','delNotice')")
    @GetMapping("updateNoticeStatus")
    public DataOutResponse updateNoticeStatus(@RequestParam Long noticeId,@RequestParam int status) {
        return noticeService.updateNoticeStatus(noticeId, status);
    }

    @PreAuthorize("hasAnyAuthority('root','addNotice')")
    @PostMapping("addNoticeInfo")
    public DataOutResponse addNoticeInfo(@RequestBody NoticeInfoVo noticeInfoVo) {
        return noticeService.addNoticeInfo(noticeInfoVo);
    }

    @PreAuthorize("hasAnyAuthority('root','findNotice')")
    @GetMapping("findNoticeById")
    public DataOutResponse findNoticeById(@RequestParam Long noticeId) {
        return noticeService.findNoticeById(noticeId);
    }

    @PreAuthorize("hasAnyAuthority('root','updateNotice')")
    @PostMapping("updateNoticeInfo")
    public DataOutResponse updateNoticeInfo(@RequestBody NoticeInfoVo noticeInfoVo) {
        return noticeService.updateNoticeInfo(noticeInfoVo);
    }
}
