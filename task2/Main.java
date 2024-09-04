import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java PointCircleRelation <circle_file> <points_file>");
            return;
        }

        String circleFile = args[0];
        String pointsFile = args[1];

        try {
            BufferedReader circleReader = new BufferedReader(new FileReader(circleFile));
            String[] circleCenter = circleReader.readLine().split(" ");
            BigDecimal centerX = new BigDecimal(circleCenter[0]);
            BigDecimal centerY = new BigDecimal(circleCenter[1]);

            if (!isInRange(centerX) || !isInRange(centerY)) {
                System.out.println("Error: Point coordinates are out of the valid range circle.");
                circleReader.close();
                return;
            }

            BigDecimal radius = new BigDecimal(circleReader.readLine());
            circleReader.close();

            BufferedReader pointsReader = new BufferedReader(new FileReader(pointsFile));
            String point;
            int pointCount = 0;
            while ((point = pointsReader.readLine()) != null) {
                pointCount++;
                if (pointCount > 100) {
                    System.out.println("Error: Number of points exceeds the limit of 100.");
                    pointsReader.close();
                    return;
                }

                String[] pointCoords = point.split(" ");
                BigDecimal pointX = new BigDecimal(pointCoords[0]);
                BigDecimal pointY = new BigDecimal(pointCoords[1]);

                if (!isInRange(pointX) || !isInRange(pointY)) {
                    System.out.println("Error: Point coordinates are out of the valid range point.");
                    pointsReader.close();
                    return;
                }

                BigDecimal dx = pointX.subtract(centerX);
                BigDecimal dy = pointY.subtract(centerY);
                BigDecimal distanceSquared = dx.multiply(dx).add(dy.multiply(dy));
                BigDecimal radiusSquared = radius.multiply(radius);

                if (distanceSquared.compareTo(radiusSquared) == 0) {
                    System.out.println(0);
                } else if (distanceSquared.compareTo(radiusSquared) < 0) {
                    System.out.println(1);
                } else {
                    System.out.println(2);
                }
            }
            pointsReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isInRange(BigDecimal value) {
        BigDecimal min = new BigDecimal("1e-38");
        BigDecimal max = new BigDecimal("1e38");
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
}