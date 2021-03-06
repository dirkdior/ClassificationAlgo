package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import shared.*;

public class KMeansAlgorithm {

    private final int correctSuggestions;
    private final int countOfTests;
    private final int percentageCorrect;

    public KMeansAlgorithm(ArrayList<Image> trainingImages, ArrayList<Image> testImages) {

        int numOfCorrect = 0; //used to count the number of correct suggestions
        int constK       = 10; //Set the K value
        ArrayList<ImageCluster> imageClusters = new ArrayList<>(); //contains a list of image clusters

        //Loop through each classification class
        for(int i = 0; i < constK; i++) {
            ArrayList<Image> clusterImages = new ArrayList<>(); //Contains a list of images that will form a cluster
            for(Image selectedTrainingImage:trainingImages) { //loop through the training images list
                if(selectedTrainingImage.getImageName() == i) //if the name of the image selected matches the selected cluster, add the image to the list
                    clusterImages.add(selectedTrainingImage);
            }
            //Add the newly created cluster into the list of image clusters
            imageClusters.add(new ImageCluster(
                    i,
                    clusterImages
            ));
            clusterImages.clear(); //clear the list to be reused afresh
        } //All image clusters have been created by end of loop

        for(Image testImage:testImages) {
            ArrayList<ImageDistances> imageDistances = new ArrayList<>(); //Keep record of distances between each test image and the centroid
            for(ImageCluster imgCluster:imageClusters) {
                imageDistances.add(new ImageDistances(
                        getDistance(imgCluster.getClusterCentroid(), testImage), //calculate the distance between centroid and test image
                        imgCluster.getClusterCentroid().getImageValue(), //get centroid as image
                        testImage //add test image as well
                )); //adds the ImageDistance into the list of image distances
            }
            Collections.sort(imageDistances); //sort image distances so shortest distance between centroid and selected test image moves top
            Image suggestedClusterCentroid = imageDistances.get(0).getPossibleImage(); //return the suggested centroid
            imageDistances.clear(); //clear the list for use in next loop

            //if the name of the suggested image matches the name of the test image, the  count as correct
            if(testImage.getImageName() == suggestedClusterCentroid.getImageName())
                numOfCorrect += 1;
        }

        //set the count of tests, the number of correct guesses and calculate the success rate
        this.countOfTests       = testImages.size();
        this.correctSuggestions = numOfCorrect;
        double percentResult    = ((double)correctSuggestions/(double)countOfTests) * 100;
        this.percentageCorrect  = (int)Math.round(percentResult);
    }

    private static double getDistance(CustomImage centroid, Image test) {
        Pattern pattern       = Pattern.compile(","); //spilt by comma
        double[] testBitMap = pattern.splitAsStream(test.getBitMap())
                .mapToDouble(Double::parseDouble)
                .toArray(); //split bitmap of selected test image into array
        double deltaSqrSum    = 0.0; // used to count the sum of the equation

        //run computation on each bitmap against the other
        for(int i = 0; i < testBitMap.length; i++) {
            double delta    = centroid.getBitMap().get(i) - testBitMap[i]; //x2−x1
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
