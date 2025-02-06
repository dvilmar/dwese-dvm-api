package org.iesalixar.daw2.dvm.dwese_dvm_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbilityDTO {
    private Long id;
    private String code;
    private String name;
    private String type;
    private Integer cost;
    private Integer cooldown;
    private ChampionDTO champion;
}
