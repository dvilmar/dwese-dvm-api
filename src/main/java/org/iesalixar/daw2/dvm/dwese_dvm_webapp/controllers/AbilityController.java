package org.iesalixar.daw2.dvm.dwese_dvm_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Champion;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.AbilityRepository;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.ChampionRepository;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Ability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


/**
 * Controlador que maneja las operaciones CRUD para la entidad `Ability`.
 * Utiliza `AbilityDAO` para interactuar con la base de datos.
 */
@Controller
@RequestMapping("/abilities")
public class AbilityController {


    private static final Logger logger = LoggerFactory.getLogger(AbilityController.class);


    // DAO para gestionar las operaciones de las habilidades en la base de datos
    @Autowired
    private AbilityRepository abilityRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ChampionRepository championRepository;


    /**
     * Lista todas las habilidades y las pasa como atributo al modelo para que sean
     * accesibles en la vista `ability.html`.
     *
     * @param model Objeto del modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de habilidades.
     */
    @GetMapping
    public String listAbilities(Model model) {
        logger.info("Solicitando la lista de todas las habilidades...");
        List<Ability> listAbilities = null;
        listAbilities = abilityRepository.findAll();
        logger.info("Se han cargado {} habilidades.", listAbilities.size());
        model.addAttribute("listAbilities", listAbilities); // Pasar la lista de habilidades al modelo
        return "ability"; // Nombre de la plantilla Thymeleaf a renderizar
    }


    /**
     * Muestra el formulario para crear una nueva habilidad.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva habilidad.");
        model.addAttribute("ability", new Ability()); // Crear un nuevo objeto Ability
        model.addAttribute("champions", championRepository.findAll()); // Lista de championes
        return "ability-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }


    /**
     * Muestra el formulario para editar una habilidad existente.
     *
     * @param id    ID de la habilidad a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para la habilidad con ID {}", id);
        Optional<Ability> ability = abilityRepository.findById(id);
        List<Champion> champions = championRepository.findAll();
        model.addAttribute("ability", ability.get());
        model.addAttribute("champions", champions); // Lista de championes
        return "ability-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }


    /**
     * Inserta una nueva habilidad en la base de datos.
     *
     * @param ability              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de habilidades.
     */
    @PostMapping("/insert")
    public String insertAbility(@Valid @ModelAttribute("ability") Ability ability, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        logger.info("Insertando nueva habilidad con código {}", ability.getCode());
        if (result.hasErrors()) {
            model.addAttribute("champions", championRepository.findAll()); // Lista de championes
            return "ability-form";  // Devuelve el formulario para mostrar los errores de validación
        }
        if (abilityRepository.existsByCode(ability.getCode())) {
            logger.warn("El código de la habilidad {} ya existe.", ability.getCode());
            String errorMessage = messageSource.getMessage("msg.ability-controller.insert.codeExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/abilities/new";
        }
        abilityRepository.save(ability);
        logger.info("Habilidad {} insertada con éxito.", ability.getCode());
        return "redirect:/abilities"; // Redirigir a la lista de habilidades
    }


    /**
     * Actualiza una habilidad existente en la base de datos.
     *
     * @param ability              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de habilidades.
     */
    @PostMapping("/update")
    public String updateAbility(@Valid @ModelAttribute("ability") Ability ability, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        logger.info("Actualizando habilidad con ID {}", ability.getId());
        if (result.hasErrors()) {
            model.addAttribute("champions", championRepository.findAll()); // Lista de championes
            return "ability-form";  // Devuelve el formulario para mostrar los errores de validación
        }
        if (abilityRepository.existsAbilityByCodeAndNotId(ability.getCode(), ability.getId())) {
            logger.warn("El código de la habilidad {} ya existe para otra habilidad.", ability.getCode());
            String errorMessage = messageSource.getMessage("msg.ability-controller.update.codeExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/abilities/edit?id=" + ability.getId();
        }
        abilityRepository.save(ability);
        logger.info("Habilidad con ID {} actualizada con éxito.", ability.getId());
        return "redirect:/abilities"; // Redirigir a la lista de habilidades
    }


    /**
     * Elimina una habilidad de la base de datos.
     *
     * @param id                 ID de la habilidad a eliminar.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de habilidades.
     */
    @PostMapping("/delete")
    public String deleteAbility(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando habilidad con ID {}", id);
        try {
            abilityRepository.deleteById(id);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "msg.ability-controller.delete.error");
            return "redirect:/abilities";
        }
        logger.info("Habilidad con ID {} eliminada con éxito.", id);
        return "redirect:/abilities"; // Redirigir a la lista de habilidades
    }
}