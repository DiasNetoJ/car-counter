/*
 * Copyright 2014 Luke Quinane
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
