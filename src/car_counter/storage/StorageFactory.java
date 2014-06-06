package car_counter.storage;

import car_counter.storage.sqlite.SqliteStorage;
import org.ini4j.Wini;

/**
 * A factory for {@link Storage} implementations.
 */
public class StorageFactory
{
    public Storage create(Wini ini)
    {
        String implementation = ini.get("Storage", "implementation", String.class);

        if ("sqlite".equals(implementation))
        {
            return new SqliteStorage(ini);
        }
        else
        {
            throw new IllegalStateException("Unknown storage backend: " + implementation);
        }
    }
}
