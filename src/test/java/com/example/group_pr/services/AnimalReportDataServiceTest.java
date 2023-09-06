package com.example.group_pr.services;

import com.example.group_pr.model.AnimalReportData;
import com.example.group_pr.repository.AnimalReportDataRepository;
import com.example.group_pr.service.AnimalReportDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Класс для проверки CRUD-операций класса AnimalReportDataService
 *
 * @see AnimalReportData
 * @see AnimalReportDataRepository
 */
@ExtendWith(MockitoExtension.class)
class AnimalReportDataServiceTest {
    @Mock
    private AnimalReportDataRepository animalReportDataRepository;

    @InjectMocks
    private AnimalReportDataService animalReportDataService;

    AnimalReportData reportTestOne = new AnimalReportData();
    AnimalReportData reportTestTwo = new AnimalReportData();
    Collection<AnimalReportData> reportListTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        reportTestOne.setId(1L);
        reportTestOne.setChatId(1L);
        reportTestOne.setRationOfAnimal("Сухой корм");
        reportTestOne.setHealthOfAnimal("Статус животного");
        reportTestOne.setHabitsOfAnimal("Некие привычки животного");
        reportTestOne.setDaysOfOwnership(5L);

        reportTestTwo.setId(2L);
        reportTestTwo.setChatId(2L);
        reportTestTwo.setRationOfAnimal("Сухой корм 1 ");
        reportTestTwo.setHealthOfAnimal("Статус животного 2");
        reportTestTwo.setHabitsOfAnimal("Некие привычки животного 3");
        reportTestTwo.setDaysOfOwnership(15L);

        reportListTest.add(reportTestOne);
        reportListTest.add(reportTestTwo);
    }


    /**
     * Тестирование метода <b>createAnimalReport()</b>
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::save</b>,
     * возвращается объект класса AnimalReportData <b>reportTestOne</b>
     */
    @Test
    void createAnimalReportDataTest() {

        when(animalReportDataRepository.save(reportTestOne)).thenReturn(reportTestOne);

        AnimalReportData result = animalReportDataService.createAnimalReportData(reportTestOne);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, reportTestOne);
        Assertions.assertNotEquals(result, reportTestTwo);
    }

    /**
     * Тестирование метода <b>findById()</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::findById</b>,
     * возвращается объект класса AnimalReportData <b>reportTestOne</b> согласно введенному id
     */
    @Test
    void findByIdTest() {
        when(animalReportDataRepository.findById(1L)).thenReturn(Optional.of(reportTestOne));

        AnimalReportData result = animalReportDataService.findAnimalReportDataById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), reportTestOne.getId());
        Assertions.assertEquals(result.getChatId(), reportTestOne.getChatId());
        Assertions.assertEquals(result.getRationOfAnimal(), reportTestOne.getRationOfAnimal());
        Assertions.assertEquals(result.getHealthOfAnimal(), reportTestOne.getHealthOfAnimal());
        Assertions.assertEquals(result.getHabitsOfAnimal(), reportTestOne.getHabitsOfAnimal());
        Assertions.assertEquals(result.getDaysOfOwnership(), reportTestOne.getDaysOfOwnership());
        Assertions.assertNotEquals(result, reportTestTwo);
    }

    /**
     * Тестирование метода <b>findAll()</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::findAll</b>,
     * возвращается объект типа Collection класса AnimalReportData
     */
    @Test
    void findAllAnimalReportTest() {

        when(animalReportDataRepository.findAll()).thenReturn(Arrays.asList(reportTestOne, reportTestTwo));

        Collection<AnimalReportData> result = animalReportDataService.findAllAnimalReport();
        Assertions.assertEquals(reportListTest.size(), result.size());
        Assertions.assertArrayEquals(result.toArray(), reportListTest.toArray());
    }

    /**
     * Тестирование метода <b>updateAnimalReportData</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::findById</b>,
     * возвращается объект класса AnimalReportData <b>reportTestOne</b>
     */
    @Test
    void updateAnimalReportDataTest() {

        when(animalReportDataRepository.findById(reportTestTwo.getId())).thenReturn(java.util.Optional.of(reportTestTwo));
        when(animalReportDataRepository.save(reportTestTwo)).thenReturn(reportTestTwo);

        AnimalReportData result = animalReportDataService.updateAnimalReportData(reportTestTwo);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(reportTestTwo, result);
        Assertions.assertNotEquals(reportTestOne, result);
    }

    /**
     * Тестирование метода <b>deleteAnimalReportData</b> в AnimalReportDataService
     */
    @Test
    public void deleteAnimalReportDataTest() {

        when(animalReportDataRepository.save(reportTestOne)).thenReturn(reportTestOne);

        AnimalReportData result = animalReportDataService.createAnimalReportData(reportTestOne);
        animalReportDataService.deleteAnimalReportDataById(result.getId());
        Mockito.verify(animalReportDataRepository, Mockito.times(1)).deleteById(result.getId());
        Mockito.verify(animalReportDataRepository, Mockito.never()).deleteById(reportTestTwo.getId());
    }
}