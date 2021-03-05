import java.util.ArrayList;
import java.util.regex.Pattern;

public class KMeansAlgorithm {

    public KMeansAlgorithm(ArrayList<Image> trainingImages, ArrayList<Image> testImages) {

        int constK = 9;
        ArrayList<ImageCluster> imageClusters = new ArrayList<>();

        for(int i = 1; i <= constK; i++) {
            ArrayList<Image> clusterImages = new ArrayList<>();
            for(Image selectedTrainingImage:trainingImages) {
                if(selectedTrainingImage.getImageName() == i)
                    clusterImages.add(selectedTrainingImage);
            }
            imageClusters.add(new ImageCluster(
                    i,
                    clusterImages
            ));
        }

    }

    public static class ImageCluster {
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

        public final CustomImage setClusterCentroid() {
            int clusterImagesCount  = clusterImages.size(); //set the number of images in the cluster
            ArrayList<Double> centroidBitMap = new ArrayList<>();

            for(int y = 0; y < clusterImagesCount; y++) {
                Pattern pattern       = Pattern.compile(","); //spilt by comma
                double[] imageBitMap  = pattern.splitAsStream(clusterImages.get(y).getBitMap())
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                for(int i = 0; i < imageBitMap.length; i++) {
                    if(y == 0)
                        centroidBitMap.add(imageBitMap[i]);
                    else {
                        centroidBitMap.set(i, centroidBitMap.get(i) + imageBitMap[i]);
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
        ArrayList<Double> bitMap;

        public CustomImage(int imageName, ArrayList<Double> bitMap) {
            this.imageName = imageName;
            this.bitMap    = bitMap;
        }

        public int getImageName() {
            return this.imageName;
        }

        public ArrayList<Double> getBitMap() {
            return this.bitMap;
        }

        public void printImage() {
            System.out.println(bitMap + "--->" + imageName);
        }
    }
}
