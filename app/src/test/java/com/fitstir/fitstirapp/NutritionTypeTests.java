package com.fitstir.fitstirapp;

import static org.junit.Assert.assertArrayEquals;

import com.fitstir.fitstirapp.ui.utility.enums.NutritionType;

import junit.framework.TestCase;

public class NutritionTypeTests extends TestCase {

    public void testGetSpinnerTitle() {
        NutritionType type = NutritionType.COOKING;
        String actual = type.getSpinnerTitle();
        String expected = "Cooking";
        assertEquals(expected, actual);
    }

    public void testGetValue() {
        NutritionType type = NutritionType.COOKING;
        int actual = type.getValue();
        int expected = 0;
        assertEquals(expected, actual);
    }

    public void testGetKey() {
        NutritionType type = NutritionType.COOKING;
        String actual = type.getKey();
        String expected = "cooking";
        assertEquals(expected, actual);
    }

    public void testValues() {
        Object[] actual = NutritionType.values();
        Object[] expected = {NutritionType.COOKING, NutritionType.LOGGING};
        assertArrayEquals(expected, actual);
    }
}