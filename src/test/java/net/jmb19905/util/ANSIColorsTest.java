package net.jmb19905.util;

import org.junit.jupiter.api.Test;

public class ANSIColorsTest {

    @Test
    void testThings() {
        System.out.println(ANSIColors.getRed() + "This should be red" + ANSIColors.getReset());
        System.out.println(ANSIColors.getGreen() + "This should be green" + ANSIColors.getReset());
        System.out.println(ANSIColors.getWhite() + "This should be white" + ANSIColors.getReset());
        System.out.println(ANSIColors.getBlack() + "This should be black (or white depending on bg)" + ANSIColors.getReset());
        System.out.println(ANSIColors.getYellow() + "This should be yellow" + ANSIColors.getReset());
        System.out.println(ANSIColors.getBlue() + "This should be blue" + ANSIColors.getReset());
        System.out.println(ANSIColors.getCyan() + "This should be cyan" + ANSIColors.getReset());
        System.out.println(ANSIColors.getPurple() + "This should be purple" + ANSIColors.getReset());
        System.out.println(ANSIColors.replaceANSI(ANSIColors.getRed() + "This should be Normal" + ANSIColors.getReset()));
    }

}
