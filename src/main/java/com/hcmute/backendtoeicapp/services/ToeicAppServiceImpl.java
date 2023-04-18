package com.hcmute.backendtoeicapp.services;

import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.ErrorResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;
import com.hcmute.backendtoeicapp.dto.PracticePartInfoResponse;
import com.hcmute.backendtoeicapp.entities.ToeicPartEntity;
import com.hcmute.backendtoeicapp.repositories.ToeicPartRepository;
import com.hcmute.backendtoeicapp.repositories.ToeicQuestionGroupRepository;
import com.hcmute.backendtoeicapp.repositories.ToeicQuestionRepository;
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
}
