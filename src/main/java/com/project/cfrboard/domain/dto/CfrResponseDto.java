package com.project.cfrboard.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CfrResponseDto {

    public Info info;
    public List<Face> faces;

    @Data
    @NoArgsConstructor
    public static class Info {
        public Size size;
        public Integer faceCount;

        @Data
        @NoArgsConstructor
        public static class Size {
            public Integer width;
            public Integer height;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Face {
        public Celebrity celebrity;

        @Data
        @NoArgsConstructor
        public static class Celebrity {
            public String value;
            public Float confidence;
        }
    }

}
