package com.yellow.test.controller;

import com.yellow.test.exception.Code;
import com.yellow.test.service.msg.MsgService;
import com.yellow.test.util.ld.LocalDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.TimeZone;

@RestController
@RequestMapping("/")
public class IndexController {
    private static final LocalDateTime time = LocalDateTime.now();

    @Autowired
    private MsgService msgService;

    @GetMapping
    public ResponseEntity<String> index() {
        String msg = msgService.msg(Code.DEPLOY_INFO.getMsg(), LocalDateFormatter.format(time), TimeZone.getDefault().getID());
        return ResponseEntity.ok(msg);
    }

}
