import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String trainingFilePath         = "datasets/cw2DataSet1.csv"; //set training file path
        String testFilePath             = "datasets/cw2DataSet2.csv"; //set test file path
        BufferedReader bufferedReader   = null;
        String line                     = "";

        ArrayList<Image> trainingImages = new ArrayList<>();
        ArrayList<Image> testImages     = new ArrayList<>();

        try {

            //Read training csv file
            bufferedReader = new BufferedReader(new FileReader(trainingFilePath));
            while ((line = bufferedReader.readLine()) != null) { //loop through each line in the training file
                String trimmedLine = line.trim();
                //extract bitmap from line by deleting last two characters which would be the image name and the comma before it
                String bitMap      = trimmedLine.substring(0, trimmedLine.length() - 2);
                int imageName      = Integer.parseInt(trimmedLine.substring(trimmedLine.length() - 1));
                trainingImages.add(new Image(
                        imageName,
                        bitMap
                ));
            }
            //Read test csv file
            bufferedReader = new BufferedReader(new FileReader(testFilePath));
            while ((line = bufferedReader.readLine()) != null) { //loop through each line in the test file
                String trimmedLine = line.trim();
                //extract bitmap from line by deleting last two characters which would be the image name and the comma before it
                String bitMap      = trimmedLine.substring(0, trimmedLine.length() - 2);
                int imageName      = Integer.parseInt(trimmedLine.substring(trimmedLine.length() - 1));
                testImages.add(new Image(
                        imageName,
                        bitMap
                ));
            }
        } catch (IOException e) { //handle any exception that could occur
            e.printStackTrace(); //print out exception
        } finally {
            //attempt to close the file reader and print any exception
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("NUMBER OF TRAINING IMAGES: " + trainingImages.size());
        System.out.println(trainingImages);
        System.out.println(trainingImages.get(trainingImages.size() - 1).getBitMap() + "--->" + trainingImages.get(trainingImages.size() - 1).getImageName());

        System.out.println("NUMBER OF TEST IMAGES: " + testImages.size());
        System.out.println(testImages);
        System.out.println(testImages.get(testImages.size() - 1).getBitMap() + "--->" + testImages.get(testImages.size() - 1).getImageName());

        KNearestNeighbour knn = new KNearestNeighbour();
        knn.getDistance(trainingImages.get(trainingImages.size() - 1), testImages.get(testImages.size() - 1));

    }
}

class Image {
    private final int imageName; //The name of the digit
    private final String bitMap;

    public Image(int imageName, String bitMap) {
        this.imageName = imageName;
        this.bitMap    = bitMap;
    }

    public int getImageName() {
        return this.imageName;
    }

    public String getBitMap() {
        return this.bitMap;
    }
}
