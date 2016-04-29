package support;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import java.util.List;
import model.Point;

/**
 * NavUtils  Class.
 * @author Melanie
 */
public final class NavUtils {
    private static final double EARTH_RADIUS_IN_METERS = 
            DistanceUtils.EARTH_MEAN_RADIUS_KM * 1000;

    /**
     * Private Constructor.
     * Suppress default constructor for non-instantiability.
     */
    private NavUtils() {
        throw new AssertionError();
    }

    /**
     * Returns distance (in meters) between 2 points.
     *
     * @param point1 Must not be null.
     * @param point2 Must not be null.
     * @return distance in meters.
     */
    public static double getDistance(Point point1, Point point2) {
        if (point1 !=  null && point2 != null) {
            final SpatialContext ctx = SpatialContext.GEO;
            com.spatial4j.core.shape.Point p1 = ctx.makePoint(
                    point1.getLongitude(), point1.getLatitude());
            com.spatial4j.core.shape.Point p2 = ctx.makePoint(
                    point2.getLongitude(), point2.getLatitude());

            return DistanceUtils.degrees2Dist(ctx.getDistCalc().distance(
                    p1, p2), DistanceUtils.EARTH_MEAN_RADIUS_KM) * 1000;
        } else  {
            throw new IllegalArgumentException("Invalid parameters");
        }
    }

    /**
     * Get total distance between all points.
     * 
     * @param points.
     * @return total distance in meters.
     */
    public static double getTotalDistance(List<Point> points) {
        double totalDistance = 0;        
       
        int count = 0;
        Point previousCount = null;

        for (Point point : points) {
            count++;

            if (count > 1 && count <= points.size()) {
                totalDistance = totalDistance + getDistance(previousCount, 
                        point);
            }

            previousCount = point;
        }

        return totalDistance;
    }

    /**
     * Returns bearing of position 2 from position 1.
     *
     * @param pt1.
     * @param pt2.
     * @return bearing.
     */
    public static double getBearing(Point pt1, Point pt2) {
        double longitude1 = pt1.getLongitude();
        double longitude2 = pt2.getLongitude();
        double latitude1 = Math.toRadians(pt1.getLatitude());
        double latitude2 = Math.toRadians(pt2.getLatitude());
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - 
                Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    /**
     * Returns coordinates of position which is given distance and bearing from
     * given point.
     *
     * @param pt1.
     * @param d.
     * @param brg.
     * @return Position.
     */
    public static Point getPosition(Point pt1, double d, double brg) {
        if (Double.doubleToRawLongBits(d) == 0) {
            return pt1;
        }

        double lat1 = Math.toRadians(pt1.getLatitude());
        double lon1 = Math.toRadians(pt1.getLongitude());
        double brgAsRadians = Math.toRadians(brg);

        double lat2 = Math.asin(Math.sin(lat1) * 
                Math.cos(d / EARTH_RADIUS_IN_METERS) + 
                Math.cos(lat1) * Math.sin(d / EARTH_RADIUS_IN_METERS) * 
                Math.cos(brgAsRadians));
        double x = Math.sin(brgAsRadians) * 
                Math.sin(d / EARTH_RADIUS_IN_METERS) * 
                Math.cos(lat1);
        double y = Math.cos(d / EARTH_RADIUS_IN_METERS) - 
                Math.sin(lat1) * 
                Math.sin(lat2);
        double lon2 = lon1 + Math.atan2(x, y);

        return new Point(Math.toDegrees(lat2), Math.toDegrees(lon2), null);
    }
}
