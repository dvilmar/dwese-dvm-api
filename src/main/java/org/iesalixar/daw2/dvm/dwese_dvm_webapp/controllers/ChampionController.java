package org.iesalixar.daw2.dvm.dwese_dvm_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.repositories.ChampionRepository;
import org.iesalixar.daw2.dvm.dwese_dvm_webapp.entities.Champion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * Controlador que maneja las operaciones CRUD para la entidad `Champion`.
 * Utiliza `ChampionDAO` para interactuar con la base de datos.
 */
@Controller
@RequestMapping("/champions")
public class ChampionController {


    private static final Logger logger = LoggerFactory.getLogger(ChampionController.class);


    // DAO para gestionar las operaciones de los campeones en la base de datos
    @Autowired
    private ChampionRepository championRepository;
    @Autowired
    private MessageSource messageSource;


    /**
     * Lista todos los campeones y las pasa como atributo al modelo para que sean
     * accesibles en la vista `champion.html`.
     *
     * @param model Objeto del modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de campeones.
     */
    @GetMapping
    public String listChampions(Model model) {
        logger.info("Solicitando la lista de todos los campeones...");
        List<Champion> listChampions = null;
        listChampions = championRepository.findAll();
        logger.info("Se han cargado {} campeones.", listChampions.size());
        model.addAttribute("listChampions", listChampions); // Pasar la lista de campeones al modelo
        return "champion"; // Nombre de la plantilla Thymeleaf a renderizar
    }


    /**
     * Muestra el formulario para crear una nueva .
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva .");
        model.addAttribute("champion", new Champion()); // Crear un nuevo objeto Champion
        return "champion-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }


    /**
     * Muestra el formulario para editar un campeón existente.
     *
     * @param id    ID del campeón a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para el campeón con ID {}", id);
        Optional<Champion> champion = championRepository.findById(id);
        model.addAttribute("champion", champion.get());
        return "champion-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }


    /**
     * Inserta una nueva campeón en la base de datos.
     *
     * @param champion              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de campeones.
     */
    @PostMapping("/insert")
    public String insertChampion(@Valid @ModelAttribute("champion") Champion champion, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nueva campeón con código {}", champion.getCode());
        if (result.hasErrors()) {
            return "champion-form";  // Devuelve el formulario para mostrar los errores de validación
        }
        if (championRepository.existsByCode(champion.getCode())) {
            logger.warn("El código del campeón {} ya existe.", champion.getCode());
            String errorMessage = messageSource.getMessage("msg.champion-controller.insert.codeExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/champions/new";
        }
        championRepository.save(champion);
        logger.info("Campeón {} insertada con éxito.", champion.getCode());
        return "redirect:/champions"; // Redirigir a la lista de campeones
    }


    /**
     * Actualiza un campeón existente en la base de datos.
     *
     * @param champion              Objeto que contiene los datos del formulario.
     * @param redirectAttributes  Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de campeones.
     */
    @PostMapping("/update")
    public String updateChampion(@Valid @ModelAttribute("champion") Champion champion, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando campeón con ID {}", champion.getId());
        if (result.hasErrors()) {
            return "champion-form";  // Devuelve el formulario para mostrar los errores de validación
        }
        if (championRepository.existsChampionByCodeAndNotId(champion.getCode(), champion.getId())) {
            logger.warn("El código del campeón {} ya existe para otra campeón.", champion.getCode());
            String errorMessage = messageSource.getMessage("msg.champion-controller.update.codeExist", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/champions/edit?id=" + champion.getId();
        }
        championRepository.save(champion);
        logger.info("Campeón con ID {} actualizada con éxito.", champion.getId());
        return "redirect:/champions"; // Redirigir a la lista de campeones
    }


    /**
     * Elimina un campeón de la base de datos.
     *
     * @param id                 ID del campeón a eliminar.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @return Redirección a la lista de campeones.
     */
    @PostMapping("/delete")
    public String deleteChampion(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando campeón con ID {}", id);
        try {
            championRepository.deleteById(id);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "msg.champion-controller.delete.error");
            return "redirect:/abilities";
        }
        logger.info("Campeón con ID {} eliminada con éxito.", id);
        return "redirect:/champions"; // Redirigir a la lista de campeones
    }
}