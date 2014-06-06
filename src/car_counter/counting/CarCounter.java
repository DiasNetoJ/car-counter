package car_counter.counting;

import java.nio.file.Path;
import java.util.Collection;

import org.joda.time.DateTime;

/**
 * The interface for the car counter.
 */
public interface CarCounter
{
    /**
     * Processes a video file looking for cars.
     *
     * @param video the path to the video to process.
     * @param startDateTime the video start date time.
     * @return a list of detected cars.
     */
    Collection<DetectedVehicle> processVideo(Path video, DateTime startDateTime);
}
