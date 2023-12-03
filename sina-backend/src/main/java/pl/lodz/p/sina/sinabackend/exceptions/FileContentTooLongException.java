package pl.lodz.p.sina.sinabackend.exceptions;

public class FileContentTooLongException extends RuntimeException {

    public FileContentTooLongException(String message) {
        super(message);
    }

}