package coursework.service;

import coursework.model.Box;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service("fileReader")
public class FileReaderService {
    private static BufferedReader in = null;
    private static int boxesNumber = 0;
    private static int dimensionsNumber = 0;
    private static int [][] matrix = null;
    public static List<Box> read() throws IOException {
        List<Box> boxes = new ArrayList<>();
        try {
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] numberS = line.split(" ");
            int[] numbers = new int[2];
            int counter = 0;
            for (String number : numberS) {
                numbers[counter++] = Integer.parseInt(number);
            }
            scanner.close();

            int lineNum = 0;
            int row = 0;
            in = new BufferedReader(new FileReader("input.txt"));
            String line2 = null;

            while ((line2 = in.readLine()) != null) {
                lineNum++;
                if (lineNum == 1) {
                    boxesNumber = numbers[0];
                    dimensionsNumber = numbers[1]+1;
                    matrix = new int[boxesNumber][dimensionsNumber];
                } else  {
                    String[] tokens = line2.split(" ");
                    for (int j = 0; j < tokens.length; j++) {
                        matrix[row][j] = Integer.parseInt(tokens[j]);
                    }
                    row++;
                }
            }


            for (int i = 0; i < boxesNumber; i++) {
                System.out.println("");
                Box box = new Box();
                List<Integer> dimensions = new ArrayList<>();
                box.setNumber(matrix[i][0]);
                for (int j = 1; j < dimensionsNumber; j++) {
                    dimensions.add(matrix[i][j]);
                }
                box.setDimensions(dimensions);
                boxes.add(box);
            }


            for (int i = 0; i < boxesNumber; i++) {
                System.out.println("");
                for (int j = 0; j < dimensionsNumber; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
            }


            for (Box box:boxes
            ) {
                System.out.println("Ящик номер "+box.getNumber()+" має розмірності: "+box.getDimensions().toString());
            }


        }catch (Exception ex) {
            System.out.println("The code throws an exception");
            System.out.println(ex.getMessage());
        }finally {
            if (in!=null) in.close();
        }

        return  boxes;
    }


}
