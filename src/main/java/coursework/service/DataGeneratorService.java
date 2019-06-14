package coursework.service;

import coursework.model.Box;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dataService")
public class DataGeneratorService {
    public List<Box> generateData(int numberOfVertex, int dimensions){
        int a = 1;
        int b = 10;
        List<Box> boxes = new ArrayList<>();
        for (int i = 0; i < numberOfVertex; i++) {
            Box box = new Box();
            box.setNumber(i);
            List<Integer> d = new ArrayList<>();
            for (int j = 0; j <dimensions ; j++) {
                int random_number1 = a + (int) (Math.random() * b);
                d.add(random_number1);
            }
            box.setDimensions(d);
            boxes.add(box);
        }

        return boxes;

    }

}
