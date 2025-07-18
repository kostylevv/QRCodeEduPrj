package com.vkostylev.edu.qrcodeprj;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String IMAGE_SIZE_ERROR_MESSAGE = "Image size must be between 150 and 350 pixels";
    private static final String IMAGE_FORMAT_ERROR_MESSAGE = "Only png, jpeg and gif image types are supported";
    private static final String CONTENTS_ERROR_MESSAGE = "Contents cannot be null or blank";
    private static final String CORRECTION_ERROR_MESSAGE = "Permitted error correction levels are L, M, Q, H";
    private static final String OTHER_MESSAGE = "Something went wrong";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        boolean sizeIsInvalid = false;
        boolean formatIsInvalid = false;
        boolean contentsIsInvalid = false;
        boolean correctionIsInvalid = false;

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            switch (fieldError.getField()) {
                case "contents":
                    contentsIsInvalid = true;
                    break;
                case "size":
                    sizeIsInvalid = true;
                    break;
                case "type":
                    formatIsInvalid = true;
                    break;
                case "correction":
                    correctionIsInvalid = true;
            }
        }
        if (contentsIsInvalid) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(CONTENTS_ERROR_MESSAGE));
        } else if (sizeIsInvalid) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(IMAGE_SIZE_ERROR_MESSAGE));
        } else if (correctionIsInvalid) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(CORRECTION_ERROR_MESSAGE));
        } else if (formatIsInvalid) {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(IMAGE_FORMAT_ERROR_MESSAGE));
        } else {
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(OTHER_MESSAGE));
        }
    }

}
