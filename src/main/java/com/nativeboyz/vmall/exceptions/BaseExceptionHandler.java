package com.nativeboyz.vmall.exceptions;

import com.nativeboyz.vmall.models.dto.MessageDto;
import com.nativeboyz.vmall.services.storage.StorageException;
import com.nativeboyz.vmall.services.storage.StorageFileNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class BaseExceptionHandler {

    // TODO: SqlExceptionHelper message

    // (save JpaRepository) DataIntegrityViolationException

    @ExceptionHandler({
            NoSuchElementException.class, // optional throw (findById JpaRepository)
            EmptyResultDataAccessException.class, // (deleteById JpaRepository)
            StorageFileNotFoundException.class
    })
    public ResponseEntity<MessageDto> handleEmptyResultDataAccessException(Exception exc) {
        return handleException(exc, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            PropertyReferenceException.class, // PageRequest with wrong fields
            FilePriorityNotFound.class // files for upload not match with json payload
    })
    public ResponseEntity<MessageDto> handleFilePriorityException(Exception exc) {
        return handleException(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> handleDataIntegrityViolationException(
            DataIntegrityViolationException exc
    ) {
        return handleException(exc, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> handleStorageException(StorageException exc) {
        return handleException(exc, HttpStatus.FORBIDDEN);
    }

    public <T extends Exception> ResponseEntity<MessageDto> handleException(T e, HttpStatus httpStatus) {
        MessageDto error = new MessageDto(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    /*
    @ExceptionHandler
    public ResponseEntity<MessageResponse> handleStorageFileNotFoundException(StorageFileNotFoundException exc) {
        return handleException(exc, HttpStatus.NOT_FOUND);
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler({ OptimisticLockingFailureException.class, DataIntegrityViolationException.class })
    ResponseEntity<ExceptionMessage> handleConflict(Exception o_O) {
        return errorResponse(HttpStatus.CONFLICT, new HttpHeaders(), o_O);
        https://stackoverflow.com/questions/43678306/handling-dataintegrityviolationexceptions-in-spring-data-rest
    }
    */

}
