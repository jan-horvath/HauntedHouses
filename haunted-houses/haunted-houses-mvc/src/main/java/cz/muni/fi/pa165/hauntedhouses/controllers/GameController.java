package cz.muni.fi.pa165.hauntedhouses.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/game")
public class GameController {

    @RequestMapping(value = "/{param}", method = RequestMethod.GET)
    public String toGame(@PathVariable int param, Model model) {
        model.addAttribute("ppp", param);
        return "game";
    }
}
