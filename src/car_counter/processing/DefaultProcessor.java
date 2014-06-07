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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import car_counter.counting.CarCounter;
import car_counter.counting.CarCounterFactory;
import car_counter.counting.DetectedVehicle;
import car_counter.storage.Storage;
import car_counter.storage.StorageFactory;
import car_counter.util.ThreadUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.ini4j.Wini;
import org.joda.time.DateTime;

/**
 * A default processor for car counting.
 */
public class DefaultProcessor
{
    private static final Logger logger = Logger.getLogger(DefaultProcessor.class);

    private final DateTimeExtractor dateTimeExtractor;
    private final CarCounter carCounter;
    private final Storage storage;

    /**
     * The incoming video directory.
     */
    private final Path incomingDirectory;

    /**
     * The data directory.
     */
    private final Path dataDirectory;


    public DefaultProcessor(Wini ini)
    {
        dateTimeExtractor = new MotionDateTimeExtractor();// TODO: via config
        carCounter = new CarCounterFactory().create(ini);
        storage = new StorageFactory().create(ini);

        String incoming = ini.get("Processing", "incoming", String.class);
        Preconditions.checkState(StringUtils.isNotBlank(incoming), "An incoming directory is required");
        incomingDirectory = Paths.get(incoming);

        if (!Files.isDirectory(incomingDirectory))
        {
            throw new IllegalStateException("Incoming path is not an existing directory: " + incoming);
        }

        String data = ini.get("Processing", "data", String.class);
        Preconditions.checkState(StringUtils.isNotBlank(data), "An data directory is required");
        dataDirectory = Paths.get(data);

        if (!Files.isDirectory(dataDirectory))
        {
            throw new IllegalStateException("Data path is not an existing directory: " + data);
        }
    }

    public void process()
    {
        while (true)
        {
            for (Path sourceFile : incomingDirectory)
            {
                try
                {

                    if (!Files.isRegularFile(sourceFile))
                    {
                        continue;
                    }

                    DateTime dateTime = dateTimeExtractor.getDateTime(sourceFile);

                    Path destinationFile = dataDirectory.resolve(UUID.randomUUID().toString());
                    Files.move(sourceFile, destinationFile);

                    Collection<DetectedVehicle> detectedVehicles = carCounter.processVideo(destinationFile, dateTime);
                    storage.store(destinationFile, detectedVehicles);
                }
                catch (Exception e)
                {
                    logger.error("Error processing file: " + sourceFile, e);
                }
            }

            ThreadUtils.sleepMillis(100);
        }
    }
}
