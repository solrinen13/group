package com.example.group_pr.controllers;


import com.example.group_pr.model.Dog;
import com.example.group_pr.service.DogShelterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collection;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс для проверки методов класса DogController
 *
 * @see DogShelterService
 */
@WebMvcTest(DogController.class)
public class DogControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private DogShelterService dogService;

    Dog testOne = new Dog();
    Collection<Dog> listTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testOne.setId(1L);
        testOne.setNickName("Степанида");
        testOne.setAge(5);
        testOne.setDogBreed("Рыжая");
        testOne.setDescription("Рыжая, шумная");

        listTest.add(testOne);
    }

    /**
     * Проверка метода <b>createDog()</b> в классе DogController
     * <br>
     * Когда вызывается метод <b>DogService::createDog()</b>, возвращается ожидаемый объект класса Dog
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода создания собаки")
    void createDogTest() throws Exception {
        when(dogService.createDog(testOne)).thenReturn(testOne);
        mvc.perform(MockMvcRequestBuilders
                        .post("/dog")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickName").value("Степанида"))
                .andExpect(jsonPath("$.age").value(5))
                .andExpect(jsonPath("$.dogBreed").value("Рыжая"))
                .andExpect(jsonPath("$.description").value("Рыжая, шумная"))
                .andExpect(status().isOk());
        verify(dogService, Mockito.times(1)).createDog(testOne);
    }

    /**
     * Проверка метода <b>getDogById()</b> в классе DogController
     * <br>
     * Когда вызывается метод <b>DogService::findDogById()</b>, возвращается ожидаемый объект класса Dog
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска собаки по id")
    void getDogByIdTest() throws Exception {
        when(dogService.findDogById(anyLong())).thenReturn(testOne);
        mvc.perform(
                        get("/dog/{id}", testOne.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(dogService).findDogById(1L);
    }

    /**
     * Проверка метода <b>getAllDogs()</b> в классе DogController
     * <br>
     * Когда вызывается метод <b>DogService::findAllDogs()</b>, возвращается коллекция ожидаемых объектов класса Dog
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска списка всех собак")
    void getAllDogsTest() throws Exception {
        when(dogService.findAllDogs()).thenReturn(listTest);
        mvc.perform(get("/dog")
                        .content(objectMapper.writeValueAsString(listTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    /**
     * Проверка метода <b>updateDog()</b> в классе DogController
     * <br>
     * Когда вызывается метод <b>DogService::updateDog()</b>, возвращается ожидаемый объект класса Dog
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода обновления данных собаки")
    void updateDogTest() throws Exception {
        when(dogService.updateDog(testOne)).thenReturn(testOne);
        mvc.perform(MockMvcRequestBuilders.put("/dog")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickName").value("Степанида"))
                .andExpect(jsonPath("$.age").value(5))
                .andExpect(jsonPath("$.dogBreed").value("Рыжая"))
                .andExpect(jsonPath("$.description").value("Рыжая, шумная"))
                .andExpect(status().isOk());
        Mockito.verify(dogService, Mockito.times(1)).updateDog(testOne);
    }

    /**
     * Проверка метода <b>deleteDogById()</b> в классе DogController
     * <br>
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода удаления собаки")
    void deleteDogByIdTest() throws Exception {
        mvc.perform(
                        delete("/dog/{id}", 1))
                .andExpect(status().isOk());
        Mockito.verify(dogService, Mockito.times(1)).deleteDogById(1L);
    }
}
