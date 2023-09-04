package com.example.group_pr.exception;

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException() {
        super("Собака не найдена!");
    }
}
