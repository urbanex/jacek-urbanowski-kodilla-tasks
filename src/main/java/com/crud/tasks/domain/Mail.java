package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCc;

    public Mail(final String mailTo, final String subject, final String message) {
        this(mailTo, subject, message, null);
    }
}


