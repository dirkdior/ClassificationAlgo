import java.util.regex.Pattern;

public final class KNearestNeighbour {
    public KNearestNeighbour() {

    }

    public double getDistance(Image training, Image test) {
        Pattern pattern       = Pattern.compile(",");
        double[] firstBitMap  = pattern.splitAsStream(training.getBitMap())
                                         .mapToDouble(Double::parseDouble)
                                         .toArray();
        double[] secondBitMap = pattern.splitAsStream(test.getBitMap())
                                         .mapToDouble(Double::parseDouble)
                                         .toArray();
        double deltaSqrSum    = 0.0;

        for(int i = 0; i < firstBitMap.length; i++ ) {
            double delta    = firstBitMap[i] - secondBitMap[i];
            double deltaSqr = delta * delta;
            deltaSqrSum    += deltaSqr;
        }

        return Math.sqrt(deltaSqrSum);
    }
}
