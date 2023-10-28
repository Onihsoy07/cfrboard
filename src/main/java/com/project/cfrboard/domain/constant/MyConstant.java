package com.project.cfrboard.domain.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MyConstant {

    public final static List<String> MANAGER_ROLE = Arrays.asList("MANAGER", "ADMIN");
    public final static List<String> INQUIRY_CATEGORY = Arrays.asList("my", "blind", "declaration", "problem", "other");
    public final static Map<String, String> INQUIRY_CATEGORY_TO_KOREAN = Map.of(
            "blind", "블라인드",
            "declaration", "신고",
            "problem", "문제",
            "other", "기타"
    );
    public final static List<String> DECLARATION_TARGET = Arrays.asList("board", "reply");

}
