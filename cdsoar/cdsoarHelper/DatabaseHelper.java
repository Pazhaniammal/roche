package com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.cdsoarHelper;

import com.roche.rapid.test.APIAutomation.CustomTests.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHelper {

    private static DBConnector dbConnector = new DBConnector();;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
    private static Connection connection;
    private static ResultSet resultSet;

    public static ArrayList<String> getOrganizationUUIDsFromOrganizationDB() {
        ArrayList<String> organizationUUIDs = new ArrayList<>();
        PreparedStatement selectStatement;
        connection = dbConnector.getOrganizationDBConnection();
        String query = "select organization_uuid from organization";

        try {
            selectStatement = connection.prepareStatement(query);
            LOGGER.info("Query Statement: "+selectStatement);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next())
                organizationUUIDs.add(resultSet.getString(1));
            LOGGER.info("Query Result: "+organizationUUIDs);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return organizationUUIDs;
    }

    public static int getOrganizationCountFromOrganizationDB() {
        int count = 0;
        PreparedStatement selectStatement;
        connection = dbConnector.getOrganizationDBConnection();
        String query = "select Count(organization_uuid) from organization;";
        try {
            selectStatement = connection.prepareStatement(query);
            LOGGER.info("Query Statement: " + selectStatement);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next())
                count = resultSet.getInt(1);
            LOGGER.info("Query Result: " + count);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

}
