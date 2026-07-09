package lv.bootcamp.shelter.controller;

import lombok.RequiredArgsConstructor;
import lv.bootcamp.shelter.form.AnimalForm;
import lv.bootcamp.shelter.model.AnimalType;
import lv.bootcamp.shelter.service.AnimalService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AnimalPageController {

    private final AnimalService animalService;

    @ModelAttribute
    public void addRoleFlags(Model model, Authentication authentication) {
        model.addAttribute("isAdmin", hasRole(authentication, "ROLE_ADMIN"));
        model.addAttribute("isUser", hasRole(authentication, "ROLE_USER"));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/animals")
    public String animals(Model model) {
        model.addAttribute("animals", animalService.findAll());
        return "animals";
    }

    @GetMapping("/animals/new")
    public String newAnimalForm(Model model, Authentication authentication) {
        if (!hasRole(authentication, "ROLE_ADMIN")) {
            return "redirect:/animals";
        }
        model.addAttribute("form", emptyForm());
        model.addAttribute("types", AnimalType.values());
        return "animals-new";
    }

    @PostMapping("/animals")
    public String createAnimal(@ModelAttribute("form") AnimalForm form) {
        animalService.createFromForm(form);
        return "redirect:/animals";
    }

    private AnimalForm emptyForm() {
        return new AnimalForm(null, null, null, null, null, null);
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
