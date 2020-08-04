package com.roche.rapid.test.APIAutomation.Configurations;

import java.util.Properties;

public class CdsoarConfigs  extends Configs {
    static Properties appProperties = getProperties(APP_SETTINGS_FILE);

    public static String DEFAULT_AWS_REGION = appProperties.getProperty("CDSOAR_AWS_REGION");
    public static String AWS_ACCESS_KEY = appProperties.getProperty("CDSOARAWSKEY");
    public static String AWS_SECRET_KEY = appProperties.getProperty("CDSOARSECRETKEY");
    public static String BUCKET_NAME = "cdsoartesting";
}
