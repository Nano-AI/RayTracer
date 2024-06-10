package com.materials;

import com.helper.HitRecord;
import com.helper.Ray;
import com.helper.Utils;
import com.helper.Vector3;

public class Dielectric implements Material {
    private double refraction_index;
    public Dielectric(double refraction_index) {
        this.refraction_index = refraction_index;
    }

    @Override
    public boolean scatter(Ray r_in, HitRecord rec, Vector3 attenuation, Ray scattered) {
        attenuation.set(new Vector3(1.0, 1.0, 1.0));
        double ri = rec.frontFace ? (1.0 / refraction_index) : refraction_index;

        Vector3 unitDir = Vector3.unitVector(r_in.getDirection());
        double cos_theta = Math.min(Vector3.dot(Vector3.multiply(-1, unitDir), rec.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);

        boolean cannot_refract = ri * sin_theta > 1.0;
        Vector3 direction = new Vector3();

        if (cannot_refract || reflectance(cos_theta, ri) > Utils.randomDouble()) {
            direction.set(Vector3.reflect(unitDir, rec.normal));
        } else {
            direction.set(Vector3.refract(unitDir, rec.normal, ri));
        }

        scattered.set(new Ray(rec.point, direction));
        return true;
    }

    private static double reflectance(double cosine, double refraction_index) {
        double r0 = (1 - refraction_index) / (1 + refraction_index);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1 - cosine) , 5);
    }
}
