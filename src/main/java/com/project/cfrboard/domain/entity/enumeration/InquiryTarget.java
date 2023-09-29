package com.project.cfrboard.domain.entity.enumeration;

import lombok.Getter;

@Getter
public enum InquiryTarget {
    BLIND("blind"), DECLARATION("declaration"), PROBLEM("problem"), OTHER("other");

    private final String target;

    InquiryTarget(String target) {
        this.target = target;
    }
}
