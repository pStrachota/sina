package pl.lodz.p.sina.sinabackend.exceptions;

public class FileTooBigException extends RuntimeException {

    public FileTooBigException(String message) {
        super(message);
    }

}