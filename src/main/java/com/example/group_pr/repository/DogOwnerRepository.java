package com.example.group_pr.repository;

import com.example.group_pr.model.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, из которого мы берем методы класса DogOwner
 *
 * @see DogOwner
 * @see com.example.group_pr.service.DogOwnerService
 */
public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
    DogOwner findByChatId(Long chatId);
}
