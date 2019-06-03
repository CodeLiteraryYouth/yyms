package com.leanin.controller;

import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.FollowFormStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 13:58.
 */
@RestController
@RequestMapping("/followFormStat")
public class FollowFormStatController {

    @Autowired
    FollowFormStatService followFormStatService;

    /**
     *
     * @param formQuest
     * @return
     */
    @PostMapping("/add")
    public DataOutResponse add(@RequestBody FormQuest formQuest){
        return followFormStatService.add(formQuest);
    }
}
