package ru.khmelev.tm.exception;

public class EndpointException extends RuntimeException {
    public EndpointException() {
        super("Ошибка в веб-сервисах");
    }
}