package org.iesalixar.daw2.dvm.dwese_dvm_api.services;

import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.AbilityDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Ability;
import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Champion;
import org.iesalixar.daw2.dvm.dwese_dvm_api.mappers.ChampionMapper;
import org.iesalixar.daw2.dvm.dwese_dvm_api.repositories.ChampionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ChampionService {

    private static final Logger logger = LoggerFactory.getLogger(ChampionService.class);

    @Autowired
    private ChampionRepository championRepository;

    @Autowired
    private ChampionMapper championMapper;

    @Autowired
    private MessageSource messageSource;

    public Page<ChampionDTO> getAllChampions(Pageable pageable) {
        logger.info("Solicitando todas las habilidades...", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Champion> champions = championRepository.findAll(pageable);
            logger.info("Se han encontrado {} habilidades.", champions.getNumberOfElements());
            return champions.map(championMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener la lista de habilidades: {}", e.getMessage());
            throw e;
        }
    }

    public ChampionDTO getChampionById(Long id) {
        logger.info("Buscando campeón con ID {}", id);
        Champion champion = championRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el campeón con ID {}", id);
                    return new IllegalArgumentException("El campeón no existe.");
                });
        logger.info("Campeón con ID {} encontrado.", id);
        return championMapper.toDTO(champion);
    }

    public ChampionDTO createChampion(ChampionCreateDTO championCreateDTO, Locale locale) {
        logger.info("Creando un nuevo campeón con nombre {}", championCreateDTO.getName());
        if (championRepository.existsByName(championCreateDTO.getName())) {
            String errorMessage = messageSource.getMessage("msg.champion-controller.insert.nameExist", null, locale);
            logger.warn("Error al crear campeón: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        Champion champion = championMapper.toEntity(championCreateDTO);
        Champion savedChampion = championRepository.save(champion);
        logger.info("Campeón creado exitosamente con ID {}", savedChampion.getId());
        return championMapper.toDTO(savedChampion);
    }

    public ChampionDTO updateChampion(Long id, ChampionCreateDTO championCreateDTO, Locale locale) {
        logger.info("Actualizando campeón con ID {}", id);
        Champion existingChampion = championRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el campeón con ID {}", id);
                    return new IllegalArgumentException("El campeón no existe.");
                });

        if (championRepository.existsChampionByNameAndNotId(championCreateDTO.getName(), id)) {
            String errorMessage = messageSource.getMessage("msg.champion-controller.update.nameExist", null, locale);
            logger.warn("Error al actualizar campeón: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        existingChampion.setCode(championCreateDTO.getCode());
        existingChampion.setName(championCreateDTO.getName());
        existingChampion.setRole(championCreateDTO.getRole());

        Champion updatedChampion = championRepository.save(existingChampion);
        logger.info("Campeón con ID {} actualizado exitosamente.", id);
        return championMapper.toDTO(updatedChampion);
    }

    public void deleteChampion(Long id) {
        logger.info("Buscando campeón con ID {}", id);
        Champion champion = championRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No se encontró el campeón con ID {}", id);
                    return new IllegalArgumentException("El campeón no existe.");
                });

        championRepository.deleteById(id);
        logger.info("Campeón con ID {} eliminado exitosamente.", id);
    }
}
