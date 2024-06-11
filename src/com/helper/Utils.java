package com.helper;

import java.awt.*;

public class Utils {
    public static double degToRad(double deg) {
        return deg * Math.PI / 180.0;
    }

    public static double randomDouble() {
        return Math.random();
    }

    public static double randomDouble(double min, double max) {
        return min + (max - min) * randomDouble();
    }

    public static Vector3 toColorVector(Color color) {
        return new Vector3(
                color.getRed() / 255.999,
                color.getGreen() / 255.999,
                color.getBlue() / 255.999
        );
    }
}
