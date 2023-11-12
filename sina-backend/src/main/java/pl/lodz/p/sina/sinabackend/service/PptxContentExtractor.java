package pl.lodz.p.sina.sinabackend.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.sina.sinabackend.exceptions.GeneralException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service(ExtractorType.PPTX)
public class PptxContentExtractor implements ContentExtractor {

    @Override
    public String extractContent(MultipartFile multipartFile) {
        try {
            InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
            XMLSlideShow ppt = new XMLSlideShow(inputStream);
            StringBuilder sb = new StringBuilder();
            for (XSLFSlide slide : ppt.getSlides()) {
                for (XSLFShape shape : slide.getShapes()) {
                    if (shape instanceof XSLFTextShape) {
                        XSLFTextShape textShape = (XSLFTextShape) shape;
                        for (XSLFTextParagraph textParagraph : textShape.getTextParagraphs()) {
                            for (XSLFTextRun textRun : textParagraph.getTextRuns()) {
                                sb.append(textRun.getRawText());
                            }
                        }
                    }
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new GeneralException("Cannot read text from PPTX file");
        }
    }
}