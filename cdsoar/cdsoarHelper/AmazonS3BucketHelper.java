package com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.cdsoarHelper;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.roche.rapid.test.APIAutomation.Configurations.CdsoarConfigs;
import com.roche.rapid.test.APIAutomation.CustomTests.IRIS.IRISHelper.AmazonAWSHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

public class AmazonS3BucketHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonAWSHelper.class);
    static String defaultAwsRegion = (System.getProperty("awsregion") == null ? CdsoarConfigs.DEFAULT_AWS_REGION : System.getProperty("awsregion"));
    static Region awsRegion = Region.getRegion(Regions.valueOf(defaultAwsRegion.toUpperCase()));
    static String AWS_ACCESS_KEY = CdsoarConfigs.AWS_ACCESS_KEY;
    static String AWS_SECRET_KEY = CdsoarConfigs.AWS_SECRET_KEY;
    private static AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);;
    private static AmazonS3 s3Client = new AmazonS3Client(awsCredentials);;
    static String BUCKET_NAME = CdsoarConfigs.BUCKET_NAME;

    public static ArrayList<String> getFolderFromS3() {
        ArrayList<String> OrganizationList = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(BUCKET_NAME).withDelimiter("/");
        ListObjectsV2Result listing = s3Client.listObjectsV2(request);
        for (String commonPrefix : listing.getCommonPrefixes()) {
            String OrganizationName = commonPrefix.replace("/", "");
            OrganizationList.add(OrganizationName);
        }
        return OrganizationList;
    }

    public static int getReportNameCount(String reportName) {
        ArrayList<String> ReportNameFolders = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(BUCKET_NAME).withDelimiter(String.format("/%s/", reportName));
        ListObjectsV2Result listing = s3Client.listObjectsV2(request);
        for (String commonPrefix : listing.getCommonPrefixes()) {
            ReportNameFolders.add(commonPrefix);
        }
        return ReportNameFolders.size();
    }

    public static int getDateFolderCount(String reportName, String date) {
        ArrayList<String> dateFolders = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(BUCKET_NAME).withDelimiter(String.format("/%s/%s", reportName, date));
        ListObjectsV2Result listing = s3Client.listObjectsV2(request);
        for (String commonPrefix : listing.getCommonPrefixes()) {
            dateFolders.add(commonPrefix);
        }
        System.out.println("List"+dateFolders);
        return dateFolders.size();
    }

    public static boolean getFolderandFileListInS3(String fileName, String bucketName) throws IOException {
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withDelimiter("/");
        ListObjectsV2Result listing = s3Client.listObjectsV2(req);
        for (String commonPrefix : listing.getCommonPrefixes()) {
            System.out.println("Check"+commonPrefix);
        }
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName).withPrefix("02bc2464-0cd4-45f5-87f2-7243614986e1");
        ListObjectsV2Result listig = s3Client.listObjectsV2(request);

        for (S3ObjectSummary summary: listig.getObjectSummaries()) {
            System.out.println("Folder Name: "+summary.getKey());
        }

        S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, "02bc2464-0cd4-45f5-87f2-7243614986e1/patientReport/03-08-2020/patient.jsonl"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                object.getObjectContent()));
        File file = new File("C:/roche_automation_analytics/testautomation/APIAutomation/src/main/java/com/roche/rapid/test/APIAutomation/CustomTests/cdsoar/cdsoarHelper/localFileName.jsonl");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;

            writer.write(line + "\n");
        }

        writer.close();
        ObjectListing result = s3Client.listObjects(bucketName);
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
            if (objectSummary.getKey().contains(fileName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean downloadFileFromS3() throws IOException {
        S3Object object = s3Client.getObject(new GetObjectRequest(BUCKET_NAME, "02bc2464-0cd4-45f5-87f2-7243614986e1/patientReport/03-08-2020/patient.jsonl"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                object.getObjectContent()));
        File file = new File("C:/roche_automation_analytics/testautomation/APIAutomation/src/main/java/com/roche/rapid/test/APIAutomation/CustomTests/cdsoar/cdsoarHelper/localFileName.jsonl");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            writer.write(line + "\n");
        }

        writer.close();
        return true;
    }
}
