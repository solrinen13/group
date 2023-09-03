package com.example.group_pr.exception;

public class DogOwnerNotFoundException extends RuntimeException {
    public DogOwnerNotFoundException() {
        super("Хозяин собаки не найден!");
    }
}
