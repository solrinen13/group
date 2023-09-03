package com.example.group_pr.repository;

import com.example.group_pr.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, из которого мы берем методы класса Cat
 * @see Cat
 * @see com.example.group_pr.service.CatShelterService
 */
    public interface CatRepository extends JpaRepository<Cat, Long> {


}
