package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.domain.vo.NoticeInfoVo;
import com.leanin.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value="notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("findNoticeList")
    public DataOutResponse findNoticeList(@RequestParam int page,@RequestParam int pageSize,@RequestParam(required = false) String noticeName) {
        return noticeService.findNoticeList(page, pageSize, noticeName);
    }

    @GetMapping("updateNoticeStatus")
    public DataOutResponse updateNoticeStatus(@RequestParam Long noticeId,@RequestParam int status) {
        return noticeService.updateNoticeStatus(noticeId, status);
    }

    @PostMapping("addNoticeInfo")
    public DataOutResponse addNoticeInfo(@RequestBody NoticeInfoVo noticeInfoVo) {
        return noticeService.addNoticeInfo(noticeInfoVo);
    }

    @GetMapping("findNoticeById")
    public DataOutResponse findNoticeById(@RequestParam Long noticeId) {
        return noticeService.findNoticeById(noticeId);
    }

    @PostMapping("updateNoticeInfo")
    public DataOutResponse updateNoticeInfo(@RequestBody NoticeInfoVo noticeInfoVo) {
        return noticeService.updateNoticeInfo(noticeInfoVo);
    }
}
