package org.iesalixar.daw2.dvm.dwese_dvm_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Ability;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.AbilityRepository;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.ChampionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/abilities")
public class AbilityController {

    private static final Logger logger = LoggerFactory.getLogger(AbilityController.class);

    @Autowired
    private AbilityRepository abilityRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ChampionRepository championRepository;

    @GetMapping
    public ResponseEntity<List<Ability>> getAllAbilities() {
        logger.info("Solicitando la lista de todas las habilidades...");
        List<Ability> listAbilities = abilityRepository.findAll();
        logger.info("Se han encontrado {} habilidades.", listAbilities.size());
        return ResponseEntity.ok(listAbilities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAbilityById(@PathVariable Long id) {
        logger.info("Buscando habilidad con ID {}", id);
        Optional<Ability> ability = abilityRepository.findById(id);
        return ability.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habilidad no encontrada"));
    }

    @PostMapping
    public ResponseEntity<?> createAbility(@Valid @RequestBody Ability ability, Locale locale) {
        logger.info("Creando una nueva habilidad con código {}", ability.getCode());
        if (abilityRepository.existsByCode(ability.getCode())) {
            String errorMessage = messageSource.getMessage("msg.ability-controller.insert.codeExist", null, locale);
            logger.warn("El código de la habilidad {} ya existe.", ability.getCode());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        Ability savedAbility = abilityRepository.save(ability);
        logger.info("Habilidad {} creada exitosamente.", ability.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAbility);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAbility(@PathVariable Long id, @Valid @RequestBody Ability ability, Locale locale) {
        logger.info("Actualizando habilidad con ID {}", id);
        if (!abilityRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habilidad no encontrada");
        }
        if (abilityRepository.existsAbilityByCodeAndNotId(ability.getCode(), id)) {
            String errorMessage = messageSource.getMessage("msg.ability-controller.update.codeExist", null, locale);
            logger.warn("El código de la habilidad {} ya existe para otra habilidad.", ability.getCode());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        ability.setId(id);
        Ability updatedAbility = abilityRepository.save(ability);
        logger.info("Habilidad con ID {} actualizada exitosamente.", id);
        return ResponseEntity.ok(updatedAbility);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAbility(@PathVariable Long id) {
        logger.info("Eliminando habilidad con ID {}", id);
        if (!abilityRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habilidad no encontrada");
        }
        try {
            abilityRepository.deleteById(id);
            logger.info("Habilidad con ID {} eliminada exitosamente.", id);
            return ResponseEntity.ok("Habilidad eliminada con éxito.");
        } catch (RuntimeException e) {
            logger.error("Error al eliminar la habilidad con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la habilidad.");
        }
    }
}