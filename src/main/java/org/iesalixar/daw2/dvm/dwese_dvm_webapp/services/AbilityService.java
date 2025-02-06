package org.iesalixar.daw2.dvm.dwese_dvm_webapp.services;

import org.iesalixar.daw2.dvm.dwese_dvm_webapp.dtos.AbilityCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.dtos.AbilityDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Ability;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.mappers.AbilityMapper;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.AbilityRepository;
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
    private MessageSource messageSource;

    /**
     * Obtiene todas las habilidades y las convierte en una lista de AbilityDTO.
     *
     * @return Lista de AbilityDTO.
     */
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

    /**
     * Obtiene una habilidad por su ID y la convierte en un AbilityDTO.
     *
     * @param id Identificador único de la habilidad.
     * @return AbilityDTO de la habilidad encontrada.
     * @throws IllegalArgumentException Si la habilidad no existe.
     */
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

    /**
     * Crea una nueva habilidad en la base de datos.
     *
     * @param abilityCreateDTO DTO que contiene los datos de la habilidad a crear.
     * @param locale Idioma para los mensajes de error.
     * @return DTO de la habilidad creada.
     * @throws IllegalArgumentException Si ya existe una habilidad con el mismo nombre.
     */
    public AbilityDTO createAbility(AbilityCreateDTO abilityCreateDTO, Locale locale) {
        logger.info("Creando una nueva habilidad con nombre {}", abilityCreateDTO.getName());
        if (abilityRepository.existsByName(abilityCreateDTO.getName())) {
            String errorMessage = messageSource.getMessage("msg.ability-controller.insert.nameExist", null, locale);
            logger.warn("Error al crear habilidad: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Ability ability = abilityMapper.toEntity(abilityCreateDTO);
        Ability savedAbility = abilityRepository.save(ability);
        logger.info("Habilidad creada exitosamente con ID {}", savedAbility.getId());
        return abilityMapper.toDTO(savedAbility);
    }

    /**
     * Actualiza una habilidad existente en la base de datos.
     *
     * @param id Identificador de la habilidad a actualizar.
     * @param abilityCreateDTO DTO que contiene los nuevos datos de la habilidad.
     * @param locale Idioma para los mensajes de error.
     * @return DTO de la habilidad actualizada.
     * @throws IllegalArgumentException Si la habilidad no existe o si el nombre ya está en uso.
     */
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

        existingAbility.setName(abilityCreateDTO.getName());
        existingAbility.setDescription(abilityCreateDTO.getDescription());
        Ability updatedAbility = abilityRepository.save(existingAbility);
        logger.info("Habilidad con ID {} actualizada exitosamente.", id);
        return abilityMapper.toDTO(updatedAbility);
    }

    /**
     * Elimina una habilidad específica por su ID.
     *
     * @param id Identificador único de la habilidad.
     * @throws IllegalArgumentException Si la habilidad no existe.
     */
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
