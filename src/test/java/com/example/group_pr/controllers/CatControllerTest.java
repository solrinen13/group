package com.example.group_pr.controllers;

import com.example.group_pr.model.Cat;
import com.example.group_pr.service.CatShelterService;
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
 * Класс для проверки методов класса CatController
 *
 * @see CatShelterService
 */
@WebMvcTest(CatController.class)
public class CatControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CatShelterService catService;

    Cat testOne = new Cat();
    Collection<Cat> listTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testOne.setId(1L);
        testOne.setNickName("Степанида");
        testOne.setAge(5);
        testOne.setCatBreed("Рыжая");
        testOne.setDescription("Рыжая, шумная");

        listTest.add(testOne);
    }

    /**
     * Проверка метода <b>createCat()</b> в классе CatController
     * <br>
     * Когда вызывается метод <b>CatService::createCat()</b>, возвращается ожидаемый объект класса Cat
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода создания кошки")
    void createCatTest() throws Exception {
        when(catService.createCat(testOne)).thenReturn(testOne);
        mvc.perform(MockMvcRequestBuilders
                        .post("/cat")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickName").value("Степанида"))
                .andExpect(jsonPath("$.age").value(5))
                .andExpect(jsonPath("$.catBreed").value("Рыжая"))
                .andExpect(jsonPath("$.description").value("Рыжая, шумная"))
                .andExpect(status().isOk());
        verify(catService, Mockito.times(1)).createCat(testOne);
    }
    /**
     * Проверка метода <b>getCatById()</b> в классе CatController
     * <br>
     * Когда вызывается метод <b>CatService::findCatById()</b>, возвращается ожидаемый объект класса Cat
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска кошки по id")
    void getCatByIdTest() throws Exception {
        when(catService.findCatById(anyLong())).thenReturn(testOne);
        mvc.perform(
                        get("/cat/{id}", testOne.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(catService).findCatById(1L);
    }

    /**
     * Проверка метода <b>getAllCats()</b> в классе CatController
     * <br>
     * Когда вызывается метод <b>CatService::findAllCats()</b>, возвращается коллекция ожидаемых объектов класса Cat
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска списка всех кошек")
    void getAllCatsTest() throws Exception {
        when(catService.findAllCats()).thenReturn(listTest);
        mvc.perform(get("/cat")
                        .content(objectMapper.writeValueAsString(listTest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    /**
     * Проверка метода <b>updateCat()</b> в классе CatController
     * <br>
     * Когда вызывается метод <b>CatService::updateCat()</b>, возвращается ожидаемый объект класса Cat
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода изменения (обновления) данных кошки")
    void updateCatTest() throws Exception {
        when(catService.updateCat(testOne)).thenReturn(testOne);
        mvc.perform(MockMvcRequestBuilders.put("/cat")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nickName").value("Степанида"))
                .andExpect(jsonPath("$.age").value(5))
                .andExpect(jsonPath("$.catBreed").value("Рыжая"))
                .andExpect(jsonPath("$.description").value("Рыжая, шумная"))
                .andExpect(status().isOk());
        Mockito.verify(catService, Mockito.times(1)).updateCat(testOne);
    }

    /**
     * Проверка метода <b>deleteCatById()</b> в классе CatController
     * <br>
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода удаления кошки")
    void deleteCatByIdTest() throws Exception {
        mvc.perform(
                        delete("/cat/{id}", 1))
                .andExpect(status().isOk());
        Mockito.verify(catService, Mockito.times(1)).deleteCatById(1L);
    }
}
