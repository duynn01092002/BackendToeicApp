package com.hcmute.backendtoeicapp.controller;

import com.google.gson.Gson;
import com.hcmute.backendtoeicapp.entities.*;
import com.hcmute.backendtoeicapp.model.*;
import com.hcmute.backendtoeicapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/toeic")
public class ToeicController {
    @Autowired
    private ToeicAnswerChoiceRepository toeicAnswerChoiceRepository;
    @Autowired
    private ToeicFullTestRepository toeicFullTestRepository;
    @Autowired
    private ToeicItemContentRepository toeicItemContentRepository;
    @Autowired
    private ToeicPartRepository toeicPartRepository;
    @Autowired
    private ToeicQuestionRepository toeicQuestionRepository;
    @Autowired
    private ToeicQuestionGroupRepository toeicQuestionGroupRepository;
    @PostMapping("upgrade-database")
    @Transactional
    public Map<String, Object> upgradeDatabase(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] buffer = file.getBytes();
        String json = new String(buffer, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        List<ToeicFullTest> tests = List.of(gson.fromJson(json, ToeicFullTest[].class));

        for (ToeicFullTest toeicFullTest : tests) {
            ToeicFullTestEntity toeicFullTestEntity = new ToeicFullTestEntity(toeicFullTest);
            toeicFullTestRepository.save(toeicFullTestEntity);

            for (ToeicPart toeicPart : toeicFullTest.getParts()) {
                ToeicPartEntity toeicPartEntity = new ToeicPartEntity(toeicPart);
                toeicPartEntity.setToeicFullTestEntity(toeicFullTestEntity);
                toeicPartRepository.save(toeicPartEntity);

                for (ToeicQuestionGroup toeicQuestionGroup : toeicPart.getToeicQuestionGroups()) {
                    ToeicQuestionGroupEntity toeicQuestionGroupEntity = new ToeicQuestionGroupEntity(toeicQuestionGroup);
                    toeicQuestionGroupEntity.setToeicPartEntity(toeicPartEntity);
                    toeicQuestionGroupRepository.save(toeicQuestionGroupEntity);

                    for (ToeicQuestion toeicQuestion : toeicQuestionGroup.getQuestions()) {
                        ToeicQuestionEntity toeicQuestionEntity = new ToeicQuestionEntity(toeicQuestion);
                        toeicQuestionEntity.setToeicQuestionGroupEntity(toeicQuestionGroupEntity);
                        toeicQuestionRepository.save(toeicQuestionEntity);

                        for (ToeicAnswerChoice toeicAnswerChoice : toeicQuestion.getChoices()) {
                            ToeicAnswerChoiceEntity toeicAnswerChoiceEntity = new ToeicAnswerChoiceEntity(toeicAnswerChoice);
                            toeicAnswerChoiceEntity.setToeicQuestionEntity(toeicQuestionEntity);
                            toeicAnswerChoiceRepository.save(toeicAnswerChoiceEntity);
                        }
                    }

                    if (toeicQuestionGroup.getQuestionContent() != null) {
                        for (ToeicItemContent toeicItemContentQuestionContent : toeicQuestionGroup.getQuestionContent()) {
                            ToeicItemContentEntity toeicItemContentEntity = new ToeicItemContentEntity(toeicItemContentQuestionContent);
                            toeicItemContentEntity.setToeicQuestionGroupQuestionEntityContent(toeicQuestionGroupEntity);
                            toeicItemContentRepository.save(toeicItemContentEntity);
                        }
                    }


                    if (toeicQuestionGroup.getTranscript() != null) {
                        for (ToeicItemContent toeicItemContentTranscript : toeicQuestionGroup.getTranscript()) {
                            ToeicItemContentEntity toeicItemContentEntity = new ToeicItemContentEntity(toeicItemContentTranscript);
                            toeicItemContentEntity.setToeicQuestionGroupEntityTranscript(toeicQuestionGroupEntity);
                            toeicItemContentRepository.save(toeicItemContentEntity);
                        }
                    }
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("message",tests);
        return result;
    }
}
