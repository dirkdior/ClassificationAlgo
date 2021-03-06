package shared;

//This data type represents the euclidean distance between two images
public class ImageDistances implements Comparable<ImageDistances> {
    private final double distance;
    private final Image possibleImage;
    private final Image selectedImage;

    public ImageDistances(double distance, Image possibleImage, Image selectedImage) {
        this.distance      = distance;
        this.possibleImage = possibleImage;
        this.selectedImage = selectedImage;
    }

    public double getDistance() { return distance; }
    public Image getPossibleImage() { return possibleImage; }
    public Image getSelectedImage() { return selectedImage; }

    //Allows distances to be compared to one another, makes sorting possible
    @Override
    public int compareTo(ImageDistances d) {
        return Double.compare(distance, d.getDistance());
    }
}
