package org.iesalixar.daw2.dvm.dwese_dvm_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionDTO {
    private Long id;
    private String name;
    private String code;
    private String role;
}