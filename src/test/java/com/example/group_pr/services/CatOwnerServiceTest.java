package com.example.group_pr.services;


import com.example.group_pr.model.AnimalReportData;
import com.example.group_pr.model.CatOwner;
import com.example.group_pr.repository.CatOwnerRepository;
import com.example.group_pr.service.CatOwnerService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

/**
 * Класс для проверки CRUD-операций класса CatOwnerService
 * @see CatOwnerService
 * @see CatOwnerRepository
 */
@ExtendWith(MockitoExtension.class)
public class CatOwnerServiceTest {

    @Mock
    CatOwnerRepository catOwnerRepositoryMock;

    @InjectMocks
    private CatOwnerService catOwnerService;

    CatOwner testOne = new CatOwner();
    CatOwner testTwo = new CatOwner();
    Collection<CatOwner> listTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
       testOne.setId(1L);
       testOne.setChatId(123L);
       testOne.setFullName("Степан");
       testOne.setAge(45);
       testOne.setAddress("Улица Пушкина 4");
       testOne.setPhoneNumber("89564564545");

       testTwo.setId(2L);
       testTwo.setChatId(2L);
       testTwo.setFullName("ЛжеСтепан");
       testTwo.setAge(54);
       testTwo.setAddress("Улица Пушкина 3");
       testTwo.setPhoneNumber("89563264545");

       listTest.add(testOne);
       listTest.add(testTwo);
    }
    /**
     * Проверка метода <b>createCatOwner()</b> класса CatOwnerService
     * <br>
     * Когда вызывается метод <b>CatOwnerRepository::save()</b>, возвращается ожидаемый объект класса CatOwner
     */
    @Test
    void createCatOwnerTest() {

        Mockito.when(catOwnerRepositoryMock.save(testOne)).thenReturn(testOne);
        CatOwner testSave = catOwnerService.createCatOwner(testOne);
        Assertions.assertThat(testSave.getId()).isEqualTo(testOne.getId());
        Assertions.assertThat(testSave.getFullName()).isEqualTo(testOne.getFullName());
        Assertions.assertThat(testSave.getAge()).isEqualTo(testOne.getAge());
        Assertions.assertThat(testSave.getAddress()).isEqualTo(testOne.getAddress());
        Assertions.assertThat(testSave.getPhoneNumber()).isEqualTo(testOne.getPhoneNumber());
        Mockito.verify(catOwnerRepositoryMock, Mockito.times(1)).save(testSave);
    }

    /**
     * Тестирование метода <b>findCatOwnerById()</b> в CatOwnerService
     * <br>
     * Mockito: когда вызывается метод <b>CatOwnerRepository::findById</b>,
     * возвращается объект класса CatOwner <b>testOne</b> согласно введенному id
     */
    @Test
    void findCatOwnerByIdTest() {
        when(catOwnerRepositoryMock.findById(1L)).thenReturn(Optional.of(testOne));
        CatOwner testSave = catOwnerService.findCatOwnerById(1L);

        Assertions.assertThat(testSave.getId()).isEqualTo(testOne.getId());
        Assertions.assertThat(testSave.getFullName()).isEqualTo(testOne.getFullName());
        Assertions.assertThat(testSave.getAge()).isEqualTo(testOne.getAge());
        Assertions.assertThat(testSave.getAddress()).isEqualTo(testOne.getAddress());
        Assertions.assertThat(testSave.getPhoneNumber()).isEqualTo(testOne.getPhoneNumber());
        Mockito.verify(catOwnerRepositoryMock, Mockito.times(1)).findById(1L);
        Mockito.verify(catOwnerRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
    }

    /**
     * Тестирование метода <b>findCatOwnerByChatId()</b> в CatOwnerService
     * <br>
     * Mockito: когда вызывается метод <b>CatOwnerRepository::findByChatId</b>,
     * возвращается объект класса CatOwner <b>testOne</b> согласно введенному chatId
     */
    @Test
    void findCatOwnerByChatIdTest() {
        CatOwner expected = testOne;
        when(catOwnerRepositoryMock.findByChatId(123L)).thenReturn(testOne);

        CatOwner testFind = catOwnerService.findCatOwnerByChatId(123L);

        Assertions.assertThat(testFind.getId()).         isEqualTo(expected.getId());
        Assertions.assertThat(testFind.getFullName()).   isEqualTo(expected.getFullName());
        Assertions.assertThat(testFind.getAge()).        isEqualTo(expected.getAge());
        Assertions.assertThat(testFind.getAddress()).    isEqualTo(expected.getAddress());
        Assertions.assertThat(testFind.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
        Mockito.verify(catOwnerRepositoryMock, Mockito.times(1)).findByChatId(123L);
        Mockito.verify(catOwnerRepositoryMock, Mockito.times(1)).findByChatId(Mockito.anyLong());
    }

    /**
     * Тестирование метода <b>findAllCatOwners()</b> в CatOwnerService
     * <br>
     * Mockito: когда вызывается метод <b>CatOwnerRepository::findAllCatOwners</b>,
     * возвращается объект типа Collection класса CatOwner
     */
    @Test
    void findAllCatOwnersTest() {

        when(catOwnerRepositoryMock.findAll()).thenReturn(Arrays.asList(testOne, testTwo));

        Collection<CatOwner> result = catOwnerService.findAllCatOwners();
        org.junit.jupiter.api.Assertions.assertEquals(listTest.size(), result.size());
        org.junit.jupiter.api.Assertions.assertArrayEquals(result.toArray(), listTest.toArray());
    }

    /**
     * Тестирование метода <b>updateCatOwner</b> в CatOwnerService
     */
    @Test
    void updateCatOwnerTest() {

        when(catOwnerRepositoryMock.findById(testTwo.getId())).thenReturn(java.util.Optional.of(testTwo));
        when(catOwnerRepositoryMock.save(testTwo)).thenReturn(testTwo);

        CatOwner result = catOwnerService.updateCatOwner(testTwo);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertEquals(testTwo, result);
        org.junit.jupiter.api.Assertions.assertNotEquals(testOne, result);
    }

    /**
     * Тестирование метода <b>deleteCatOwner</b> в CatOwnerService
     */
    @Test
    public void deleteCatOwnerTest() {

        when(catOwnerRepositoryMock.save(testOne)).thenReturn(testOne);

        CatOwner result = catOwnerService.createCatOwner(testOne);
        catOwnerService.deleteCatOwnerById(result.getId());
        Mockito.verify(catOwnerRepositoryMock, Mockito.times(1)).deleteById(result.getId());
        Mockito.verify(catOwnerRepositoryMock, Mockito.never()).deleteById(testTwo.getId());
    }
}
