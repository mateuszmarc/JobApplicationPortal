package com.marcykiewicz.mateusz.joba_application_portal.exception;

public class DatabaseIntegrityException extends RuntimeException {
    public DatabaseIntegrityException(String message) {
        super(message);
    }
}
