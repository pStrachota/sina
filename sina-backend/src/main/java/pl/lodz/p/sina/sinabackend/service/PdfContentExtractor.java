package pl.lodz.p.sina.sinabackend.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.sina.sinabackend.exceptions.GeneralException;

import java.io.IOException;

@Service(ExtractorType.PDF)
public class PdfContentExtractor implements ContentExtractor{

    @Override
    public String extractContent(MultipartFile multipartFile) {
        try (final PDDocument document = Loader.loadPDF(multipartFile.getBytes())) {
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new GeneralException("Cannot read text from PDF file");
        }
    }
}
