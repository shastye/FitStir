package com.fitstir.fitstirapp;

import static org.junit.Assert.assertArrayEquals;

import com.fitstir.fitstirapp.ui.utility.enums.CategoryOptions;

import junit.framework.TestCase;

public class CategoryOptionsTests extends TestCase {

    public void testGetSpinnerTitle() {
        CategoryOptions option = CategoryOptions.GENERIC_FOODS;
        String actual = option.getSpinnerTitle();
        String expected = "Generic Foods";
        assertEquals(expected, actual);
    }

    public void testGetValue() {
        CategoryOptions option = CategoryOptions.GENERIC_FOODS;
        int actual = option.getValue();
        int expected = 0;
        assertEquals(expected, actual);
    }

    public void testGetKey() {
        CategoryOptions option = CategoryOptions.GENERIC_FOODS;
        String actual = option.getKey();
        String expected = "generic-foods";
        assertEquals(expected, actual);
    }

    public void testValues() {
        Object[] actual = CategoryOptions.values();
        Object[] expected = new Object[4];
        expected[0] = CategoryOptions.GENERIC_FOODS;
        expected[1] = CategoryOptions.PACKAGED_FOODS;
        expected[2] = CategoryOptions.GENERIC_MEALS;
        expected[3] = CategoryOptions.FAST_FOODS;
        assertArrayEquals(expected, actual);
    }
}