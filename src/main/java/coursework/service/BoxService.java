package coursework.service;

import coursework.model.Box;
import coursework.model.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("boxService")
public class BoxService {


    public void sortDimensions(List<Box> boxes){

        for (Box box:boxes) {
            Collections.sort(box.getDimensions());
        }
    }

    public  void checkNesting(Graph graph, List<Box> boxes){

        for (Box box:boxes
        ) {
            for (int i = 1; i < boxes.size()+1; i++) {
                if (checkDimensions(box, boxes.get(i-1))) {
                    graph.addEdge(box.getNumber(), boxes.get(i-1).getNumber(), 1);
                }
            }

        }
    }

    public boolean checkDimensions(Box box1, Box box2){

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
}
