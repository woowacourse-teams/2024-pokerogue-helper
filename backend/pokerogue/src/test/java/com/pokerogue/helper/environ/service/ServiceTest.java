package com.pokerogue.helper.environ.service;

import com.pokerogue.helper.environ.client.TestPokeClientConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "local-mysql")
@Import(TestPokeClientConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class ServiceTest {
}
