package ua.epam.payments.payments.model.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CardGenerationTest {

    List<String> list = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 1000; i++) {
            list.add(CardGeneration.generateCardNumber());
        }
    }

    @Test
    public void generateCardNumber() {
        for (int i = 0; i < list.size(); i++) {

            assertTrue(list.get(i).matches("^\\d{16}$"));
        }

    }
}