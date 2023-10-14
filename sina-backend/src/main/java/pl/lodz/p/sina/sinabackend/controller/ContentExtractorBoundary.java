package pl.lodz.p.sina.sinabackend.controller;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.sina.sinabackend.dto.ContentResponseDto;
import pl.lodz.p.sina.sinabackend.service.ContentExtractorControl;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/extractor")
@RequiredArgsConstructor
@Validated
public class ContentExtractorBoundary {

    private final ContentExtractorControl contentExtractorControl;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity<ContentResponseDto> classify(@NotNull @RequestParam("file") final MultipartFile pdfFile, @NotNull @RequestParam("language") final String language, @RequestParam("contextLength") final String contextLength) {
        return ResponseEntity.ok().body(ContentResponseDto.builder().content(this.contentExtractorControl.extractContent(pdfFile, language, contextLength)).build());
    }


}