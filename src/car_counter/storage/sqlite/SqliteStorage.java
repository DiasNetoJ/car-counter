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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import car_counter.counting.DetectedVehicle;
import car_counter.storage.Storage;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.ini4j.Wini;

/**
 * Sqlite based storage.
 */
public class SqliteStorage implements Storage
{
    private Connection connection;

    public SqliteStorage(Wini ini)
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Error loading sqlite driver.", e);
        }

        try
        {
            String database = ini.get("Storage", "database", String.class);
            Preconditions.checkState(StringUtils.isNotBlank(database), "A database file is required");
            Path dbFile = Paths.get(database);

            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", dbFile.toAbsolutePath()));

            initialiseTables();
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("Error opening database", e);
        }
    }

    private void initialiseTables() throws SQLException
    {
        Collection<String> tables = new ArrayList<String>();
        ResultSet resultSet = connection.getMetaData().getTables(null, null, "%", null);
        while (resultSet.next())
        {
            tables.add(resultSet.getString(3));
        }

        if (tables.contains("detected_vehicles"))
        {
            return;
        }

        Statement statement = connection.createStatement();

        statement.executeUpdate(
            "create table detected_vehicles (" +
            "id integer primary key," +
            "timestamp integer," +
            "initial_location integer," +
            "end_location integer," +
            "speed float," +
            "colour string," +
            "video_file string)");
    }

    @Override
    public void store(Path destinationFile, Collection<DetectedVehicle> detectedVehicles)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(
                "insert into detected_vehicles " +
                "(timestamp, initial_location, end_location, speed, colour, video_file) values " +
                "(?, ?, ?, ?, ?, ?)");

            for (DetectedVehicle detectedVehicle : detectedVehicles)
            {
                statement.setLong(1, detectedVehicle.getDateTime().getMillis());
                statement.setLong(2, detectedVehicle.getInitialLocation().ordinal());
                statement.setLong(3, detectedVehicle.getEndLocation().ordinal());

                if (detectedVehicle.getSpeed() == null)
                {
                    statement.setNull(4, Types.NULL);
                }
                else
                {
                    statement.setFloat(4, detectedVehicle.getSpeed());
                }

                statement.setString(5, null);
                statement.setString(6, destinationFile.getFileName().toString());

                statement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("Error inserting records", e);
        }
    }

    /**
     * Get the database connection.
     *
     * @return the connection.
     */
    public Connection getConnection()
    {
        return connection;
    }
}
