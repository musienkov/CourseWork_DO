package coursework.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class FloydWarshallController {

    @RequestMapping(value = "/find-path-FW", method = RequestMethod.POST)
    public ModelAndView findPathFW(ModelAndView modelAndView, @RequestParam("n") Integer n, @RequestParam("d") Integer d){

        System.out.println("n = "+n);
        System.out.println("d = "+d);

        return modelAndView;
    }
}
