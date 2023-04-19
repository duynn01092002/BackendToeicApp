package com.hcmute.backendtoeicapp.controller;

import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;
import com.hcmute.backendtoeicapp.dto.PracticePartInfoResponse;
import com.hcmute.backendtoeicapp.repositories.ToeicFullTestRepository;
import com.hcmute.backendtoeicapp.services.interfaces.ToeicAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/toeic-app")
public class ToeicAppController {
    @Autowired
    private ToeicAppService toeicAppService;
    @Autowired
    private ToeicFullTestRepository toeicFullTestRepository;

    @GetMapping("list-full-test")
    private Map<String, Object> listFullTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", toeicFullTestRepository.findAll());
        result.put("message","Lấy dữ liệu thành công");
        return result;
    }
    @GetMapping("practice/{id}")
    private BaseResponse getToeicPartInfoByPartNumberId(@PathVariable("id") Integer id) {
        BaseResponse response = toeicAppService.getListPracticePartInfoByPartId(id);
        return response;
    }
    @GetMapping("practice/part/{id}")
    private BaseResponse getQuestionGroupAndQuestionByPartId(@PathVariable("id") Integer id) {
        BaseResponse response = toeicAppService.getListQuestionGroupAndQuestionByPartId(id);
        return response;
    }
}
