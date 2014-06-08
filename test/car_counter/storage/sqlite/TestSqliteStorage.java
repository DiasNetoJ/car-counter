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

package car_counter.storage.sqlite;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import car_counter.counting.DetectedVehicle;
import car_counter.counting.Direction;
import car_counter.storage.DatabaseTestUtils;
import org.apache.commons.io.IOUtils;
import org.ini4j.Wini;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link SqliteStorage}.
 */
public class TestSqliteStorage
{
    private Wini ini;
    private Path dbFile;

    @Before
    public void setUp() throws Exception
    {
        dbFile = Files.createTempFile("test", ".db");

        String iniValue = String.format(
            "[Storage]\n" +
            "implementation = sqlite\n" +
            "database = %s\n",
            dbFile);

        try (InputStream stream = IOUtils.toInputStream(iniValue, StandardCharsets.UTF_8))
        {
            ini = new Wini(stream);
        }
    }

    @Test
    public void testStoreMultipleVehicles()
    {
        // Arrange
        SqliteStorage storage = new SqliteStorage(ini);
        DateTime dateTime = new DateTime(2012, 3, 4, 5, 6, 7, 8);
        Collection<DetectedVehicle> vehicles = Arrays.asList(
            new DetectedVehicle(dateTime, Direction.LEFT, Direction.RIGHT, null, null),
            new DetectedVehicle(dateTime, Direction.RIGHT, Direction.LEFT, null, null)
        );
        Path videoFile = Paths.get("video.avi");

        // Act
        storage.store(videoFile, vehicles);

        // Assert
        String expected =
            "0 id=1 timestamp=1330797967008 initial_location=1 end_location=2 speed=null colour=null video_file=video.avi \n" +
            "1 id=2 timestamp=1330797967008 initial_location=2 end_location=1 speed=null colour=null video_file=video.avi \n";

        DatabaseTestUtils.assertTableContents("detected_vehicles", storage.getConnection(), expected);
    }

    @After
    public void tearDown() throws Exception
    {
        Files.delete(dbFile);
    }
}