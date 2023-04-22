package co.jp.groves.app.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {

    @GetMapping("/loginForm")
    String loginForm() {
        return "login/form";
    }
}
