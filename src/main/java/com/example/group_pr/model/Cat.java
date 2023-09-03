package com.example.group_pr.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
@Entity
@Data
@Table(name = "cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name", nullable = false, length = 25)
    private String nickName;  // прозвище

    @Column(name = "age")
    private Integer age;  // возраст

    @Column(name = "cat_breed", nullable = false, length = 25)
    private String catBreed;  // порода кошек

    @Column(name = "description")
    private String description;  // описание питомца

}
