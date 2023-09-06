package com.example.group_pr.services;

import com.example.group_pr.model.Dog;
import com.example.group_pr.repository.DogRepository;
import com.example.group_pr.service.DogShelterService;
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
public class DogShelterServiceTest {
    @Mock
    DogRepository dogRepositoryMock;

    @InjectMocks
    private DogShelterService dogService;

    Dog testOne = new Dog();
    Dog testTwo = new Dog();
    Collection<Dog> listTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testOne.setId(1L);
        testOne.setNickName("Степанида");
        testOne.setAge(5);
        testOne.setDogBreed("Рыжая");
        testOne.setDescription("Рыжая, шумная");

        testOne.setId(2L);
        testOne.setNickName("Степан");
        testOne.setAge(3);
        testOne.setDogBreed("Полосатая");
        testOne.setDescription("Полосатый и игривый");

        listTest.add(testOne);
        listTest.add(testTwo);
    }
    /**
     * Проверка метода <b>createDog()</b> класса dogService
     * <br>
     * Когда вызывается метод <b>dogRepository::save()</b>, возвращается ожидаемый объект класса Dog
     */
    @Test
    void createDogTest() {

        when(dogRepositoryMock.save(testOne)).thenReturn(testOne);
        Dog testSave = dogService.createDog(testOne);
        Assertions.assertThat(testSave.getId()).isEqualTo(testOne.getId());
        Assertions.assertThat(testSave.getNickName()).isEqualTo(testOne.getNickName());
        Assertions.assertThat(testSave.getAge()).isEqualTo(testOne.getAge());
        Assertions.assertThat(testSave.getDogBreed()).isEqualTo(testOne.getDogBreed());
        Assertions.assertThat(testSave.getDescription()).isEqualTo(testOne.getDescription());
        Mockito.verify(dogRepositoryMock, Mockito.times(1)).save(testSave);
    }

    /**
     * Тестирование метода <b>findDogById()</b> в dogService
     * <br>
     * Mockito: когда вызывается метод <b>dogRepository::findById</b>,
     * возвращается объект класса dog <b>testOne</b> согласно введенному id
     */
    @Test
    void findDogByIdTest() {
        when(dogRepositoryMock.findById(1L)).thenReturn(Optional.of(testOne));
        Dog testSave = dogService.findDogById(1L);

        Assertions.assertThat(testSave.getId()).isEqualTo(testOne.getId());
        Assertions.assertThat(testSave.getNickName()).isEqualTo(testOne.getNickName());
        Assertions.assertThat(testSave.getAge()).isEqualTo(testOne.getAge());
        Assertions.assertThat(testSave.getDogBreed()).isEqualTo(testOne.getDogBreed());
        Assertions.assertThat(testSave.getDescription()).isEqualTo(testOne.getDescription());
        Mockito.verify(dogRepositoryMock, Mockito.times(1)).findById(1L);
        Mockito.verify(dogRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
    }


    /**
     * Тестирование метода <b>findAllDogs()</b> в dogService
     * <br>
     * Mockito: когда вызывается метод <b>dogRepository::findAllDogs</b>,
     * возвращается объект типа Collection класса dog
     */
    @Test
    void findAllDogsTest() {

        when(dogRepositoryMock.findAll()).thenReturn(Arrays.asList(testOne, testTwo));

        Collection<Dog> result = dogService.findAllDogs();
        org.junit.jupiter.api.Assertions.assertEquals(listTest.size(), result.size());
        org.junit.jupiter.api.Assertions.assertArrayEquals(result.toArray(), listTest.toArray());
    }

    /**
     * Тестирование метода <b>updateDog</b> в dogService
     */
    @Test
    void updateDogTest() {

        Dog expected = new Dog();
        expected.setId(1L);
        expected.setNickName("Бобик>");
        expected.setAge(3);
        expected.setDogBreed("Дворняга");

        when(dogRepositoryMock.findById(1L)).thenReturn(Optional.of(expected));
        when(dogRepositoryMock.save(expected)).thenReturn(expected);

        Dog actual = dogService.updateDog(expected);

        org.junit.jupiter.api.Assertions.assertEquals(actual.getNickName(), expected.getNickName());
        org.junit.jupiter.api.Assertions.assertEquals(actual.getAge(), expected.getAge());
        org.junit.jupiter.api.Assertions.assertEquals(actual.getDogBreed(), expected.getDogBreed());
    }

    /**
     * Тестирование метода <b>deleteDog</b> в dogService
     */
    @Test
    public void deleteDogTest() {

        when(dogRepositoryMock.save(testOne)).thenReturn(testOne);

        Dog result = dogService.createDog(testOne);
        dogService.deleteDogById(result.getId());
        Mockito.verify(dogRepositoryMock, Mockito.times(1)).deleteById(result.getId());
        Mockito.verify(dogRepositoryMock, Mockito.never()).deleteById(testTwo.getId());
    }
}
