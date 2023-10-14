package pl.lodz.p.sina.sinabackend.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.sina.sinabackend.dto.ChatRequest;
import pl.lodz.p.sina.sinabackend.dto.ChatResponse;
import pl.lodz.p.sina.sinabackend.exceptions.CannotReadTextFromPdfException;
import pl.lodz.p.sina.sinabackend.exceptions.GeneralException;

import java.io.IOException;

@Service
@Slf4j
public class ContentExtractorControl {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final String PROMPT_TEMPLATE = """
            I'm developing an application for summarizing PDF files.
            Write me a summary of what is in this PDF file.
            Write in language: %s
            Here is the content of the PDF file that needs to be summarized:       \s
            %s
            """;

    public String extractContent(final MultipartFile multipartFile, final String language) {
        String text;

        try (final PDDocument document = Loader.loadPDF(multipartFile.getBytes())) {
            final PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
            if (text == null || text.isEmpty()) {
                throw new CannotReadTextFromPdfException("Cannot read text from PDF file");
            } else {
                String prompt = String.format(PROMPT_TEMPLATE, language, text);
                ChatRequest request = new ChatRequest(model, prompt);

                ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
                if (response != null) {
                    text = response.getChoices().get(0).getMessage().getContent();
                }
            }

        } catch (IOException e) {
           throw new GeneralException("Unknown exception while reading PDF file");
        }
        return text;
    }

}