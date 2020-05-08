package cz.muni.fi.pa165.hauntedhouses.controllers;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
@RequestMapping("/auth")
public class AuthController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String securePage() {
        return "auth/test";
    }
}
