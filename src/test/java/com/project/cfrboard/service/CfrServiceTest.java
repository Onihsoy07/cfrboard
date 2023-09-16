package com.project.cfrboard.service;

import com.project.cfrboard.domain.dto.CfrResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class CfrServiceTest {

    @Autowired
    CfrService cfrService;

    @Test
    void getCfrResponseDto() throws IOException {
        //given
        ClassPathResource resource = new ClassPathResource("/static/image/haruka.png");
        File file = resource.getFile();
        MultipartFile multipartFile = new MockMultipartFile("static/image/haruka.png", new FileInputStream(file));

        //when
        CfrResponseDto cfrResponseDto = cfrService.getCfrResponseDto(multipartFile);

        //then
        assertThat(cfrResponseDto.getFaces().isEmpty()).isFalse();
        log.info("cfrResponseDto = {}", cfrResponseDto);
    }

//    private MultipartFile fileToMultipartFile(File file) throws IOException {
//        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
//
//        try {
//            InputStream input = new FileInputStream(file);
//            OutputStream os = fileItem.getOutputStream();
//            IOUtils.copy(input, os);
//            // Or faster..
//            // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
//        } catch (IOException ex) {
//            // do something.
//        }
//
//        /**
//         * 필요 타입:
//         * org.apache.commons.fileupload.FileItem
//         * 제공된 타입:
//         * org.apache.tomcat.util.http.fileupload.FileItem
//         *
//         * DI 필요?
//         */
//        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
//    }

}