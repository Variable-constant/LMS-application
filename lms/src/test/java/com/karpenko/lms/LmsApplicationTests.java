package com.karpenko.lms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LmsApplicationTests {

    @Test
    public void sum4() {
        assertEquals(4, 2 + 2);
    }

}
