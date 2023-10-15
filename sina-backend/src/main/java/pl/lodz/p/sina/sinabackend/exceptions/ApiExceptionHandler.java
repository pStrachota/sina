package pl.lodz.p.sina.sinabackend.exceptions;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiException apiException =
                new ApiException("INVALID ARGUMENT PASSED", HttpStatus.BAD_REQUEST,
                        errors, LocalDateTime.now());

        return handleExceptionInternal(ex, apiException, headers, apiException.getHttpStatus(),
                request);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                       WebRequest request) {
        ApiException apiException =
                new ApiException(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        return new ResponseEntity<>(
                new ApiException(ex.getLocalizedMessage(), HttpStatus.METHOD_NOT_ALLOWED,
                        List.of(request.getDescription(false)), LocalDateTime.now()),
                new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }



    @ExceptionHandler(NumberFormatException.class)
    public final ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex,
                                                                    WebRequest request) {
        ApiException apiException =
                new ApiException(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @ExceptionHandler(CannotReadTextException.class)
    public final ResponseEntity<Object> handleCannotReadTextFromPdfException(CannotReadTextException ex,
                                                                             WebRequest request) {
        ApiException apiException =
                new ApiException(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<Object> handleGeneralException(GeneralException ex,
                                                                             WebRequest request) {
        ApiException apiException =
                new ApiException(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public final ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex,
                                                                    WebRequest request) {
        ApiException apiException =
                new ApiException(ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex,
                                                                       WebRequest request) {
        ApiException apiException =
                new ApiException(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST,
                        List.of(request.getDescription(false)), LocalDateTime.now());

        return new ResponseEntity<>(apiException, new HttpHeaders(), apiException.getHttpStatus());
    }

}




