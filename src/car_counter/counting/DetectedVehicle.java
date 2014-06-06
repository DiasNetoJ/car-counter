package car_counter.counting;

import java.awt.Color;

import org.joda.time.DateTime;

/**
 * A vehicle or person detected while processing the video.
 */
public class DetectedVehicle
{
    /**
     * The date / time the vehicle was detected.
     */
    private final DateTime dateTime;

    /**
     * The initial location of the vehicle.
     */
    private final Direction initialLocation;

    /**
     * The end location of the vehicle.
     */
    private final Direction endLocation;

    /**
     * The average speed the vehicle is estimated to be travelling at (in km/h), or {@code null} if no estimate is
     * available.
     */
    private final Float speed;

    /**
     * The detected colour of the vehicle, or {@code null} if no detection was made.
     */
    private final Color colour;

    public DetectedVehicle(DateTime dateTime, Direction initialLocation, Direction endLocation, Float speed,
                           Color colour) {
        this.dateTime = dateTime;
        this.initialLocation = initialLocation;
        this.endLocation = endLocation;
        this.speed = speed;
        this.colour = colour;
    }
}
