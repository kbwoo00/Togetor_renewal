package com.togetor_renewal.togetor.web;

import java.time.LocalDateTime;

public interface Const {
    String LOGIN_SESSION = "loginSession";
    String SESSION_USER_ID = "userId";
    String SUCCESS_CHECK = "checkSuccess";
    int PAGE_SIZE = 3;

    LocalDateTime now = LocalDateTime.of(
            LocalDateTime.now().getYear(),
            LocalDateTime.now().getMonth(),
            LocalDateTime.now().getDayOfMonth(),
            LocalDateTime.now().getHour(),
            LocalDateTime.now().getMinute());
}
