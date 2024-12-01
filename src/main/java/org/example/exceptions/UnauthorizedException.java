package org.example.exceptions;

/**
 * Exception thrown when a user is not authorized to access a resource.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
