package com.wanubit.springtesting.domain;

import lombok.Getter;

@Getter
public enum CourseType {
    BOOTCAMP("BOOTCAMP"),
    MASTERCLASS("MASTERCLASS"),
    INITIAL("INITIAL");

    private final String type;

    CourseType(String type) {
        this.type = type;
    }
}
