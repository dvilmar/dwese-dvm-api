package org.iesalixar.daw2.dvm.dwese_dvm_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.services.ChampionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/champions")
public class ChampionController {

    private static final Logger logger = LoggerFactory.getLogger(ChampionController.class);

    @Autowired
    private ChampionService championService;


    @Operation(summary = "Obtener todos los campeones", description = "Devuelve una lista de todos los campeones disponibles en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de campeones recuperada exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<ChampionDTO>> getAllChampions(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        logger.info("Solicitando la lista de todos los campeones...", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<ChampionDTO> champions = championService.getAllChampions(pageable);
            logger.info("Se han encontrado {} campeones.", champions.getNumberOfElements());
            return ResponseEntity.ok(champions);
        } catch (Exception e) {
            logger.error("Error al listar los campeones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener un campeón por ID", description = "Recupera un campeón específico según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campeón encontrado", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "404", description = "Campeón no encontrado"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getChampionById(@PathVariable Long id) {
        logger.info("Buscando campeón con ID {}", id);
        try {
            ChampionDTO championDTO = championService.getChampionById(id);
            return ResponseEntity.ok(championDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("No se encontró el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al buscar el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el campeón.");
        }
    }

    @Operation(summary = "Crear nuevo campeón", description = "Permite registrar un nuevo campeón en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Campeón creado exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "400", description = "Datos inválidos proporcionados"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createChampion(@Valid @RequestBody ChampionCreateDTO championCreateDTO, Locale locale) {
        logger.info("Creando un nuevo campeón con nombre {}", championCreateDTO.getName());
        try {
            ChampionDTO createdChampion = championService.createChampion(championCreateDTO, locale);
            logger.info("Campeón creado exitosamente con ID {}", createdChampion.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdChampion);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear el campeón: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear el campeón: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el campeón.");
        }
    }

    @Operation(summary = "Actualizar un campeón", description = "Permite actualizar los datos de un campeón existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campeón actualizado exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "400", description = "Datos inválidos"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateChampion(@PathVariable Long id, @Valid @RequestBody ChampionCreateDTO championCreateDTO, Locale locale) {
        logger.info("Actualizando campeón con ID {}", id);
        try {
            ChampionDTO updatedChampion = championService.updateChampion(id, championCreateDTO, locale);
            logger.info("Campeón con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedChampion);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el campeón.");
        }
    }

    @Operation(summary = "Eliminar un campeón", description = "Permite eliminar un campeón específico de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campeón eliminado exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "404", description = "Campeón no encontrado"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChampion(@PathVariable Long id) {
        logger.info("Eliminando campeón con ID {}", id);
        try {
            championService.deleteChampion(id);
            logger.info("Campeón con ID {} eliminado exitosamente.", id);
            return ResponseEntity.ok("Campeón eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el campeón.");
        }
    }
}
