import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

public class KMeansAlgorithm {

    private final int correctSuggestions;
    private final int countOfTests;
    private final int percentageCorrect;

    public KMeansAlgorithm(ArrayList<Image> trainingImages, ArrayList<Image> testImages) {

        int numOfCorrect = 0; //used to count the number of correct suggestions
        int constK       = 9; //Set the K value
        ArrayList<ImageCluster> imageClusters = new ArrayList<>(); //contains a list of image clusters

        //Loop through each classification class
        for(int i = 1; i <= constK; i++) {
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
            System.out.print("Selected Image: ");
            testImage.printImage();

            ArrayList<KNearestNeighbour.ImageDistances> imageDistances = new ArrayList<>(); //Keep record of distances between each test image and the centroid
            for(ImageCluster imgCluster:imageClusters) {
                imageDistances.add(new KNearestNeighbour.ImageDistances(
                        getDistance(imgCluster.clusterCentroid, testImage),
                        imgCluster.clusterCentroid.imageValue,
                        testImage
                ));
            }
            Collections.sort(imageDistances);
            Image suggestedClusterCentroid = imageDistances.get(0).getPossibleImage();
            System.out.print("Suggested Cluster Centroid: ");
            suggestedClusterCentroid.printImage();
            imageDistances.clear();
            System.out.println("------------------------------------------");

            if(testImage.getImageName() == suggestedClusterCentroid.getImageName())
                numOfCorrect += 1;
        }
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
        System.out.println("Total Count Of Tests: " + countOfTests);
        System.out.println("Correct Suggestions:  " + correctSuggestions);
        System.out.println("% Correct: " + percentageCorrect + "%" );
    }

    public static class ImageCluster { //Data Type to represent an image cluster
        private final int imageLabel;
        private final ArrayList<Image> clusterImages;
        private final CustomImage clusterCentroid;

        public ImageCluster(int imageLabel, ArrayList<Image> clusterImages) {
            this.imageLabel      = imageLabel;
            this.clusterImages   = clusterImages;
            this.clusterCentroid = setClusterCentroid();
        }

        public int getImageLabel() { return imageLabel; }

        public CustomImage getClusterCentroid() { return clusterCentroid; }

        //Sets the Cluster Centroid
        public final CustomImage setClusterCentroid() {
            int clusterImagesCount  = clusterImages.size(); //set the number of images in the cluster
            ArrayList<Double> centroidBitMap = new ArrayList<>(); //Hold bitmap as a list

            for(int y = 0; y < clusterImagesCount; y++) {
                Pattern pattern       = Pattern.compile(","); //spilt by comma
                double[] imageBitMap  = pattern.splitAsStream(clusterImages.get(y).getBitMap())
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                //loop through each entry in the bitmap
                for(int i = 0; i < imageBitMap.length; i++) {
                    if(y == 0)
                        centroidBitMap.add(imageBitMap[i]); //on the first image, add all the bitmaps as centroid bitmap
                    else {
                        centroidBitMap.set(i, centroidBitMap.get(i) + imageBitMap[i]); //on consecutive images, add to bitmaps
                    }
                }
            }

            //Get the mean value for each value in the bitmap to build the Centroid
            for(int i = 0; i < centroidBitMap.size(); i++) {
                centroidBitMap.set(i, centroidBitMap.get(i) / clusterImagesCount);
            }

            //return newly built image as cluster centroid
            return new CustomImage(imageLabel, centroidBitMap);
        }
    }

    //This data type is similar to the Image datatype but it allows the bitmap to be set manually
    public static class CustomImage {
        private final int imageName; //The name of the digit
        private final ArrayList<Double> bitMap;
        private final Image imageValue;

        public CustomImage(int imageName, ArrayList<Double> bitMap) {
            this.imageName  = imageName;
            this.bitMap     = bitMap;
            this.imageValue = toImage();
        }

        public int getImageName() {
            return this.imageName;
        }

        public ArrayList<Double> getBitMap() {
            return this.bitMap;
        }

        public Image getImageValue() { return imageValue; }

        private Image toImage() {
            String bitMapStr = String.join(",", (CharSequence) bitMap);
            return new Image(imageName, bitMapStr);
        }
    }
}
