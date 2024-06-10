import com.sun.jdi.IntegerValue;

public class Interval {
    public double min, max;
    public static final double n_infinity = Double.NEGATIVE_INFINITY;
    public static final double p_infinity = Double.POSITIVE_INFINITY;

    public static final Interval empty = new Interval(p_infinity, n_infinity);
    public static final Interval universe = new Interval(n_infinity, p_infinity);
    public Interval() {
        min = n_infinity;
        max = p_infinity;
    }

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double size() {
        return max - min;
    }

    public boolean contians(double x) {
        return min <= x && x <= max;
    }

    public boolean surrounds(double x) {
        return min < x && x < max;
    }

    double clamp(double x) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}
