package coursework.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Box {
    private int number;
    private List<Integer> dimensions;


    public Box(){

    }
    public Box(int number, List<Integer> dimensions) {
        this.number = number;
        this.dimensions = dimensions;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Integer> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Integer> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Box> createBoxes(int numberOfBoxes, int demensional){
        List<Box> boxes = new ArrayList<>();
        for (int i = 0; i <numberOfBoxes ; i++) {
            Box box = new Box(i,dimensions);

            boxes.add(box);
        }

        return boxes;
    }

    public static void checkNesting(Graph graph, List<Box> boxes){

        for (Box box:boxes
        ) {
            for (int i = 1; i < boxes.size()+1; i++) {
                if (checkDimensions(box, boxes.get(i-1))) {
                    graph.addEdge(box.number, boxes.get(i-1).number, 1);
                }
            }

        }
    }

    public static boolean checkDimensions(Box box1, Box box2){


        boolean isNested = false;
        for (int i = 0; i < box1.getDimensions().size(); i++) {
            if (box1.getDimensions().get(i) < box2.getDimensions().get(i)){
                isNested = true;
            }
            else
                return false;
        }

        return isNested;
    }
    public static void sortDimensions(List<Box> boxes){

        for (Box box:boxes
        ) {
            Collections.sort(box.dimensions);

        }


    }
}
