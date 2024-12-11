package org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La clase 'Ability' representa una entidad que modela una habilidad dentro de la base de datos.
 *
 * Las anotaciones de Lombok ayudan a reducir el código repetitivo al generar automáticamente
 * métodos comunes como getters, setters, constructores, y otros métodos estándar de los objetos.
 */

@Entity
@Table(name = "abilities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{msg.ability.code.notEmpty}")
    @Size(max = 2, message = "{msg.ability.code.size}")
    @Column(name = "code", nullable = false, length = 2)
    private String code;

    @NotEmpty(message = "{msg.ability.name.notEmpty}")
    @Size(max = 2, message = "{msg.ability.name.size}")
    @Column(name = "name", nullable = false, length = 2)
    private String name;

    @NotEmpty(message = "{msg.ability.description.notEmpty}")
    @Size(max = 2, message = "{msg.ability.description.size}")
    @Column(name = "description", nullable = false, length = 2)
    private String description;

    @NotEmpty(message = "{msg.ability.type.notEmpty}")
    @Size(max = 2, message = "{msg.ability.type.size}")
    @Column(name = "type", nullable = false, length = 2)
    private String type;

    @NotEmpty(message = "{msg.ability.cost.notEmpty}")
    @Size(max = 100, message = "{msg.ability.cost.size}")
    @Column(name = "cost", nullable = false, length = 100)
    private Integer cost;

    @NotEmpty(message = "{msg.ability.cooldown.notEmpty}")
    @Size(max = 100, message = "{msg.ability.cooldown.size}")
    @Column(name = "cooldown", nullable = false, length = 100)
    private Integer cooldown;

    // Relación con la entidad 'Champion', representando el campeón al que pertenece la habilidad.
    @NotNull(message = "{msg.province.region.notNull}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "champion_id", nullable = false)
    private Champion champion;

    /**
     * Este es un constructor personalizado que no incluye el campo 'id'.
     * Se utiliza para crear instancias de 'Ability' cuando no es necesario o no se conoce el 'id' de la habilidades
     * (por ejemplo, antes de insertar la habilidades en la base de datos, donde el 'id' es autogenerado).
     * @param code Código de la habilidad.
     * @param name Nombre de la habilidad.
     * @param description Descripcion de la habilidad.
     * @param type Tipo de habilidad.
     * @param cost Coste de la habilidad.
     * @param cooldown Tiempo de enfriamiento de la habilidad.
     */

    public Ability(Champion champion, Integer cooldown, Integer cost, String type, String description, String name, String code) {
        this.champion = champion;
        this.cooldown = cooldown;
        this.cost = cost;
        this.type = type;
        this.description = description;
        this.name = name;
        this.code = code;
    }
}
