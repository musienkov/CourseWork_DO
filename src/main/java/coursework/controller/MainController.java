package coursework.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
import java.util.List;
@Controller
public class MainController {
    @GetMapping({"/", "/index"})
    public ModelAndView welcomePage(ModelAndView modelAndView) {


        modelAndView.setViewName("/index");
        return modelAndView;
    }
    @GetMapping("/about_program")
    public ModelAndView aboutProgram(ModelAndView modelAndView) {


        modelAndView.setViewName("/about_program");
        return modelAndView;
    }

}
