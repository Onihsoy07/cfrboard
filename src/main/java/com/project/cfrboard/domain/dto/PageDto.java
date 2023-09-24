package com.project.cfrboard.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private int startPage;
    private int endPage;
    private Boolean isPreviousPage;
    private Boolean isNextPage;
}
