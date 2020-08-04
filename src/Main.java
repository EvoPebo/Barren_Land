import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {

        int height = 4;//600
        int width =  4;//400;
        DisjointSet disjointSet = new DisjointSet(width * height);

        ArrayList<Integer> rectangles = new ArrayList<Integer>();
        String filename = "C:\\Users\\Owner\\IdeaProjects\\Barren_Land\\land1";

        // below works with files with only numbers and spaces
        //parseFile(filename,rectangles);

        // below works with inputs in the command line
        stdinReader(rectangles);

        if(rectangles.size() % 4 == 0) {
            int rectanglesNum = rectangles.size()/4;
            System.out.println("Number of rectangles: " + rectanglesNum);

            boolean[] isFertileArray = new boolean[width * height];
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    isFertileArray[width * y + x] = isFertile(x, y, rectangles);
                }
            }
            populateDJS(disjointSet,width,height,isFertileArray);
            disjointSet.printFertile(isFertileArray);

            //disjointSet.printSets();
        } else
            System.out.println("Invalid rectangles");

    }
    
    public static void populateDJS(DisjointSet disjointSet, int width, int height, boolean[] isFertileArray){
        for(int i = 0; i < isFertileArray.length; i++){
            if(!isFertileArray[i]){
                continue;
            }
            int above = i + width;
            int right = i + 1;
            // checks the one above to see if fertile and adds to disjoint set
            if(i / width != height - 1 && isFertileArray[above]){
                disjointSet.join(i, above);
            }
            // checks the one to the right to see if fertile and adds to disjoint set
            if(i % width != width - 1 && isFertileArray[right]){
                disjointSet.join(i, right);
            }
        }
    }

    public static void stdinReader(ArrayList<Integer> rectangles){
        System.out.println("Please, enter your rectangles: ");
        //String rectangleFormat = "{\"0 292 399 307\"}";
        Scanner in = new Scanner(System. in);
        String rectangleFormat = in. nextLine();
        System.out.println("String read from console is : \n" + rectangleFormat);

        regexParse(rectangleFormat, rectangles);
    }

    public static void regexParse(String rectangleFormat, ArrayList<Integer> rectangles){
        // regex is: zero or one dashes, one or many digits
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(rectangleFormat);
        while(matcher.find()){
            rectangles.add(Integer.parseInt(matcher.group()));
        }
    }

    public static void parseFile(String filename, ArrayList<Integer> rectangles){

        try {
            File file = new File(filename);
            Scanner in = new Scanner(file);

            while (in.hasNext()) {
                int data = in.nextInt();
                rectangles.add(data);
                System.out.println(data);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static boolean isFertile(int x, int y, ArrayList<Integer> rectangles){
        //checks if point is in range of of barren lands
        for(int i = 0; i <= rectangles.size() - 4;){
            int x1 = rectangles.get(i++);
            int y1 = rectangles.get(i++);
            int x2 = rectangles.get(i++);
            int y2 = rectangles.get(i++);
            if(x >= x1 && x <= x2 && y >= y1 && y <= y2){
                return false;
            }
        }
        return true;
    }
}
