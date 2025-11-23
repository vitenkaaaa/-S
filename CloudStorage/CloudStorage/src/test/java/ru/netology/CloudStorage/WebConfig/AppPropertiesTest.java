package ru.netology.CloudStorage.WebConfig;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppPropertiesTest {

    @Test
    public void testSettersAndGetters() {
        AppProperties appProperties = new AppProperties();
        String storagePath = "/tmp/files";
        String jwtSecret = "mysecret";
        appProperties.setFileStoragePath(storagePath);
        appProperties.setJwtSecret(jwtSecret);
        assertEquals(storagePath, appProperties.getFileStoragePath());
        assertEquals(jwtSecret, appProperties.getJwtSecret());
    }
}