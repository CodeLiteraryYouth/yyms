package com.leanin.controller;

import com.leanin.domain.request.FormQuest;
import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.SatisfyFormStatService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CPJ.
 * @date 2019/6/3.
 * @time 16:55.
 */
@RestController
@RequestMapping("/satisfyFormStat")
public class SatisfyFormStatController {

    @Autowired
    SatisfyFormStatService satisfyFormStatService;

    /**
     *
     * @param formQuest
     * @return
     */
    @PostMapping("/add")
    public DataOutResponse add(@RequestBody FormQuest formQuest){
        return satisfyFormStatService.add(formQuest);
    }

}
