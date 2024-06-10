package com.materials;

import java.awt.*;

import com.helper.*;

public interface Material {
     boolean scatter(Ray r_in, HitRecord rec, Vector3 attenuation, Ray scattered);
}
