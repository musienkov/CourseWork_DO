package coursework.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;
@Controller
public class InputDataController {

    @GetMapping("/input_data")
    public ModelAndView inputData(ModelAndView modelAndView) {


        modelAndView.setViewName("/input_data");
        return modelAndView;
    }

}
