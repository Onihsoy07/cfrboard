package com.project.cfrboard.domain.entity.enumeration;

import lombok.Getter;

@Getter
public enum BoardTable {

    FREE("FREE"), CFR("CFR");

    private final String value;

    BoardTable(String value) {
        this.value = value;
    }
}
