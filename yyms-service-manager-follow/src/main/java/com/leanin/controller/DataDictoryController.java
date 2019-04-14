package com.leanin.controller;

import com.leanin.domain.response.DataOutResponse;
import com.leanin.service.DataDictoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataDictoryController {

	@Autowired
	private DataDictoryService dataDictoryService;

	@PreAuthorize("hasAnyAuthority('root','findDisease')")
	@GetMapping("findDiseaseList")
	public DataOutResponse findDiseaseList(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required=false) String diseaseName) {
		return dataDictoryService.findDiseaseList(page, pageSize, diseaseName);
	}
}
