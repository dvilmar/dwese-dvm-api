package org.iesalixar.daw2.dvm.dwese_dvm_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Champion;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.ChampionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/champions")
public class ChampionController {

    private static final Logger logger = LoggerFactory.getLogger(ChampionController.class);

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public ResponseEntity<List<Champion>> getAllChampions() {
        logger.info("Solicitando la lista de todos los campeones...");
        try {
            List<Champion> champions = championRepository.findAll();
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
            Optional<Champion> champion = championRepository.findById(id);
            return champion.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campeón no existe."));
        } catch (Exception e) {
            logger.error("Error al buscar el campeón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el campeón.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createChampion(@Valid @RequestBody Champion champion, Locale locale) {
        logger.info("Insertando nuevo campeón con código {}", champion.getCode());
        if (championRepository.existsByCode(champion.getCode())) {
            String errorMessage = messageSource.getMessage("msg.champion-controller.insert.codeExist", null, locale);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        championRepository.save(champion);
        return ResponseEntity.status(HttpStatus.CREATED).body(champion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChampion(@PathVariable Long id, @Valid @RequestBody Champion champion, Locale locale) {
        logger.info("Actualizando campeón con ID {}", id);
        if (!championRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campeón no existe.");
        }
        if (championRepository.existsChampionByCodeAndNotId(champion.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.champion-controller.update.codeExist", null, locale);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        champion.setId(id);
        championRepository.save(champion);
        return ResponseEntity.ok(champion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChampion(@PathVariable Long id) {
        logger.info("Eliminando campeón con ID {}", id);
        if (!championRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El campeón no existe.");
        }
        championRepository.deleteById(id);
        return ResponseEntity.ok("Campeón eliminado con éxito.");
    }
}
