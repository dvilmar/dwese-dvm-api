package org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * La clase 'Champion' representa una entidad que modela un campeón dentro de la base de datos.
 *
 * Las anotaciones de Lombok ayudan a reducir el código repetitivo al generar automáticamente
 * métodos comunes como getters, setters, constructores, y otros métodos estándar de los objetos.
 */

@Entity
@Table(name = "champions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Champion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "{msg.champion.code.notEmpty}")
    @Size(max = 2, message = "{msg.champion.code.size}")
    @Column(name = "code", nullable = false, length = 2)
    private String code;

    @NotEmpty(message = "{msg.champion.name.notEmpty}")
    @Size(max = 2, message = "{msg.champion.name.size}")
    @Column(name = "name", nullable = false, length = 2)
    private String name;

    @NotEmpty(message = "{msg.champion.rol.notEmpty}")
    @Size(max = 2, message = "{msg.champion.rol.size}")
    @Column(name = "rol", nullable = false, length = 2)
    private String rol;

    @NotEmpty(message = "{msg.champion.lore.notEmpty}")
    @Size(max = 2, message = "{msg.champion.lore.size}")
    @Column(name = "lore", nullable = false, length = 2)
    private String lore;

    /*@NotEmpty(message = "{msg.champion.image.notEmpty}")
    @Size(max = 100, message = "{msg.champion.image.size}")
    @Column(name = "image", nullable = false, length = 100)
    private Integer image;*/

    // Relación uno a muchos con la entidad Ability.
    // Un campeón puede tener muchas habilidades.
    @OneToMany(mappedBy = "champion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ability> abilities;

    /**
     * Este es un constructor personalizado que no incluye el campo 'id'.
     * Se utiliza para crear instancias de 'Champion' cuando no es necesario o no se conoce el 'id' del campeón
     * (por ejemplo, antes de insertar el campeón en la base de datos, donde el 'id' es autogenerado).
     * @param code Código del campeón.
     * @param name Nombre del campeón.
     * @param rol Rol/Posición del campeón.
     * @param lore Historia del campeón.
     */

    public Champion(String code, String name, String rol, String lore) {
        this.code = code;
        this.name = name;
        this.rol = rol;
        this.lore = lore;
    }
}
