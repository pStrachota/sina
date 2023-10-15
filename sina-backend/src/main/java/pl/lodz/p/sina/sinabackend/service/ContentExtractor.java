package pl.lodz.p.sina.sinabackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface ContentExtractor {

    String extractContent(final MultipartFile multipartFile);

}
