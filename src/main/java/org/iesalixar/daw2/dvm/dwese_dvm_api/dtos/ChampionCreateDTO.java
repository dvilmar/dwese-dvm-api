package org.iesalixar.daw2.dvm.dwese_dvm_api.dtos;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
public class ChampionCreateDTO {
    @NotEmpty(message = "{msg.champion.code.notEmpty}")
    @Size(max = 2, message = "{msg.champion.code.size}")
    private String code;

    @NotEmpty(message = "{msg.champion.name.notEmpty}")
    @Size(max = 100, message = "{msg.champion.name.size}")
    private String name;

    @NotEmpty(message = "{msg.champion.name.notEmpty}")
    @Size(max = 100, message = "{msg.champion.role.size}")
    private String role;
}
