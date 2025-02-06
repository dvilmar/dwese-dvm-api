package org.iesalixar.daw2.dvm.dwese_dvm_webapp.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AbilityCreateDTO {
    @NotEmpty(message = "{msg.ability.code.notEmpty}")
    @Size(max = 2, message = "{msg.ability.code.size}")
    private String code;

    @NotEmpty(message = "{msg.ability.name.notEmpty}")
    @Size(max = 100, message = "{msg.ability.name.size}")
    private String name;

    @NotEmpty(message = "{msg.ability.type.notEmpty}")
    @Size(max = 100, message = "{msg.ability.type.size}")
    private String type;

    @NotEmpty(message = "{msg.ability.cost.notEmpty}")
    @Min(value = 0, message = "{msg.ability.cost.min}")
    @Max(value = 100, message = "{msg.ability.cost.max}")
    private Integer cost;

    @NotEmpty(message = "{msg.ability.cooldown.notEmpty}")
    @Min(value = 0, message = "{msg.ability.cost.min}")
    @Max(value = 100, message = "{msg.ability.cost.max}")
    private Integer cooldown;

    @NotNull(message = "{msg.ability.champion.notNull}")
    private Long championId;
}
