package org.iesalixar.daw2.dvm.dwese_dvm_webapp.mappers;

import org.iesalixar.daw2.dvm.dwese_dvm_webapp.dtos.AbilityCreateDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.dtos.AbilityDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.dtos.ChampionDTO;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Ability;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Champion;
import org.springframework.stereotype.Component;

@Component
public class AbilityMapper {
    public AbilityDTO toDTO(Ability ability) {
        if (ability == null) {
            return null;
        }

        AbilityDTO abilityDTO = new AbilityDTO();
        abilityDTO.setId(ability.getId());
        abilityDTO.setCode(ability.getCode());
        abilityDTO.setName(ability.getName());
        abilityDTO.setType(ability.getType());
        abilityDTO.setCost(ability.getCost());
        abilityDTO.setCooldown(ability.getCooldown());

        // Mapear el campeón asociado si está presente
        if (ability.getChampion() != null) {
            Champion champion = ability.getChampion();
            ChampionDTO championDTO = new ChampionDTO(champion.getId(), champion.getCode(), champion.getName(), champion.getRole());
        }

        return abilityDTO;
    }

    public Ability toEntity(AbilityCreateDTO createDTO, Champion champion) {
        if (createDTO == null) {
            return null;
        }

        Ability ability = new Ability();
        ability.setCode(createDTO.getCode());
        ability.setCode(ability.getCode());
        ability.setName(ability.getName());
        ability.setType(ability.getType());
        ability.setCost(ability.getCost());
        ability.setCooldown(ability.getCooldown());
        ability.setChampion(ability.getChampion());

        return ability;
    }
}
