package com.rafaa;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(JobServiceApplication.class)
public class JobServiceApplicationTests {

    @Test
    public void should_a_return_b(){
    }
}
