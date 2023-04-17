package com.hcmute.backendtoeicapp.controller;

import com.hcmute.backendtoeicapp.repositories.ToeicFullTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/toeic-app")
public class ToeicAppController {
    @Autowired
    private ToeicFullTestRepository toeicFullTestRepository;

    @GetMapping("list-full-test")
    private Map<String, Object> listFullTest() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", toeicFullTestRepository.findAll());
        result.put("message","Lấy dữ liệu thành công");
        return result;
    }
}
