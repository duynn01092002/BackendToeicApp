package com.hcmute.backendtoeicapp.services.interfaces;

import com.hcmute.backendtoeicapp.base.BaseResponse;
import com.hcmute.backendtoeicapp.base.SuccessfulResponse;

public interface ToeicAppService {
    BaseResponse getListPracticePartInfoByPartId(Integer id);
}
