package mx.edu.utez.sda.u2p1.u2p1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerDrive {
    @Autowired
    PasswordEncoder encoder;
    @GetMapping("drive")
    //SOLO COMILLAS SI ES UN ROL
    @Secured({"ROLE_ADMIN"})
    public String drive(){
        return "drive";
    }
}
