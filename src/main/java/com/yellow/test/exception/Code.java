package com.yellow.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Code {
    C_1001(1001, "c.1001"),
    C_1002(1002, "c.1002"),
    C_1003(1003, "c.1003"),
    C_1004(1004, "c.1004"),

    DEPLOY_INFO(0, "deploy.info");

    private final int value;
    private final String msg;

}
