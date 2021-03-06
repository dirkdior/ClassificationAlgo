import algorithms.*;
import shared.Image;

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
        System.out.println("NUMBER OF TRAINING IMAGES : " + trainingImages.size());

        System.out.println("NUMBER OF TEST IMAGES     : " + testImages.size());

        //Run KMeans Algorithm
        System.out.println("---\nUSING K-MEANS ALGORITHM [training -> test] [cw2DataSet1.csv -> cw2DataSet2.csv]");
        long km1StartTime    = System.nanoTime(); //starting time for k-means algorithm
        KMeansAlgorithm kma1 = new KMeansAlgorithm(trainingImages, testImages);
        long km1EndTime      = System.nanoTime(); //stop time for k-nn algorithm
        kma1.printPerformance();
        System.out.println("DURATION (in Seconds)         : " + (double)(km1EndTime - km1StartTime) / 1_000_000_000.0); //print duration in seconds

        System.out.println("---\nUSING K-MEANS ALGORITHM [training -> test] [cw2DataSet2.csv -> cw2DataSet1.csv]");
        long km2StartTime    = System.nanoTime(); //starting time for k-means algorithm
        KMeansAlgorithm kma2 = new KMeansAlgorithm(testImages, trainingImages); //use test file as training
        long km2EndTime      = System.nanoTime(); //stop time for k-nn algorithm
        kma2.printPerformance();
        System.out.println("DURATION (in Seconds)         : " + (double)(km2EndTime - km2StartTime) / 1_000_000_000.0); //print duration in seconds

        //Run algorithms.KNearestNeighbour algorithm
        System.out.println("---\nUSING K-NEAREST NEIGHBOUR ALGORITHM [training -> test] [cw2DataSet1.csv -> cw2DataSet2.csv]");
        long nnStartTime1      = System.nanoTime(); //start time for k-nn algorithm
        KNearestNeighbour knn1 = new KNearestNeighbour(trainingImages, testImages);
        long nnEndTime1        = System.nanoTime(); //stop time for k-nn algorithm
        knn1.printPerformance();
        System.out.println("DURATION (in Seconds)         : " + (double)(nnEndTime1 - nnStartTime1) / 1_000_000_000.0); //print duration in seconds

        System.out.println("---\nUSING K-NEAREST NEIGHBOUR ALGORITHM [training -> test] [cw2DataSet2.csv -> cw2DataSet1.csv]");
        long nnStartTime2      = System.nanoTime(); //start time for k-nn algorithm
        KNearestNeighbour knn2 = new KNearestNeighbour(testImages, trainingImages); //use test file as training
        long nnEndTime2        = System.nanoTime(); //stop time for k-nn algorithm
        knn2.printPerformance();
        System.out.println("DURATION (in Seconds)         : " + (double)(nnEndTime2 - nnStartTime2) / 1_000_000_000.0); //print duration in seconds
    }
}
