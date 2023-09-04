package com.example.group_pr.repository;

import com.example.group_pr.model.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, из которого мы берем методы класса CatOwner
 *
 * @see CatOwner
 * @see com.example.group_pr.service.CatOwnerService
 */
public interface CatOwnerRepository extends JpaRepository<CatOwner, Long> {
    CatOwner findByChatId(Long chatId);

}
