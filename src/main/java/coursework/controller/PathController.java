package coursework.controller;

import coursework.model.Box;
import coursework.model.Graph;
import coursework.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class PathController {
    @Autowired
    DataGeneratorService dataService;

    @Autowired
    BoxService boxService;

    @Autowired
    GraphService graphService;
    @Autowired
    FloydWarshallService serviceFW;
    @Autowired
    FileReaderService fileReader;

    @RequestMapping(value = "/get-parameters", method = RequestMethod.POST)
    public ModelAndView findLongestPath(ModelAndView modelAndView, @RequestParam("n") Integer n, @RequestParam("d") Integer d){

        if(n<=0||d<0){

            modelAndView.addObject("notAllowedNumberMessage", "Ви ввели невірне число!");
            modelAndView.setViewName("/input_data");
            return modelAndView;
        }


        if(n>50){

            modelAndView.addObject("maxNumberMessage", "Занадто багато вершин!");
            modelAndView.setViewName("/input_data");
            return modelAndView;
        }

        if(n<2){

            modelAndView.addObject("minNumberMessage", "Занадто мало вершин!");
            modelAndView.setViewName("/input_data");
            return modelAndView;
        }

        if(d>10){
            modelAndView.addObject("maxDimensionMessage", "Занадто багато розмірностей!");
            modelAndView.setViewName("/input_data");
            return modelAndView;
        }
        if(d<1){
            modelAndView.addObject("minDimensionMessage", "Занадто мало розмірностей!");
            modelAndView.setViewName("/input_data");
            return modelAndView;
        }

        else {
            System.out.println("n = " + n);
            System.out.println("d = " + d);
            Graph g = new Graph(n);

            List<Box> boxes = dataService.generateData(n, d);
            Box.sortDimensions(boxes);
            //   boxService.sortDimensions(boxes);
            //   boxService.checkNesting(g,boxes);
            Box.checkNesting(g, boxes);
            graphService.setGraph(g);

            //    g.findLongestPath(1);
            for (int i = 0; i < g.getVertices().length; i++) {
                g.findLongestPath(i, false);
            }
            int size1 =  g.getLongestPath().size();
            String path1 = g.getLongestPath().toString();
            System.out.println(path1);
            List<Integer> vertices = graphService.getVertices();
            modelAndView.addObject("vertices", vertices);
            serviceFW.setV(g.getMatrix().length);
            List<Integer> listFW = serviceFW.floydWarshall(g.getMatrix(), false);
            int size2 = listFW.size();

            String path2 = listFW.toString();
            //String path2 = path1;
            modelAndView.addObject("n", n);
            int[][] matrix = g.getMatrix();
            modelAndView.addObject("matrix", matrix);
            modelAndView.addObject("d", d);
            modelAndView.addObject("graph", g);
            modelAndView.addObject("size1", size1-1);
            modelAndView.addObject("size2", size2-1);
            modelAndView.addObject("path1", path1);
            modelAndView.addObject("path2", path2);
            modelAndView.addObject("boxes",boxes);

            modelAndView.setViewName("/find_path");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/find-path-from-i-to-j", method = RequestMethod.POST)
    public ModelAndView findPathFromItoJ(@RequestParam("i") Integer i, @RequestParam("j") Integer j, ModelAndView modelAndView){
        Graph g = graphService.getGraph();
        int size = g.getMatrix().length;

      // graphService.setEndPoint(j);

        serviceFW.setV(g.getMatrix().length);
        serviceFW.setStartPoint(i);
        serviceFW.setEndPoint(j);
        int[][] matrix = g.getMatrix();
//        for (int k = 0; k <matrix.length ; k++) {
//            for (int l = 0; l <matrix.length ; l++) {
//                matrix[i][j]*=-1;
//            }
//        }
        serviceFW.floydWarshall(matrix, true);
        String pathIJ = serviceFW.getLongestPathIJ().toString();
      //  System.out.println("PATH FROM I TO J = "+serviceFW.getLongestPathIJ());
        // g.findLongestPath(i,true);
      //  System.out.println("path from i to j = "+g.getPathFromItoJ());
        modelAndView.addObject("i", i);
        modelAndView.addObject("j",j);
        modelAndView.addObject("pathIJ", pathIJ);
        modelAndView.setViewName("/find_path_i_j");
        return modelAndView;
    }


    @RequestMapping(value = "/read-from-file", method = RequestMethod.POST)
    public ModelAndView readBoxesFromFile(ModelAndView modelAndView){


        List<Box> boxes = null;
        try {
            boxes = FileReaderService.read();
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("/input_data");
            return modelAndView;
        }
        Graph graph = new Graph(boxes.size());
        System.out.println();
        Box.sortDimensions(boxes);

        Box.checkNesting(graph,boxes);
        graphService.setGraph(graph);


        for (int i = 0; i <graph.getVertices().length ; i++) {
            graph.findLongestPath(i,false);
        }
        String path1 = graph.getLongestPath().toString();
        int size1 = graph.getLongestPath().size();
        List<Integer> vertices =graphService.getVertices();
        modelAndView.addObject("vertices", vertices);
        serviceFW.setV(graph.getMatrix().length);
        int[][]matrix = graph.getMatrix();
        List<Integer> listFW = serviceFW.floydWarshall(matrix, false);
        int size2 = listFW.size();

        modelAndView.addObject("matrix",matrix);
        modelAndView.addObject("graph", graph);
        modelAndView.addObject("size1", size1-1);
        modelAndView.addObject("size2", size2-1);
        modelAndView.addObject("path1", path1);
        modelAndView.addObject("path2", listFW.toString());
        modelAndView.addObject("boxes",boxes);
        modelAndView.getModelMap().addAttribute("g",graph);
        modelAndView.setViewName("/find_path");
        return modelAndView;
    }

}
