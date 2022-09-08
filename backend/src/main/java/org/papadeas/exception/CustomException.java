package org.papadeas.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * The custom exception
 */
@Getter
@Setter
abstract class CustomException extends RuntimeException {

    CustomException(String message) {
        super(message);
    }

    CustomException(Throwable tr) {
        super(tr);
    }

    CustomException(String message, Throwable tr) {
        super(message, tr);
    }
}

