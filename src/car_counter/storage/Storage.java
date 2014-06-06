package car_counter.storage;

import java.nio.file.Path;
import java.util.Collection;

import car_counter.counting.DetectedVehicle;

/**
 * The interface for a storage backend.
 */
public interface Storage
{
    /**
     * Stores the detected vehicles to the storage backend.
     *
     * @param destinationFile the video file.
     * @param detectedVehicles the detected vehicles.
     */
    void store(Path destinationFile, Collection<DetectedVehicle> detectedVehicles);
}
