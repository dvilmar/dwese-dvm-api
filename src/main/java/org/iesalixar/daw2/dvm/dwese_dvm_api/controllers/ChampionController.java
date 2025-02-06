package org.iesalixar.daw2.dvm.dwese_dvm_api.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.services.ChampionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<ChampionDTO>> getAllChampions() {
        logger.info("Solicitando la lista de todos los campeones...");
        try {
            List<ChampionDTO> champions = championService.getAllChampions();
            logger.info("Se han encontrado {} campeones.", champions.size());
            return ResponseEntity.ok(champions);
        } catch (Exception e) {
            logger.error("Error al listar los campeones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

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
