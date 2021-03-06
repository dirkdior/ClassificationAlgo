package shared;

public class Image {
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

    public void printImage() {
        System.out.println(bitMap + "--->" + imageName);
    }
}
