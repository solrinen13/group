package com.example.group_pr.services;

import com.example.group_pr.model.Cat;
import com.example.group_pr.model.CatOwner;
import com.example.group_pr.repository.CatRepository;
import com.example.group_pr.service.CatOwnerService;
import com.example.group_pr.service.CatShelterService;
import org.assertj.core.api.Assertions;
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
@ExtendWith(MockitoExtension.class)
public class CatShelterServiceTest {
    @Mock
    CatRepository catRepositoryMock;

    @InjectMocks
    private CatShelterService catService;

    Cat testOne = new Cat();
    Cat testTwo = new Cat();
    Collection<Cat> listTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testOne.setId(1L);
        testOne.setNickName("Степанида");
        testOne.setAge(5);
        testOne.setCatBreed("Рыжая");
        testOne.setDescription("Рыжая, шумная");

        testOne.setId(2L);
        testOne.setNickName("Степан");
        testOne.setAge(3);
        testOne.setCatBreed("Полосатая");
        testOne.setDescription("Полосатый и игривый");

        listTest.add(testOne);
        listTest.add(testTwo);
    }
    /**
     * Проверка метода <b>createCat()</b> класса CatService
     * <br>
     * Когда вызывается метод <b>CatRepository::save()</b>, возвращается ожидаемый объект класса Cat
     */
    @Test
    void createCatTest() {

        Mockito.when(catRepositoryMock.save(testOne)).thenReturn(testOne);
        Cat testSave = catService.createCat(testOne);
        Assertions.assertThat(testSave.getId()).isEqualTo(testOne.getId());
        Assertions.assertThat(testSave.getNickName()).isEqualTo(testOne.getNickName());
        Assertions.assertThat(testSave.getAge()).isEqualTo(testOne.getAge());
        Assertions.assertThat(testSave.getCatBreed()).isEqualTo(testOne.getCatBreed());
        Assertions.assertThat(testSave.getDescription()).isEqualTo(testOne.getDescription());
        Mockito.verify(catRepositoryMock, Mockito.times(1)).save(testSave);
    }

    /**
     * Тестирование метода <b>findCatById()</b> в CatService
     * <br>
     * Mockito: когда вызывается метод <b>CatRepository::findById</b>,
     * возвращается объект класса Cat <b>testOne</b> согласно введенному id
     */
    @Test
    void findCatByIdTest() {
        when(catRepositoryMock.findById(1L)).thenReturn(Optional.of(testOne));
        Cat testSave = catService.findCatById(1L);

        Assertions.assertThat(testSave.getId()).isEqualTo(testOne.getId());
        Assertions.assertThat(testSave.getNickName()).isEqualTo(testOne.getNickName());
        Assertions.assertThat(testSave.getAge()).isEqualTo(testOne.getAge());
        Assertions.assertThat(testSave.getCatBreed()).isEqualTo(testOne.getCatBreed());
        Assertions.assertThat(testSave.getDescription()).isEqualTo(testOne.getDescription());
        Mockito.verify(catRepositoryMock, Mockito.times(1)).findById(1L);
        Mockito.verify(catRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
    }


    /**
     * Тестирование метода <b>findAllCats()</b> в CatService
     * <br>
     * Mockito: когда вызывается метод <b>CatRepository::findAllCats</b>,
     * возвращается объект типа Collection класса Cat
     */
    @Test
    void findAllCatsTest() {

        when(catRepositoryMock.findAll()).thenReturn(Arrays.asList(testOne, testTwo));

        Collection<Cat> result = catService.findAllCats();
        org.junit.jupiter.api.Assertions.assertEquals(listTest.size(), result.size());
        org.junit.jupiter.api.Assertions.assertArrayEquals(result.toArray(), listTest.toArray());
    }

    /**
     * Тестирование метода <b>updateCat</b> в CatService
     */
    @Test
    void updateCatTest() {

        Cat expected = new Cat();
        expected.setId(1L);
        expected.setNickName("Василий");
        expected.setAge(3);
        expected.setCatBreed("Черный");

        Mockito.when(catRepositoryMock.findById(1L)).thenReturn(Optional.of(expected));
        Mockito.when(catRepositoryMock.save(expected)).thenReturn(expected);

        Cat actual = catService.updateCat(expected);

       org.junit.jupiter.api.Assertions.assertEquals(actual.getNickName(), expected.getNickName());
       org.junit.jupiter.api.Assertions.assertEquals(actual.getAge(), expected.getAge());
       org.junit.jupiter.api.Assertions.assertEquals(actual.getCatBreed(), expected.getCatBreed());
    }

    /**
     * Тестирование метода <b>deleteCat</b> в CatService
     */
    @Test
    public void deleteCatTest() {

        when(catRepositoryMock.save(testOne)).thenReturn(testOne);

        Cat result = catService.createCat(testOne);
        catService.deleteCatById(result.getId());
        Mockito.verify(catRepositoryMock, Mockito.times(1)).deleteById(result.getId());
        Mockito.verify(catRepositoryMock, Mockito.never()).deleteById(testTwo.getId());
    }
}
