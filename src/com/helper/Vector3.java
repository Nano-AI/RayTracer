package com.helper;

import java.awt.*;

public class Vector3 {
    Interval colorRange = new Interval(0.000, 0.999);
    private double[] e = new double[3];

    public Vector3() {
        e[0] = 0;
        e[1] = 0;
        e[2] = 0;
    }

    public Vector3(double x, double y, double z) {
        e[0] = x;
        e[1] = y;
        e[2] = z;
    }

    public static Vector3 multiply(Vector3 v, Vector3 u) {
        return new Vector3(v.getX() * u.getX(), v.getY() * u.getY(), v.getZ() * u.getZ());
    }

    public double getX() {
        return e[0];
    }

    public double getY() {
        return e[1];
    }

    public double getZ() {
        return e[2];
    }

    public Vector3 setX(double x) {
        e[0] = x;
        return this;
    }

    public Vector3 setY(double y) {
        e[1] = y;
        return this;
    }

    public Vector3 setZ(double z) {
        e[2] = z;
        return this;
    }

    public double getIndex(int index) {
        return e[index];
    }

    public Vector3 addIndex(int index, double val) {
        e[index] += val;
        return this;
    }

    public Vector3 multiplyIndex(int index, double val) {
        e[index] *= val;
        return this;
    }


    boolean equals(Vector3 v) {
        return getX() == v.getX() && getY() == v.getY() && v.getZ() == getZ();
    }

    public Vector3 add(Vector3 v) {
        addIndex(0, v.getX());
        addIndex(1, v.getY());
        addIndex(2, v.getZ());
        return this;
    }

    public Vector3 multiply(double v) {
        multiplyIndex(0, v);
        multiplyIndex(1, v);
        multiplyIndex(2, v);
        return this;
    }

    public Vector3 subtract(Vector3 v) {
        return this.add(Vector3.multiply(-1, v));
    }

    public Vector3 divide(double v) {
        return multiply(1/v);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return Math.pow(getX(), 2) + Math.pow(getY(), 2) + Math.pow(getZ(), 2);
    }

    @Override
    public String toString() {
        return String.format("(x=%.2f,y=%.2f,z=%.2f)", getX(), getY(), getZ());
    }

    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }

    public static Vector3 add(Vector3 v1, double t) {
        return new Vector3(v1.getX() + t, v1.getY() + t, v1.getZ() + t);
    }

    public static Vector3 subtract(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }

    public static Vector3 multiply(Vector3 v, double t) {
        return new Vector3(v.getX() * t, v.getY() * t, v.getZ() * t);
    }

    public static Vector3 multiply(double t, Vector3 v) {
        return multiply(v, t);
    }

    public static Vector3 divide(Vector3 v, double t) {
        return multiply(v, 1/t);
    }
    public static Vector3 divide(double t, Vector3 v) {
        return divide(v, t);
    }

    public static double dot(Vector3 v1, Vector3 v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    public static Vector3 cross(Vector3 v1, Vector3 v2) {
        return new Vector3(
                v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
                v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
                v1.getX() * v2.getY() - v1.getY() - v2.getX()
        );
    }

    public static Vector3 unitVector(Vector3 v) {
        return divide(v, v.length());
    }

    public static Vector3 randomInUnitSphere() {
        while (true) {
            Vector3 p = Vector3.random(-1, 1);
            if (p.lengthSquared() < 1)
                return p;
        }
    }

    public static Vector3 randomUnitVector() {
        return unitVector(randomInUnitSphere());
    }

    public static Vector3 randomOnHemisphere(Vector3 normal) {
        Vector3 onUnitSphere = randomUnitVector();
        if (dot(onUnitSphere, normal) > 0.0) {
            return onUnitSphere;
        } else {
            return Vector3.multiply(-1, onUnitSphere);
        }
    }

    public Color toColor() {
        return new Color((int) (255.999 * colorRange.clamp(this.getX())), (int) (255.999 * colorRange.clamp(this.getY())), (int) (255.999 * colorRange.clamp(this.getZ())));
    }

    public static Vector3 fromColor(Color c) {
        return new Vector3(c.getRed() / 255.999, c.getGreen() / 255.999, c.getBlue() / 255.999);
    }

    public Vector3 clone() {
        return new Vector3(this.getX(), this.getY(), this.getZ());
    }

    public static Vector3 random() {
        return new Vector3(Utils.randomDouble(), Utils.randomDouble(), Utils.randomDouble());
    }

    public static Vector3 random(double min, double max) {
        return new Vector3(
                Utils.randomDouble(min, max),
                Utils.randomDouble(min, max),
                Utils.randomDouble(min, max)
        );
    }

    public boolean nearZero() {
        double s = 1e-8;
        return Math.abs(e[0]) < s && Math.abs(e[1]) < s && Math.abs(e[2]) < s;
    }

    public static Vector3 reflect(Vector3 v, Vector3 n) {
        return Vector3.subtract(v, Vector3.multiply(n, 2 * Vector3.dot(v, n)));
    }

    public void set(Vector3 v) {
        this.setX(v.getX());
        this.setY(v.getY());
        this.setZ(v.getZ());
    }

    // Snell's law: https://raytracing.github.io/books/RayTracingInOneWeekend.html#dielectrics/snell'slaw
    public static Vector3 refract(Vector3 uv, Vector3 n, double etai_over_etat) {
        double cos_theta = Math.min(Vector3.dot(Vector3.multiply(-1, uv), n), 1.0);
        Vector3 r_out_perp = Vector3.multiply(etai_over_etat, Vector3.add(uv, Vector3.multiply(cos_theta, n)));
        Vector3 r_out_parallel = Vector3.multiply(Math.sqrt(Math.abs(1.0 - r_out_perp.lengthSquared())), Vector3.multiply(-1, n));
        return Vector3.add(r_out_parallel, r_out_perp);
    }

    public static Vector3 randomInUnitDisk() {
        while (true) {
            Vector3 p = new Vector3(Utils.randomDouble(-1, 1), Utils.randomDouble(-1, 1), 0);
            if (p.lengthSquared() < 1) {
                return p;
            }
        }
    }
}
