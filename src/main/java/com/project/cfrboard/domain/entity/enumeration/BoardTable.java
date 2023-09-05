package com.project.cfrboard.domain.entity.enumeration;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public enum BoardTable {

    FREE("free"), CFR("cfr");

    private final String value;

    BoardTable(String value) {
        this.value = value;
    }
}
