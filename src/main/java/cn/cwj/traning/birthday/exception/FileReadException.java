package cn.cwj.traning.birthday.exception;

public class FileReadException extends RuntimeException {

    public FileReadException() {
        super();
    }

    public FileReadException(String message, Throwable exception) {
        super(message, exception);
    }
}
