package com.hcmute.backendtoeicapp.controller;

import com.hcmute.backendtoeicapp.AppConfiguration;
import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;
import com.hcmute.backendtoeicapp.dto.PracticePartInfoResponse;
import com.hcmute.backendtoeicapp.repositories.ToeicFullTestRepository;
import com.hcmute.backendtoeicapp.services.interfaces.ToeicAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    @Autowired
    private AppConfiguration appConfiguration;

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

    @GetMapping("practice/part/{id}/download")
    public ResponseEntity downloadFile1(
            @PathVariable("id") Integer id
    ) throws IOException {
        final byte[] buffer = this.toeicAppService.downloadPartData(id);
        if (buffer == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=toeic.zip")
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // Contet-Length
                .contentLength(buffer.length) //
                .body(resource);
    }
}
