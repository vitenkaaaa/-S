package ru.netology.CloudStorage.WebConfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "app.fileStoragePath=/var/data",
        "app.jwtSecret=superSecretKey"
})
public class AppPropertiesIntegrationTest {

    @Autowired
    private AppProperties appProperties;

    @Test
    public void testPropertiesBindingFromConfig() {
        assertThat(appProperties.getFileStoragePath()).isEqualTo("/var/data");
        assertThat(appProperties.getJwtSecret()).isEqualTo("superSecretKey");
    }
}
