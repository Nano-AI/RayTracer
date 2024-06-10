package com.materials;

import com.helper.*;

public class Lambertian implements Material {
    public Vector3 albedo;
    public Lambertian(Vector3 albedo) {
        this.albedo = albedo;
    }

    public boolean scatter(Ray r_in, HitRecord rec, Vector3 attenuation, Ray scattered) {
        Vector3 scatter_dir = Vector3.add(rec.normal, Vector3.randomUnitVector());

        if (scatter_dir.nearZero()) {
            scatter_dir = rec.normal;
        }
        scattered.set(new Ray(rec.point, scatter_dir));
        attenuation.set(albedo);
        return true;
    }
}
