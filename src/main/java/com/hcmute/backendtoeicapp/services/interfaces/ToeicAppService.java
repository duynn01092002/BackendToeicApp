package com.hcmute.backendtoeicapp.services.interfaces;

import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;

import java.io.IOException;

public interface ToeicAppService {
    BaseResponse getListPracticePartInfoByPartId(Integer id);

    BaseResponse getListQuestionGroupAndQuestionByPartId(Integer id);

    byte[] downloadPartData(Integer partId) throws IOException;
}
