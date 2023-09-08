package com.example.group_pr.controllers;

import com.example.group_pr.model.DogOwner;
import com.example.group_pr.service.DogOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Класс-контроллер для объектов класса DogOwner
 *
 * @see DogOwner
 * @see DogOwnerService
 */
@RestController
@RequestMapping("/dog_owner")
@Tag(name = "DogOwners", description = "CRUD-операции для работы с владельцами собак")
public class DogOwnerController {

    private final DogOwnerService dogOwnerService;

    public DogOwnerController(DogOwnerService dogOwnerService) {
        this.dogOwnerService = dogOwnerService;
    }

    @PostMapping
    @Operation(
            summary = "Создать нового владельца собаки",
            description = "Создание нового владельца собаки с его уникальным идентификатором"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Владелец собаки успешно создан",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            )
    })
    public ResponseEntity<DogOwner> createDogOwner(@RequestBody DogOwner dogOwner) {
        DogOwner createdDogOwner = dogOwnerService.createDogOwner(dogOwner);
        return ResponseEntity.ok(createdDogOwner);
    }

    @GetMapping("/{dogOwnerId}")
    @Operation(
            summary = "Найти владельца собаки по ее уникальному идентификатору ",
            description = "Поиск владельца собаки по ее уникальному идентификатору"
    )
    @Parameters(value = {
            @Parameter(name = "Уникальный идентификатор владельца собаки", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Владелец собаки успешно найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Владелец собаки не найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<DogOwner> getCatOwnerById(@PathVariable("dogOwnerId") Long dogOwnerId) {
        DogOwner foundDogOwner = dogOwnerService.findDogOwnerById(dogOwnerId);
        if (foundDogOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundDogOwner);
    }

    @GetMapping("/findByChatId")
    @Operation(
            summary = "Найти владельца собаки по идентификатору чата",
            description = "Поиск владельца собаки по идентификатору чата"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Владелец собаки успешно найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Владелец собаки не найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<DogOwner> getCatOwnerByChatId(@RequestParam("chatId") Long chatId) {
        DogOwner foundCatOwner = dogOwnerService.findDogOwnerByChatId(chatId);
        if (foundCatOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundCatOwner);
    }

    @GetMapping
    @Operation(
            summary = "Найти список всех владельцев собак",
            description = "Показать список всех владельцев собак"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список владельцев собак успешно найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список владельцев собак не найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<Collection<DogOwner>> getAllDogOwners() {
        Collection<DogOwner> dogOwners = dogOwnerService.findAllDogOwners();
        if (dogOwners == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dogOwners);
    }

    @PutMapping
    @Operation(
            summary = "Обновить данные владельца собаки",
            description = "Обновление данных владельца собаки"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные владельца собаки успешно Обновлены",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Владелец собаки не найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = DogOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<DogOwner> updateDogOwner(@RequestBody DogOwner dogOwner) {
        DogOwner updatedDogOwner = dogOwnerService.updateDogOwner(dogOwner);
        if (updatedDogOwner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDogOwner);
    }

    @DeleteMapping("/{dogOwnerId}")
    @Operation(
            summary = "Удаление владельца собаки по его уникальному идентификатору",
            description = "Поиск владельца собаки для удаления по его уникальному идентификатору"
    )
    @Parameters(value = {
            @Parameter(name = "Уникальный идентификатор владельца собаки", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Владелец собаки успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Владелец собаки не найден"
            )
    })
    public ResponseEntity<Void> deleteDogOwnerById(@PathVariable("dogOwnerId") Long dogOwnerId) {
        dogOwnerService.deleteDogOwnerById(dogOwnerId);
        return ResponseEntity.ok().build();
    }
}
