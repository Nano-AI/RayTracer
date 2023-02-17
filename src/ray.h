#ifndef RAY_H
#define RAY_H

#include "vec3.h"

class ray {
    public:
    // Constructors
    ray() {}
    ray(const point3& origin, const vec3& direction) 
        : orig(origin), dir(direction)
    {}

    // Get functions
    point3 origin() const { return orig; }
    vec3 direction() const { return dir; }

    // Get the point output of the position function at value t
    // Basically P(t), where P(t) = origin + time * direction
    point3 at(double t) const {
        return orig + t * dir;
    }

    public:
    point3 orig;
    vec3 dir;
};

#endif