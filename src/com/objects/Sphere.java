package com.objects;

import com.helper.*;
import com.materials.*;

public class Sphere implements Hittable {
    private Vector3 center;
    private double radius;
    public Material mat;
    public Sphere(Vector3 center, double radius, Material mat) {
        this.center = center;
        this.radius = radius;
        this.mat = mat;
    }

    @Override
    public boolean hit(Ray r, Interval ray_t, HitRecord rec) {
        // subtract the ray's origin from the center to get the distance between the two
        Vector3 oc = Vector3.subtract(r.getOrigin(), center);
        // vector math to get the a b and c value for quadratic equation
        // https://raytracing.github.io/books/RayTracingInOneWeekend.html#outputanimage
        double a = Vector3.dot(r.getDirection(), r.getDirection());
        double b = 2.0 * Vector3.dot(oc, r.getDirection());
        double c = Vector3.dot(oc, oc) - radius * radius;
        // get the discriminant
        double discrim = b * b - 4 * a * c;
        double root = (-b - Math.sqrt(discrim)) / (2.0 * a);
        if (!ray_t.surrounds(root)) {
            root = (-b + Math.sqrt(discrim)) / (2.0 * a);
            if (!ray_t.surrounds(root)) {
                return false;
            }
        }

        // if less than zero, it doesn't exist - unreal solution
        if (discrim < 0) {
            return false;
        }

        if (rec == null) {
            rec = new HitRecord();
        }

        rec.t = root;
        rec.point = r.at(rec.t);
        rec.normal = Vector3.divide(Vector3.subtract(rec.point, center), radius);
        rec.mat = this.mat;
        Vector3 outwardNormal = Vector3.divide(Vector3.subtract(rec.point, center), radius);
        rec.setFaceNormal(r, outwardNormal);
        // return the solution
        return true;
    }
}
