package com.roche.rapid.test.APIAutomation.CustomTests.cdsoar;

import com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.cdsoarHelper.AmazonS3BucketHelper;
import com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.cdsoarHelper.DatabaseHelper;
import com.roche.rapid.test.APIAutomation.CustomTests.cdsoar.util.DateUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class Verify_organization_folder_in_aws_s3_bucket {
    DatabaseHelper databaseHelper = new DatabaseHelper();
    AmazonS3BucketHelper amazonS3BucketHelper = new AmazonS3BucketHelper();
    int dbOrganizationUUIDSCount;
    int s3OrganizationUUIDSCount;
    String reportName = "patientReport";
    String currentDate;
    @Test
    public void verifyOrganizationUUIDAndReportNameDate() {
        ArrayList<String> dbOrganizationUUIDs = databaseHelper.getOrganizationUUIDsFromOrganizationDB();
        ArrayList<String> s3OrganizationUUIDs = amazonS3BucketHelper.getFolderFromS3();
        dbOrganizationUUIDSCount = databaseHelper.getOrganizationCountFromOrganizationDB();
        currentDate = DateUtil.getCurrentDate();
        System.out.println("Current Date"+currentDate);
        s3OrganizationUUIDSCount = s3OrganizationUUIDs.size();
        int reportNameCount = amazonS3BucketHelper.getReportNameCount(reportName);
        int dateFolderCount = amazonS3BucketHelper.getDateFolderCount(reportName, currentDate);
        Assert.assertEquals(s3OrganizationUUIDSCount, dbOrganizationUUIDSCount);
        Assert.assertEquals(s3OrganizationUUIDs, dbOrganizationUUIDs);
        Assert.assertEquals(reportNameCount, dbOrganizationUUIDSCount);
        Assert.assertEquals(dateFolderCount, dbOrganizationUUIDSCount);

    }

}
