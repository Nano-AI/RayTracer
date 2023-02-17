#ifndef SPHERE_H
#define SPHERE_H

#include "hittable.h"
#include "vec3.h"

class sphere : public hittable {
    public:
    sphere() {}
    sphere(point3 cen, double r) : center(cen), radius(r) {};

    virtual bool hit(
        const ray& r, double t_min, double t_max, hit_record& rec
    ) const override;

    public:
    point3 center;
    double radius;
};

bool sphere::hit(const ray& r, double t_min, double t_max, hit_record& rec) const {
    //tex:
    //circle:
    //$$x^2 + y^2 + z^2 = radius^2$$
    //written with displacement:
    //$$(x-C_x)^2+(y-C_y)^2+(z-C_z)^2=r^2$$
    //$$C = (C_x, C_y, C_z),P=(x, y, z)$$
    //rewriting it with substitution in vector form
    //$$(x-C_x)^2+(y-C_y)^2+(z-C_z)^2=(P-C)^2=r^2$$
    //given that P (the vector) can be modeled off of the equation $$P(t)=A+tb$$ where t is the distance,
    //and A is the ray origin and b is the ray's direction, it can be rewritten as
    //$$(P(t)-C)^2=(A+tb-C)^2=r^2$$
    //now to check if the ray every hits the vector, the equation can be expanded and rewritten
    //$$t^2*b^2+2tb(A-C)+(A_C)^2-r^2=0$$
    //all values are known, only unknown is t, so solving for t will allow the program to figure out if the ray hit the circle
    //$$\frac{ -b \pm \sqrt{ b^2 -4ac } }{2a}={\frac{-2h\pm\sqrt{(2h)^2-4ac}}{2a}}=\frac{-h\pm\sqrt{h^2-ac}}{a}$$
    //Get the distance from the ray to the origin of the sphere 

    vec3 oc = r.origin() - center;
    auto a = r.direction().length_squared(); // a^2
    auto b = dot(oc, r.direction());
    auto c = oc.length_squared() - radius * radius;
    // Get the discriminant of the quadratic formula
    auto discriminant = b * b - a * c;
    // If it is above zero, there exists a real solution
    // false since it doesn't it
    if (discriminant < 0) {
        return false;
    }

    auto sqrtd = sqrt(discriminant);

    auto root = (-b - sqrtd) / a;
    if (root < t_min || t_max < root) {
        root = (-b + sqrtd) / a;
        if (root < t_min || t_max < root) {
            return false;
        }
    }

    rec.t = root;
    rec.p = r.at(rec.t);
    rec.normal = (rec.p - center) / radius;

    return true;
}

#endif