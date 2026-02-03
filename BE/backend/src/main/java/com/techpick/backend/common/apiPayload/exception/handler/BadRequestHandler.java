package com.techpick.backend.common.apiPayload.exception.handler;

import com.techpick.backend.common.apiPayload.code.BaseErrorCode;
import com.techpick.backend.common.apiPayload.exception.GeneralException;

public class BadRequestHandler extends GeneralException {
    public BadRequestHandler(BaseErrorCode errorCode) {super(errorCode);}
}
