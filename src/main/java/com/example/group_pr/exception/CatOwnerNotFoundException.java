package com.example.group_pr.exception;

public class CatOwnerNotFoundException extends RuntimeException {
    public CatOwnerNotFoundException() {
        super("Хозяин кошки не найден!");
    }
}
