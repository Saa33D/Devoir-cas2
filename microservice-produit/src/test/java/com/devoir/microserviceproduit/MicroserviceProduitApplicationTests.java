package com.devoir.microserviceproduit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "eureka.client.enabled=false")
class MicroserviceProduitApplicationTests {

    @Test
    void contextLoads() {
    }

}
