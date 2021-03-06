package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import shared.*;

public final class KNearestNeighbour {

    private final int correctSuggestions;
    private final int countOfTests;
    private final int percentageCorrect;

    public KNearestNeighbour(ArrayList<Image> trainingImages, ArrayList<Image> testImages) {
        int numOfCorrect = 0; //used to count the number of correct suggestions

        //run algorithm on each image in the test datasheet
        for(Image selectedImg:testImages) {
            ArrayList<ImageDistances> nearestNeighbours = new ArrayList<>();

            //run each image against all the images in the training datasheet
            for(Image trainingImg: trainingImages) {
                nearestNeighbours.add(new ImageDistances(
                        getDistance(trainingImg, selectedImg),
                        trainingImg,
                        selectedImg
                ));
            }
            Collections.sort(nearestNeighbours); //sort image distances so images least distance move top
            Image suggestedImage = nearestNeighbours.get(0).getPossibleImage(); //get the first image with the least distance; K = 1
            nearestNeighbours.clear(); //clear the list for use in next loop

            if(selectedImg.getImageName() == suggestedImage.getImageName())
                numOfCorrect += 1;
        }
        //set the count of tests, the number of correct guesses and calculate the success rate
        this.countOfTests       = testImages.size();
        this.correctSuggestions = numOfCorrect;
        double percentResult    = ((double)correctSuggestions/(double)countOfTests) * 100;
        this.percentageCorrect  = (int)Math.round(percentResult);
    }

    //Get the distance between two "Images"
    private static double getDistance(Image training, Image test) {
        Pattern pattern       = Pattern.compile(","); //spilt by comma
        double[] firstBitMap  = pattern.splitAsStream(training.getBitMap())
                                         .mapToDouble(Double::parseDouble)
                                         .toArray(); //split bitmap of selected training image into array
        double[] secondBitMap = pattern.splitAsStream(test.getBitMap())
                                         .mapToDouble(Double::parseDouble)
                                         .toArray(); //split bitmap of selected test image into array
        double deltaSqrSum    = 0.0; // used to count the sum of the equation

        //run computation on each bitmap against the other
        for(int i = 0; i < firstBitMap.length; i++) {
            double delta    = firstBitMap[i] - secondBitMap[i]; //x2−x1
            double deltaSqr = delta * delta; //(x2−x1)^2
            deltaSqrSum    += deltaSqr; //add up results
        }

        return Math.sqrt(deltaSqrSum); //finish Euclidean Distance computation and return the distance
    }

    public final void printPerformance() {
        System.out.println("Total Number of Images Tested : " + countOfTests);
        System.out.println("Accurate Suggestions          : " + correctSuggestions);
        System.out.println("Accuracy                      : " + percentageCorrect + "%" );
    }
}
