package com.example.group_pr.repository;


import com.example.group_pr.model.AnimalReportData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс, содержащий методы для работы с базой данных объектов класса AnimalReportData
 * @see AnimalReportData
 * @see com.example.group_pr.service.AnimalReportDataService
 */
    public interface AnimalReportDataRepository extends JpaRepository<AnimalReportData, Long> {


    AnimalReportData findByChatId(Long id);
    List<AnimalReportData> findAnimalReportDataByChatIdIsNotNullAndLastMessageMsIsLessThan(Long millisecondsNowMinusTwoDays);
}
