package by.cher.spring.http.controller;

import by.cher.spring.database.entity.Role;
import by.cher.spring.database.repository.CompanyRepository;
import by.cher.spring.dto.UserDto;
import by.cher.spring.dto.UserReadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"user"})
@RequestMapping("/api/v1")
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> getRoles(){
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(Model model,
                        UserReadDto userReadDto) {
        model.addAttribute("user", userReadDto);
        return "greeting/hello";
    }

    @GetMapping(value = "/hello/{id}")
    public String hello(
            @RequestParam("age") Integer age,
            @RequestHeader("accept") String accept,
            @CookieValue("JSESSIONID") String jsessionId,
            @PathVariable("id") Integer id,
            Model model,
            UserReadDto userReadDto) {

        model.addAttribute("user", userReadDto);
        return "greeting/hello";
    }

    @GetMapping(value = "/bye")
    public String bye() {
        return "greeting/bye";
    }
}
