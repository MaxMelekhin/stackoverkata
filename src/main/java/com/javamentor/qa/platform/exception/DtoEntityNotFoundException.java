package com.javamentor.qa.platform.exception;

/**Ошибка отсутствия ответа в БД.*/
public class DtoEntityNotFoundException extends RuntimeException{
    public DtoEntityNotFoundException() {
    }
    public DtoEntityNotFoundException(String message) {
        super(message);
    }
}
