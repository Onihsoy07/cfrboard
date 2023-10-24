package com.project.cfrboard.domain.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyConstant {

    public final static List<String> MANAGER_ROLE = Arrays.asList("MANAGER", "ADMIN");
    public final static List<String> INQUIRY_TARGET = Arrays.asList("my", "blind", "declaration", "problem", "other");
    public final static Map<String, String> INQUIRY_TARGET_TO_KOREAN = Map.of(
            "blind", "블라인드",
            "declaration", "신고",
            "problem", "문제",
            "other", "기타"
    );

}
