package org.iesalixar.daw2.dvm.dwese_dvm_api.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.services.AbilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<AbilityDTO>> getAllAbilities() {
        logger.info("Solicitando la lista de todas las habilidades...");
        try {
            List<AbilityDTO> abilities = abilityService.getAllAbilities();
            logger.info("Se han encontrado {} habilidades.", abilities.size());
            return ResponseEntity.ok(abilities);
        } catch (Exception e) {
            logger.error("Error al listar las habilidades: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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
