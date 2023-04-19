package com.hcmute.backendtoeicapp.services;

import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.ErrorResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;
import com.hcmute.backendtoeicapp.dto.PracticePartInfoResponse;
import com.hcmute.backendtoeicapp.entities.*;
import com.hcmute.backendtoeicapp.model.ToeicAnswerChoice;
import com.hcmute.backendtoeicapp.model.ToeicItemContent;
import com.hcmute.backendtoeicapp.model.ToeicQuestion;
import com.hcmute.backendtoeicapp.model.ToeicQuestionGroup;
import com.hcmute.backendtoeicapp.repositories.*;
import com.hcmute.backendtoeicapp.services.interfaces.ToeicAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToeicAppServiceImpl implements ToeicAppService {
    @Autowired
    private ToeicPartRepository toeicPartRepository;
    @Autowired
    private ToeicQuestionGroupRepository toeicQuestionGroupRepository;
    @Autowired
    private ToeicQuestionRepository toeicQuestionRepository;
    @Autowired
    private ToeicItemContentRepository toeicItemContentRepository;
    @Autowired
    private ToeicAnswerChoiceRepository toeicAnswerChoiceRepository;

    @Override
    public BaseResponse getListPracticePartInfoByPartId(Integer id) {

        if (id < 1 || id > 7) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage("Không tồn tại part id = " + id);
            return response;
        }
        List<PracticePartInfoResponse> practicePartInfoResponseList = new ArrayList<>();
        List<ToeicPartEntity> toeicPartEntityList = this.toeicPartRepository.getToeicPartByPartNumber(id);
        for (ToeicPartEntity toeicPartEntity : toeicPartEntityList) {
            PracticePartInfoResponse response = new PracticePartInfoResponse();
            response.setPartId(toeicPartEntity.getId());
            response.setNumOfQuestions(this.toeicPartRepository.getNumOfQuestions(toeicPartEntity.getId()));
            practicePartInfoResponseList.add(response);
        }
        for (int i = 0; i < practicePartInfoResponseList.size(); i++) {
            practicePartInfoResponseList.get(i).setName("Luyện tập part " + id + " - " + (i + 1));
        }
        SuccessfulResponse response = new SuccessfulResponse();
        response.setMessage("Lấy dữ liệu thành công");
        response.setData(practicePartInfoResponseList);
        return response;
    }
    @Override
    public BaseResponse getListQuestionGroupAndQuestionByPartId(Integer id) {
        if (this.toeicPartRepository.findToeicPartEntityById(id) == null) {
            ErrorResponse response = new ErrorResponse();
            response.setMessage("Không tồn tại part có id = " + id);
            return response;
        }
        List<ToeicQuestionGroupEntity> toeicQuestionGroupEntityList =
                this.toeicQuestionGroupRepository.getListToeicQuestionGroupByPartId(id);
        List<ToeicQuestionGroup> questionGroups = new ArrayList<ToeicQuestionGroup>();
        for (ToeicQuestionGroupEntity entity : toeicQuestionGroupEntityList) {
            ToeicQuestionGroup model = new ToeicQuestionGroup();
            List<ToeicItemContentEntity> questionContentEntities =
                    this.toeicItemContentRepository.getListQuestionContentByQuestionGroupId(entity.getId());
            List<ToeicItemContentEntity> transcriptEntities =
                    this.toeicItemContentRepository.getListTranscriptByQuestionGroupId(entity.getId());
            List<ToeicQuestionEntity> questionEntities =
                    this.toeicQuestionRepository.getListToeicQuestionByQuestionGroupId(entity.getId());
            List<ToeicItemContent> questionContents = questionContentEntities.stream().map(ToeicItemContent::new).toList();
            List<ToeicItemContent> transcripts = transcriptEntities.stream().map(ToeicItemContent::new).toList();
            List<ToeicQuestion> questions = questionEntities.stream().map(ToeicQuestion::new).toList();
            for (int i = 0;i < questionEntities.size();i++) {
                List<ToeicAnswerChoiceEntity> answerChoiceEntities =
                        this.toeicAnswerChoiceRepository.getToeicAnswerChoiceByToeicQuestionId(questionEntities.get(i).getId());
                List<ToeicAnswerChoice> answerChoices = answerChoiceEntities.stream().map(ToeicAnswerChoice::new).toList();
                questions.get(i).setChoices(answerChoices);
            }
            model.setQuestionContent(questionContents);
            model.setTranscript(transcripts);
            model.setQuestions(questions);
            questionGroups.add(model);
        }
        SuccessfulResponse response = new SuccessfulResponse();
        response.setMessage("Lấy dữ liệu thành công");
        response.setData(questionGroups);
        return response;
    }
}
