package com.objects;

import com.helper.*;

public interface Hittable {
    boolean hit(Ray r, Interval ray_t, HitRecord rec);
}
