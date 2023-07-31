package com.fitstir.fitstirapp;

import static org.junit.Assert.assertArrayEquals;

import com.fitstir.fitstirapp.ui.utility.enums.HealthOptions;

import junit.framework.TestCase;

import java.util.ArrayList;

public class HealthOptionsTests extends TestCase {

    public void testGetSpinnerTitle() {
        HealthOptions health = HealthOptions.ALCOHOL_FREE;
        String expected = "Alcohol Free";
        String actual = health.getSpinnerTitle();

        assertEquals(expected, actual);
    }

    public void testGetValue() {
        HealthOptions health = HealthOptions.ALCOHOL_FREE;
        int expected = 0;
        int actual = health.getValue();

        assertEquals(expected, actual);
    }

    public void testGetKey() {
        HealthOptions health = HealthOptions.ALCOHOL_FREE;
        String expected = "alcohol-free";
        String actual = health.getKey();

        assertEquals(expected, actual);
    }

    public void testValues() {
        HealthOptions[] array = HealthOptions.values();

        ArrayList<HealthOptions> expectedList = new ArrayList<HealthOptions>() {{
            add(HealthOptions.ALCOHOL_FREE);
            add(HealthOptions.CELERY_FREE);
            add(HealthOptions.CRUSTACEAN_FREE);
        }};
        Object[] expected = expectedList.toArray();

        Object[] actual = new Object[3];
        System.arraycopy(array, 0, actual, 0, 3);

        assertArrayEquals(expected, actual);
    }
}