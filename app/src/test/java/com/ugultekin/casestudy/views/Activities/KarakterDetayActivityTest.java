package com.ugultekin.casestudy.views.Activities;

import org.junit.Test;

import static org.junit.Assert.*;

public class KarakterDetayActivityTest {

    private KarakterDetayActivity karakterDetayActivity = new KarakterDetayActivity();

    @Test
    public void replaceTextTest() {

        String input = "umut_gültekin";
        String output;
        String expected = "gültekin_gültekin";

        output = karakterDetayActivity.replaceUmutToGultekin(input);

        assertEquals(expected, output);

    }

    @Test
    public void replaceTextEmptyTest() {

        String input = "umut_gültekin";
        String output;
        String expected = "umut gültekin";

        output = karakterDetayActivity.replaceTextBlank(input);

        assertEquals(expected, output);

    }

}