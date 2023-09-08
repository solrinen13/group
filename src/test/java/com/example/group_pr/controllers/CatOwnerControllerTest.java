package com.example.group_pr.controllers;

import com.example.group_pr.model.CatOwner;
import com.example.group_pr.service.CatOwnerService;
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
 * @see CatOwnerService
 */
@WebMvcTest(CatOwnerController.class)
public class CatOwnerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
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
     * Проверка метода <b>createCatOwner()</b> в классе CatOwnerController
     * <br>
     * Когда вызывается метод <b>CatOwnerService::createCatOwner()</b>, возвращается ожидаемый объект класса CatOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода создания владельца кошки")
    void createCatOwnerTest() throws Exception {


        Mockito.when(catOwnerService.createCatOwner(testOne)).thenReturn(testOne);

        mvc.perform(MockMvcRequestBuilders
                        .post("/cat_owner")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(status().isOk());

        Mockito.verify(catOwnerService, Mockito.times(1)).createCatOwner(testOne);
    }

    /**
     * Проверка метода <b>getCatOwnerById()</b> в классе CatOwnerController
     * <br>
     * Когда вызывается метод <b>CatOwnerService::findCatOwnerById()</b>, возвращается ожидаемый объект класса CatOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска хозяина кошки по id")
    void getCatOwnerByIdTest() throws Exception {


        Mockito.when(catOwnerService.findCatOwnerById(any(Long.class))).thenReturn(testOne);

        mvc.perform(MockMvcRequestBuilders
                        .get("/cat_owner/{catOwnerId}", 1L)
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(status().isOk());

        Mockito.verify(catOwnerService, Mockito.times(1)).findCatOwnerById(1L);
    }

    /**
     * Проверка метода <b>getCatOwnerByChatId()</b> в классе CatOwnerController
     * <br>
     * Когда вызывается метод <b>CatOwnerService::findCatOwnerByChatId()</b>, возвращается ожидаемый объект класса CatOwner
     */
    @Test
    @DisplayName("Проверка метода поиска хозяина кошки по chat id")
    void getCatOwnerByChatIdTest() throws Exception {

        catOwnerService.createCatOwner(testOne);
        Mockito.when(catOwnerService.findCatOwnerByChatId(any(Long.class))).thenReturn(testOne);
        mvc.perform(MockMvcRequestBuilders
                        .get("/cat_owner/findByChatId")
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
        Mockito.verify(catOwnerService, Mockito.times(1)).findCatOwnerByChatId(123L);
    }

    /**
     * Проверка метода <b>getAllCatOwners()</b> в классе CatOwnerController
     * <br>
     * Когда вызывается метод <b>CatOwnerService::findAllCatOwners()</b>, возвращается коллекция ожидаемых объектов класса CatOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода поиска списка всех хозяев кошек")
    void getAllCatOwnersTest() throws Exception {
        List<CatOwner> expected = new ArrayList<>();

        expected.add(testOne);
        expected.add(testTwo);
        Mockito.when(catOwnerService.findAllCatOwners()).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders
                        .get("/cat_owner")
                        .content(objectMapper.writeValueAsString(expected))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(status().isOk());
    }

    /**
     * Проверка метода <b>updateCatOwner()</b> в классе CatOwnerController
     * <br>
     * Когда вызывается метод <b>CatOwnerService::updateCatOwner()</b>, возвращается ожидаемый объект класса CatOwner
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода изменения (обновления) данных хозяина кошки")
    void updateCatOwnerTest() throws Exception {
        Mockito.when(catOwnerService.updateCatOwner(testOne)).thenReturn(testOne);

        mvc.perform(MockMvcRequestBuilders
                        .put("/cat_owner")
                        .content(objectMapper.writeValueAsString(testOne))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("Степан"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Улица Пушкина 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("89564564545"))
                .andExpect(status().isOk());

        Mockito.verify(catOwnerService, Mockito.times(1)).updateCatOwner(testOne);
    }

    /**
     * Проверка метода <b>deleteCatOwnerById()</b> в классе CatOwnerController
     * <br>
     *
     * @throws Exception может возникнуть исключение
     */
    @Test
    @DisplayName("Проверка метода удаления хозяина кошки")
    void deleteCatOwnerByIdTest() throws Exception {


        Mockito.doNothing().when(catOwnerService).deleteCatOwnerById(testOne.getId());

        mvc.perform(MockMvcRequestBuilders
                        .delete("/cat_owner/{catOwnerId}", testOne.getId()))
                .andExpect(status().isOk());
    }
}
