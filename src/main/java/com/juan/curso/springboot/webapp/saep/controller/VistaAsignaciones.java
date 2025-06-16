package com.juan.curso.springboot.webapp.saep.controller;

import com.juan.curso.springboot.webapp.saep.model.Asignaciones;
import com.juan.curso.springboot.webapp.saep.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vista/asignaciones") // Ruta base para agrupar endpoints
public class VistaAsignaciones {

    @Autowired
    private AsignacionRepository asignacionesRepository;

    // Listar todas las asignaciones
    @GetMapping("")
    public String listar(Model model) {
        model.addAttribute("asignaciones", asignacionesRepository.findAll());
        return "asignaciones"; // asignaciones.html
    }

    // Mostrar formulario (crear o editar)
    @GetMapping("/form")
    public String formulario(Model model, @RequestParam(required = false) Long id) {
        Asignaciones asignacion = (id != null) ?
                asignacionesRepository.findById(id).orElse(new Asignaciones()) :
                new Asignaciones();
        model.addAttribute("asignacion", asignacion);
        return "asignaciones_form"; // asignaciones_form.html
    }

    // Guardar o actualizar
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Asignaciones asignacion, RedirectAttributes ra) {
        asignacionesRepository.save(asignacion);
        ra.addFlashAttribute("mensaje", "Operación exitosa");
        return "redirect:/vista/asignaciones";
    }

    // Eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        asignacionesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Asignación eliminada");
        return "redirect:/vista/asignaciones";
    }
}