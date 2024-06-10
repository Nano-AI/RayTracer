package com.materials;

import com.helper.HitRecord;
import com.helper.Ray;
import com.helper.Vector3;

public class Metal implements Material {
    private Vector3 albedo;
    private double fuzz;
    public Metal(Vector3 albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    @Override
    public boolean scatter(Ray r_in, HitRecord rec, Vector3 attenuation, Ray scattered) {
        Vector3 reflected = Vector3.reflect(r_in.getDirection(), rec.normal);
        reflected = Vector3.add(Vector3.unitVector(reflected), Vector3.multiply(fuzz, Vector3.randomUnitVector()));
        scattered.set(new Ray(rec.point, reflected));
        attenuation.set(albedo);
        return (Vector3.dot(scattered.getDirection(), rec.normal) > 0);
    }
}
