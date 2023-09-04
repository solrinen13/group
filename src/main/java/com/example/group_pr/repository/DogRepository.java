package com.example.group_pr.repository;

import com.example.group_pr.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Интерфейс, из которого мы берем методы класса Dog
 * @see Dog
 * @see com.example.group_pr.service.DogShelterService
 */
    public interface DogRepository extends JpaRepository<Dog, Long> {


}
