package shared;

import java.util.ArrayList;

//This data type is similar to the Image datatype but it allows the bitmap to be set manually
public class CustomImage {
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
        //replace all empty spaces and square brackets in the string converted from the array
        String bitMapStr = bitMap.toString().replaceAll("\\[+|]+|\\s+", "");
        return new Image(imageName, bitMapStr);
    }
}
