package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wojtek.system_ksiegowy.model.SystemUser;
import pl.wojtek.system_ksiegowy.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController
{
    private UserRepository userRepository;

    @Autowired
    public HomeController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser authenticatedUser = userRepository.findByLogin(authentication.getName());
        request.getSession().setAttribute("logged", authenticatedUser);
        /*model.addAttribute("logged", authenticatedUser);*/
        if (authentication.getPrincipal().equals("anonymousUser"))
            return "unloggedHome";
        else return "loggedHome";
    }

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping("/loginerror")
    public String loginError(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        AuthenticationException ex = (AuthenticationException)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        String message = ex.getMessage();
        model.addAttribute("loginError", message);
        return "login";
    }
}
