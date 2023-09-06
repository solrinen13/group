package com.example.group_pr.controllers;

import com.example.group_pr.model.DogOwner;
import com.example.group_pr.service.DogOwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс для проверки методов класса CatOwnerController
 *
 * @see DogOwnerService
 */
@WebMvcTest(DogOwnerController.class)
public class DogOwnerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private DogOwnerService dogOwnerService;

    DogOwner testOne = new DogOwner();
    DogOwner testTwo = new DogOwner();
    Collection<DogOwner> listTest = new ArrayList<>();

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
     * Проверка метода <b>createDogOwner()</b> в классе DogOwnerController
     * <br>
     * Когда вызывается метод <b>DogOwnerService::createDogOwner()</b>, возвращается ожидаемый объект класса DogOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода создания владельца кошки")
    void createDogOwnerTest() throws Exception {


        Mockito.when(dogOwnerService.createDogOwner(testOne)).thenReturn(testOne);

        mvc.perform(MockMvcRequestBuilders
                        .post("/dog_owner")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(status().isOk());

        Mockito.verify(dogOwnerService, Mockito.times(1)).createDogOwner(testOne);
    }

    /**
     * Проверка метода <b>getDogOwnerById()</b> в классе DogOwnerController
     * <br>
     * Когда вызывается метод <b>DogOwnerService::findDogOwnerById()</b>, возвращается ожидаемый объект класса DogOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска хозяина кошки по id")
    void getDogOwnerByIdTest() throws Exception {


        Mockito.when(dogOwnerService.findDogOwnerById(any(Long.class))).thenReturn(testOne);

        mvc.perform(MockMvcRequestBuilders
                        .get("/dog_owner/{dogOwnerId}", 1L)
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(status().isOk());

        Mockito.verify(dogOwnerService, Mockito.times(1)).findDogOwnerById(1L);
    }

    /**
     * Проверка метода <b>getDogOwnerByChatId()</b> в классе DogOwnerController
     * <br>
     * Когда вызывается метод <b>DogOwnerService::findDogOwnerByChatId()</b>, возвращается ожидаемый объект класса DogOwner
     */
    @Test
    @DisplayName("Проверка метода поиска хозяина кошки по chat id")
    void getDogOwnerByChatIdTest() throws Exception {

        dogOwnerService.createDogOwner(testOne);
        Mockito.when(dogOwnerService.findDogOwnerByChatId(any(Long.class))).thenReturn(testOne);
        mvc.perform(MockMvcRequestBuilders
                        .get("/dog_owner/findByChatId")
                        .param("chatId", String.valueOf(123L))
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.chatId").value(123L))
                .andExpect(status().isOk());
        Mockito.verify(dogOwnerService, Mockito.times(1)).findDogOwnerByChatId(123L);
    }

    /**
     * Проверка метода <b>getAllDogOwners()</b> в классе DogOwnerController
     * <br>
     * Когда вызывается метод <b>DogOwnerService::findAllDogOwners()</b>, возвращается коллекция ожидаемых объектов класса DogOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска списка всех хозяев кошек")
    void getAllDogOwnersTest() throws Exception {
        List<DogOwner> expected = new ArrayList<>();

        expected.add(testOne);
        expected.add(testTwo);
        Mockito.when(dogOwnerService.findAllDogOwners()).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders
                        .get("/dog_owner")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    /**
     * Проверка метода <b>updateDogOwner()</b> в классе DogOwnerController
     * <br>
     * Когда вызывается метод <b>DogOwnerService::updateDogOwner()</b>, возвращается ожидаемый объект класса DogOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода изменения (обновления) данных хозяина кошки")
    void updateDogOwnerTest() throws Exception {
        Mockito.when(dogOwnerService.updateDogOwner(testOne)).thenReturn(testOne);

        mvc.perform(MockMvcRequestBuilders
                        .put("/dog_owner")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(status().isOk());

        Mockito.verify(dogOwnerService, Mockito.times(1)).updateDogOwner(testOne);
    }

    /**
     * Проверка метода <b>deleteDogOwnerById()</b> в классе DogOwnerController
     * <br>
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода удаления хозяина кошки")
    void deleteDogOwnerByIdTest() throws Exception {


        Mockito.doNothing().when(dogOwnerService).deleteDogOwnerById(testOne.getId());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/dog_owner/{dogOwnerId}", testOne.getId()))
                .andExpect(status().isOk());
    }
}
