package com.roche.rapid.test.APIAutomation.CustomTests.cdsoar;

import com.roche.rapid.test.APIAutomation.CustomTests.DBConnector;
import com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.cdsoarHelper.AmazonS3BucketHelper;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class TestOrganizationDBTableOnDestination {
    private DBConnector dbConnector;
    private Logger LOGGER = LoggerFactory.getLogger(TestOrganizationDBTableOnDestination.class);
    private Connection connection;
    private ResultSet resultSet;

    @BeforeClass
    private void setup() {
        dbConnector = new DBConnector();
    }

    @AfterClass
    private void cleanupAfterTest() {
        dbConnector.cleanup();
    }

    @Test
    public void getOrganizationUUIDCountFromOrganizationDB() {
        int count = 0;
        PreparedStatement selectStatement;

        connection = dbConnector.getOrganizationDBConnection();

        String query = "select Count(organization_uuid) from organization;";
        try {
            selectStatement = connection.prepareStatement(query);
            LOGGER.info("Query Statement: "+selectStatement);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next())
                count = resultSet.getInt(1);
            LOGGER.info("Query Result: "+count);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void getOrganizationUUIDsFromOrganizationDB() {
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
    }

    @Test
    private void listDownAllDatabases() throws SQLException {
        ArrayList<String> destinationOrganizationDBs = new ArrayList<>();
        connection = dbConnector.getDestinationOrganizationsConnection();

        PreparedStatement selectStatement;
        String query = "SELECT datname FROM pg_database WHERE datistemplate = false;";

        try {
            selectStatement = connection.prepareStatement(query);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next())
                destinationOrganizationDBs.add(resultSet.getString(1));
            LOGGER.info("Query Result: "+destinationOrganizationDBs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void getPatientUUIDsFromDestinationOrgDB() throws ConfigurationException {
        ArrayList<String> patientUUIDs = new ArrayList<>();
        PreparedStatement selectStatement;

        connection = dbConnector.getDestinationOrganization("03eef72e-543d-475f-89a5-c216f4c3e4ef");
        String query = "select patientid from patient;";

        try {
            selectStatement = connection.prepareStatement(query);
            LOGGER.info("Query Statement: "+selectStatement);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next())
                patientUUIDs.add(resultSet.getString(1));
            LOGGER.info("Query Result: "+patientUUIDs);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void  filesChecking() throws IOException {
        UUID uid = UUID.fromString("02bc2464-0cd4-45f5-87f2-7243614986e1");
        System.out.println("Value"+AmazonS3BucketHelper.getFolderFromS3());
        System.out.println("Message "+folderExistInS3(uid, "cdsoartesting"));
    }

    @Test
    public void filesCheckings() throws IOException {
        System.out.print(AmazonS3BucketHelper.getReportNameCount(""));
    }

    public static boolean folderExistInS3(UUID patientUUID, String bucketName) throws IOException {
        return AmazonS3BucketHelper.getFolderandFileListInS3(patientUUID.toString(), bucketName);
    }
}

