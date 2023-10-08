package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.PageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PageService {

    public PageRequest cusPageable(Pageable pageable) {
        return PageRequest.of((pageable.getPageNumber()==0)?0:pageable.getPageNumber()-1, 15, Sort.by("createDate").descending());
    }

    public PageDto getPageOffset(Pageable pageable, Page<?> pageList) {
        int startPage = ((pageable.getPageNumber()-1)/10)*10;
        int endPage = pageList.getTotalPages()<=10 ? pageList.getTotalPages() : (startPage + 11);
        Boolean isPreviousPage = startPage==0 ? false : true;
        Boolean isNextPage = true;

        if (pageList.getTotalPages() <= endPage) {
            endPage = pageList.getTotalPages()+1;
            isNextPage = false;
        }

        return new PageDto(startPage, endPage, isPreviousPage, isNextPage);
    }

}
