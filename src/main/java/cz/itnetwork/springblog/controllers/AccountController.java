package cz.itnetwork.springblog.controllers;

import cz.itnetwork.springblog.models.dto.UserDTO;
import cz.itnetwork.springblog.models.exceptions.DuplicateEmailException;
import cz.itnetwork.springblog.models.exceptions.PasswordsDoNotEqualException;
import cz.itnetwork.springblog.models.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String renderLogin() {
        return "/pages/account/login";
    }

    @GetMapping("register")
    public String renderRegister(@ModelAttribute UserDTO user) {
        return "/pages/account/register";
    }

    @PostMapping("register")
    public String registerUser(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors())
            return renderRegister(userDTO);

        try { //pokusíme se vytvořit uživatele
            userService.create(userDTO, false); //false - nejedná se o admin uživatele
        } catch (DuplicateEmailException exception) {
            result.rejectValue("email", "error", "E-mail je již používán.");
            return "/pages/account/register";
        } catch (PasswordsDoNotEqualException exception) {
            result.rejectValue("password", "error", "Hesla se nerovnají.");
            result.rejectValue("confirmPassword", "error", "Hesla se nerovnají.");
            return "/pages/account/register";
        }

        redirectAttributes.addFlashAttribute("success", "Uživatel zaregistrován.");
        return "redirect:/account/login";
    }
}
