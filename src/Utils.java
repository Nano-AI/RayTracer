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
}
