package car_counter.storage.sqlite;

import java.nio.file.Path;
import java.util.Collection;

import car_counter.counting.DetectedVehicle;
import car_counter.storage.Storage;
import org.ini4j.Wini;

/**
 * Sqlite based storage.
 */
public class SqliteStorage implements Storage
{
    public SqliteStorage(Wini ini)
    {
    }

    @Override
    public void store(Path destinationFile, Collection<DetectedVehicle> detectedVehicles)
    {
    }
}
