package cn.luokaiii.user.exception;

import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public HttpStatus getStatus() {
        return status;
    }

    public ResponseException() {
        super();
    }

    public ResponseException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public ResponseException(HttpStatus status) {
        super();
        this.status = status;
    }

    public ResponseException(String msg) {
        super(msg);
    }
}
