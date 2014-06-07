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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Database test utils.
 */
public class DatabaseTestUtils
{
    public static void assertTableContents(String table, Connection connection, String expectedContents)
    {
        StringBuilder contents = new StringBuilder();

        try
        {
            PreparedStatement statement = connection.prepareStatement(String.format("select * from %s", table));
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int row = 0;

            while (resultSet.next())
            {
                contents.append(row++);
                contents.append(" ");

                for (int i = 1; i < metaData.getColumnCount(); i++)
                {
                    String column = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    contents.append(String.format("%s=%s ", column, value));
                }

                contents.append("\n");
            }
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("Error querying database", e);
        }

        assertThat(contents.toString(), is(expectedContents));
    }
}
