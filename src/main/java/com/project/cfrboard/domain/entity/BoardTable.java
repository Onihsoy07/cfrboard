package com.project.cfrboard.domain.entity;

import lombok.Getter;

@Getter
public enum BoardTable {

    FREE("free"), MANAGER("cfr");

    private final String value;

    BoardTable(String value) {
        this.value = value;
    }
}
