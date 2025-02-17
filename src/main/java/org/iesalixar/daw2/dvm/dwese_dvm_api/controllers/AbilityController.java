package org.iesalixar.daw2.dvm.dwese_dvm_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.services.AbilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/abilities")
public class AbilityController {

    private static final Logger logger = LoggerFactory.getLogger(AbilityController.class);

    @Autowired
    private AbilityService abilityService;

    @Operation(summary = "Obtener todas las habilidades", description = "Devuelve una lista de todas las habilidades disponibles en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de habilidades recuperada exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<Page<AbilityDTO>> getAllAbilities(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        logger.info("Solicitando la lista de todas las habilidades...", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<AbilityDTO> abilities = abilityService.getAllAbilities(pageable);
            logger.info("Se han encontrado {} habilidades.", abilities.getNumberOfElements());
            return ResponseEntity.ok(abilities);
        } catch (Exception e) {
            logger.error("Error al listar las habilidades: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener una habilidad por ID", description = "Recupera una habilidad específica según su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidad encontrada", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "404", description = "Habilidad no encontrada"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getAbilityById(@PathVariable Long id) {
        logger.info("Buscando habilidad con ID {}", id);
        try {
            AbilityDTO abilityDTO = abilityService.getAbilityById(id);
            logger.info("Habilidad con ID {} encontrada.", id);
            return ResponseEntity.ok(abilityDTO);
        } catch (IllegalArgumentException e) {
            logger.warn("No se encontró la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al buscar la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la habilidad.");
        }
    }

    @Operation(summary = "Crear nueva habilidad", description = "Permite registrar una nueva habilidad en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Habilidad creada exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "400", description = "Datos inválidos proporcionados"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> createAbility(
            @Valid @RequestBody AbilityCreateDTO abilityCreateDTO,
            Locale locale) {
        logger.info("Creando una nueva habilidad con nombre {}", abilityCreateDTO.getName());
        try {
            AbilityDTO createdAbility = abilityService.createAbility(abilityCreateDTO, locale);
            logger.info("Habilidad creada exitosamente con ID {}", createdAbility.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAbility);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear la habilidad: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al crear la habilidad: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la habilidad.");
        }
    }

    @Operation(summary = "Actualizar una habilidad", description = "Permite actualizar los datos de una habilidad existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidad actualizada exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "400", description = "Datos inválidos"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAbility(
            @PathVariable Long id,
            @Valid @RequestBody AbilityCreateDTO abilityCreateDTO,
            Locale locale) {
        logger.info("Actualizando habilidad con ID {}", id);
        try {
            AbilityDTO updatedAbility = abilityService.updateAbility(id, abilityCreateDTO, locale);
            logger.info("Habilidad con ID {} actualizada exitosamente.", id);
            return ResponseEntity.ok(updatedAbility);
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la habilidad.");
        }
    }

    @Operation(summary = "Eliminar una habilidad", description = "Permite eliminar una habilidad específica de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habilidad eliminada exitosamente", content = @Content(mediaType="application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ChampionDTO.class)))), @ApiResponse (responseCode = "404", description = "Habilidad no encontrada"), @ApiResponse (responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAbility(@PathVariable Long id) {
        logger.info("Eliminando habilidad con ID {}", id);
        try {
            abilityService.deleteAbility(id);
            logger.info("Habilidad con ID {} eliminada exitosamente.", id);
            return ResponseEntity.ok("Habilidad eliminada con éxito.");
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la habilidad.");
        }
    }
}
