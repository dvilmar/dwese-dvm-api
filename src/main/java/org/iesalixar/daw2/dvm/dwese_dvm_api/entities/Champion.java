package org.iesalixar.daw2.dvm.dwese_dvm_api.entities;

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
    @Size(max = 50, message = "{msg.champion.code.size}")
    @Column(name = "code", nullable = false)
    private String code;

    @NotEmpty(message = "{msg.champion.name.notEmpty}")
    @Size(max = 255, message = "{msg.champion.name.size}")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "{msg.champion.rol.notEmpty}")
    @Size(max = 255, message = "{msg.champion.rol.size}")
    @Column(name = "rol", nullable = false)
    private String role;

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
     * @param role Rol/Posición del campeón.
     */

    public Champion(String code, String name, String role) {
        this.code = code;
        this.name = name;
        this.role = role;
    }
}
