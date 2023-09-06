package com.example.group_pr.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@Table(name = "cat_owner")
public class CatOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName;                           // полное имя хозяина животного
    @Column(name = "age")
    private Integer age;                               // возраст хозяина животного
    @Column(name = "address")
    private String address;                            // адрес хозяина животного
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;                        // контактный номер телефона хозяина животного

    private Long chatId;

    @Enumerated
    private OwnerStatus status;

    public CatOwner(long l, String testFullName, int i, String testAddress, String testPhoneNumber) {
    }


    public CatOwner(long l, String testFullName, int i, String testAddress, String testPhoneNumber, Long chatId, OwnerStatus ownerStatus) {
    }
}
