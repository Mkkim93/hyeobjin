package com.hyeobjin.exception;

public class DuplicateManufacturerException extends RuntimeException {

    public DuplicateManufacturerException(String message) {
        super(message);
    }

    public class InvalidManufacturerDataException extends RuntimeException {

        public InvalidManufacturerDataException(String message) {
            super(message);
        }
    }
}


