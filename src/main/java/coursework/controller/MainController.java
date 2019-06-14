package coursework.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
