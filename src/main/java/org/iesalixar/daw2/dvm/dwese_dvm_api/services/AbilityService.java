package org.iesalixar.daw2.dvm.dwese_dvm_api.services;

import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Ability;
import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Champion;
import org.iesalixar.daw2.dvm.dwese_dvm_api.mappers.AbilityMapper;
import org.iesalixar.daw2.dvm.dwese_dvm_api.repositories.AbilityRepository;
import org.iesalixar.daw2.dvm.dwese_dvm_api.repositories.ChampionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AbilityService {

    private static final Logger logger = LoggerFactory.getLogger(AbilityService.class);

    @Autowired
    private AbilityRepository abilityRepository;

    @Autowired
    private AbilityMapper abilityMapper;

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private MessageSource messageSource;

    public List<AbilityDTO> getAllAbilities() {
        logger.info("Solicitando todas las habilidades...");
        try {
            List<Ability> abilities = abilityRepository.findAll();
            logger.info("Se han encontrado {} habilidades.", abilities.size());
            return abilities.stream()
                    .map(abilityMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error al obtener la lista de habilidades: {}", e.getMessage());
            throw e;
        }
    }

    public AbilityDTO getAbilityById(Long id) {
        logger.info("Buscando habilidad con ID {}", id);
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la habilidad con ID {}", id);
                    return new IllegalArgumentException("La habilidad no existe.");
                });
        logger.info("Habilidad con ID {} encontrada.", id);
        return abilityMapper.toDTO(ability);
    }

    public AbilityDTO createAbility(AbilityCreateDTO abilityCreateDTO, Locale locale) {
        logger.info("Creando una nueva habilidad con nombre {}", abilityCreateDTO.getName());
        if (abilityRepository.existsByName(abilityCreateDTO.getName())) {
            String errorMessage = messageSource.getMessage("msg.ability-controller.insert.nameExist", null, locale);
            logger.warn("Error al crear habilidad: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Champion champion = championRepository.findById(abilityCreateDTO.getChampionId())
                .orElseThrow(() -> new IllegalArgumentException("El campeón especificado no existe."));

        Ability ability = abilityMapper.toEntity(abilityCreateDTO, champion);
        Ability savedAbility = abilityRepository.save(ability);
        logger.info("Habilidad creada exitosamente con ID {}", savedAbility.getId());
        return abilityMapper.toDTO(savedAbility);
    }

    public AbilityDTO updateAbility(Long id, AbilityCreateDTO abilityCreateDTO, Locale locale) {
        logger.info("Actualizando habilidad con ID {}", id);
        Ability existingAbility = abilityRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la habilidad con ID {}", id);
                    return new IllegalArgumentException("La habilidad no existe.");
                });

        if (abilityRepository.existsAbilityByNameAndNotId(abilityCreateDTO.getName(), id)) {
            String errorMessage = messageSource.getMessage("msg.ability-controller.update.nameExist", null, locale);
            logger.warn("Error al actualizar habilidad: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Champion champion = championRepository.findById(abilityCreateDTO.getChampionId())
                .orElseThrow(() -> new IllegalArgumentException("El campeón especificado no existe."));

        existingAbility.setName(abilityCreateDTO.getName());
        existingAbility.setType(abilityCreateDTO.getType());
        existingAbility.setCost(abilityCreateDTO.getCost());
        existingAbility.setCooldown(abilityCreateDTO.getCooldown());
        existingAbility.setChampion(champion);

        Ability updatedAbility = abilityRepository.save(existingAbility);
        logger.info("Habilidad con ID {} actualizada exitosamente.", id);
        return abilityMapper.toDTO(updatedAbility);
    }

    public void deleteAbility(Long id) {
        logger.info("Buscando habilidad con ID {}", id);
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la habilidad con ID {}", id);
                    return new IllegalArgumentException("La habilidad no existe.");
                });

        abilityRepository.deleteById(id);
        logger.info("Habilidad con ID {} eliminada exitosamente.", id);
    }
}
