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

package car_counter.processing;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import car_counter.storage.DatabaseTestUtils;
import car_counter.storage.sqlite.SqliteStorage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.ini4j.Wini;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Tests for {@link DefaultProcessor}.
 */
public class TestDefaultProcessor
{
    private Wini ini;
    private Path dbFile;
    private Path tempDirectory;
    private Path incoming;
    private Path data;

    @Before
    public void setUp() throws Exception
    {
        BasicConfigurator.configure();

        dbFile = Files.createTempFile("test", ".db");
        tempDirectory = Files.createTempDirectory("test");

        incoming = tempDirectory.resolve("incoming");
        data = tempDirectory.resolve("data");

        Files.createDirectories(incoming);
        Files.createDirectories(data);

        String iniValue = String.format(
            "[Counting]\n" +
            "implementation = noop\n" +
            "[Storage]\n" +
            "implementation = sqlite\n" +
            "database = %s\n" +
            "[Processing]\n" +
            "incoming = %s\n" +
            "data = %s\n",
            dbFile, incoming, data);

        ini = new Wini();
        ini.getConfig().setMultiOption(true);

        try (InputStream stream = IOUtils.toInputStream(iniValue, StandardCharsets.UTF_8))
        {
            ini.load(stream);
        }
    }

    @Test
    public void testStoreMultipleVehicles() throws Exception
    {
        // Arrange
        DefaultProcessor processor = new DefaultProcessor(ini);
        Files.copy(Paths.get("test/data/21-20140504155125.avi"), incoming.resolve("21-20140504155125.avi"));

        // Act
        processor.processIncomingContents();

        // Assert
        String expected =
            "0 id=1 timestamp=1399182685000 initial_location=0 end_location=0 speed=null colour=null video_file=### \n";

        String contents = DatabaseTestUtils.getTableContents("detected_vehicles", ((SqliteStorage) processor.getStorage()).getConnection());
        contents = contents.replaceAll("video_file=[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", "video_file=###");

        assertThat(contents, is(expected));
    }

    @After
    public void tearDown() throws Exception
    {
        Files.delete(dbFile);
        FileUtils.deleteDirectory(tempDirectory.toFile());
    }
}
