package com.example.group_pr.exception;

public class CatNotFoundException extends RuntimeException {
    public CatNotFoundException() {
        super("Кошка не найдена!");
    }
}
