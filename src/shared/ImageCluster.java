package shared;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ImageCluster { //Data Type to represent an image cluster
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
