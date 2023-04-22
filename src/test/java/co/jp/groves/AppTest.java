package co.jp.groves;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    void succeedingTest() {
        assertEquals(2, 1 + 1, "1 + 1 should equal 2");
    }

    @Test
    void failingTest() {
        /*noop*/
    }
}
