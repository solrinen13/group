package com.example.group_pr.controllers;


import com.example.group_pr.model.Dog;

import com.example.group_pr.service.DogShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/dog")
@Tag(name = "Dogs", description = "CRUD-операции для работы с кошками")
public class DogController {
    private final DogShelterService dogShelterService;

    public DogController(DogShelterService dogShelterService) {
        this.dogShelterService = dogShelterService;
    }

    @PostMapping
    @Operation(
            summary = "Создать новую собаку",
            description = "Создание новой собаки с ее уникальным идентификатором"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собака успешно создана",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            )
    })
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
        Dog createdDog = dogShelterService.createDog(dog);
        return ResponseEntity.ok(createdDog);
    }

    @GetMapping("/{dogId}")
    @Operation(
            summary = "Найти собаку по ее уникальному идентификатору ",
            description = "Поиск собаку по ее уникальному идентификатору"
    )
    @Parameters(value = {
            @Parameter(name = "Уникальный идентификатор собаки", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собаки успешно найдена",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собака не найдена",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Dog> getDogById(@PathVariable("dogId") Long dogId) {
        Dog foundDog = dogShelterService.findDogById(dogId);
        if (foundDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundDog);
    }

    @GetMapping
    @Operation(
            summary = "Найти список всех собак",
            description = "Показать список всех собак"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список собак успешно найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список собак не найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Collection<Dog>> getAllDogs() {
        Collection<Dog> dogs = dogShelterService.findAllDogs();
        if (dogs == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dogs);
    }

    @PutMapping
    @Operation(
            summary = "Обновить данные собаки",
            description = "Обновление данных собаки"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные собаки успешно обновлены",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собака не найдена",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Dog.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dog) {
        Dog updatedDog = dogShelterService.updateDog(dog);
        if (updatedDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDog);
    }

    @DeleteMapping("/{dogId}")
    @Operation(
            summary = "Удаление собаки по ее уникальному идентификатору",
            description = "Поиск собаки для удаления по ее уникальному идентификатору"
    )
    @Parameters(value = {
            @Parameter(name = "Уникальный идентификатор собаки", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Собака успешно удалена"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Собака не найдена"
            )
    })
    public ResponseEntity<Void> deleteDogById(@PathVariable("dogId") Long dogId) {
        dogShelterService.deleteDogById(dogId);
        return ResponseEntity.ok().build();
    }
}
