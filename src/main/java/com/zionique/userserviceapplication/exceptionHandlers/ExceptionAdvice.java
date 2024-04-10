package com.zionique.userserviceapplication.exceptionHandlers;

import com.zionique.userserviceapplication.dtos.ErrorResponseDto;
import com.zionique.userserviceapplication.exceptions.NotFoundException;
import com.zionique.userserviceapplication.exceptions.UserExistsException;
import com.zionique.userserviceapplication.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> catchNotFoundException(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        ResponseEntity<ErrorResponseDto> response = new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponseDto> catchUserExistsException(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        ResponseEntity<ErrorResponseDto> response = new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponseDto> catchWrongPasswordException(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        ResponseEntity<ErrorResponseDto> response = new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        return response;
    }
}
