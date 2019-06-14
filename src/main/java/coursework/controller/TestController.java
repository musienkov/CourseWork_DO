package coursework.controller;

import coursework.model.Box;
import coursework.model.Graph;
import coursework.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    DataGeneratorService dataService;

    @Autowired
    BoxService boxService;

    @Autowired
    GraphService graphService;
    @Autowired
    FloydWarshallService serviceFW;

    @Autowired
    ExcelWriterService excelWriterService;



    @GetMapping("/test")
    public ModelAndView inputData(ModelAndView modelAndView) {


        modelAndView.setViewName("/test_program");
        return modelAndView;
    }



    @RequestMapping(value = "/test-program", method = RequestMethod.POST)
    public ModelAndView testProgram(ModelAndView modelAndView, @RequestParam("n") Integer n, @RequestParam("d") Integer d, @RequestParam("k") Integer k){


        if(n<=0||d<0){

            modelAndView.addObject("notAllowedNumberMessage", "Ви ввели невірне число!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }


        if(n>50){

            modelAndView.addObject("maxNumberMessage", "Занадто багато вершин!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }

        if(n<2){

            modelAndView.addObject("minNumberMessage", "Занадто мало вершин!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }

        if(d>10){
            modelAndView.addObject("maxDimensionMessage", "Занадто багато розмірностей!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }
        if(d<1){
            modelAndView.addObject("minDimensionMessage", "Занадто мало розмірностей!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }

        if(k<1){
            modelAndView.addObject("minStepMessage", "Занадто малий крок!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }

        if(k>10){
            modelAndView.addObject("maxStepMessage", "Занадто великий крок!");
            modelAndView.setViewName("/test_program");
            return modelAndView;
        }

      else {
            ArrayList<Long> timesFW = new ArrayList<>();
            ArrayList<Long> timesDFS = new ArrayList<>();
            ArrayList<Long> times = new ArrayList<>();


            int vertexNumber = n;
            for (int z = 0; z < 5; z++) {
                Graph g = new Graph(vertexNumber);
                List<Box> boxes = dataService.generateData(vertexNumber, d);
                Box.sortDimensions(boxes);

                Box.checkNesting(g, boxes);
                graphService.setGraph(g);
                vertexNumber += k;


                long startTimeDFS = System.nanoTime();
                for (int i = 0; i < g.getVertices().length; i++) {
                    g.findLongestPath(i, false);
                }
                long workTimeDFS = System.nanoTime() - startTimeDFS;


                long startTimeFW = System.nanoTime();
                serviceFW.setV(g.getMatrix().length);

                long workTimeFW = System.nanoTime() - startTimeFW;

                List<Integer> listFW = serviceFW.floydWarshall(g.getMatrix(), false);


                times.add(workTimeFW);
                times.add(workTimeDFS);
                System.out.println("workTime DFS = " + workTimeDFS);
                System.out.println("workTime FW = " + workTimeFW);

                ExcelWriterService.fillMap(g.getMatrix().length, times);
                times.clear();
                
            }


            ExcelWriterService excel = new ExcelWriterService();
            try {
                excel.write();
            } catch (IOException e) {
                e.printStackTrace();
            }


            modelAndView.setViewName("/test_info");

            return modelAndView;
        }
    }
}
