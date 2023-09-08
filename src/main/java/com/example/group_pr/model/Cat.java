package com.example.group_pr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Entity
@Data
@NoArgsConstructor
@Table(name = "cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name", nullable = true, length = 25)
    private String nickName;  // прозвище

    @Column(name = "age")
    private Integer age;  // возраст

    @Column(name = "cat_breed", nullable = true, length = 25)
    private String catBreed;  // порода кошек

    @Column(name = "description")
    private String description;  // описание питомца

    public Cat(long id, String nickName, int age, String catBreed, String description) {
    }

}
