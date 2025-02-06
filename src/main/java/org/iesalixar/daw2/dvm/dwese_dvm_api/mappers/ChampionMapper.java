package org.iesalixar.daw2.dvm.dwese_dvm_api.mappers;

import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.dtos.ChampionDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_api.entities.Champion;
import org.springframework.stereotype.Component;

@Component
public class ChampionMapper {
    public ChampionDTO toDTO(Champion champion) {
        ChampionDTO dto = new ChampionDTO(champion.getId(), champion.getCode(), champion.getName(), champion.getRole());
        return dto;
    }

    public Champion toEntity(ChampionDTO dto) {
        Champion champion = new Champion();
        champion.setId(dto.getId());
        champion.setCode(dto.getCode());
        champion.setName(dto.getName());
        champion.setRole(dto.getRole());
        return champion;
    }

    public Champion toEntity(ChampionCreateDTO createDTO) {
        Champion champion = new Champion();
        champion.setCode(createDTO.getCode());
        champion.setName(createDTO.getName());
        champion.setRole(createDTO.getRole());
        return champion;
    }
}
