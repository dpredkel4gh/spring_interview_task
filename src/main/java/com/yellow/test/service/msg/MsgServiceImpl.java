package com.yellow.test.service.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String msg(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.ENGLISH);
    }

}
