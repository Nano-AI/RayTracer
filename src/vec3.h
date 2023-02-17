#ifndef VEC3_H
#define VEC3_H

#include <cmath>
#include <iostream>

using std::sqrt;

class vec3 {
    public:
    // Constructors
    vec3() : e{0, 0, 0} {}
    vec3(double e0, double e1, double e2) : e{e0, e1, e2} {}

    // Get methods
    double x() const { return e[0]; };
    double y() const { return e[1]; };
    double z() const { return e[2]; };

    // Negative operator
    vec3 operator-() const { return vec3(-e[0], -e[1], -e[2]); }
    // Memory operators
    double operator[](int i) const { return e[i]; };
    double &operator[](int i) { return e[i]; };
    
    // Adding two vectors
    vec3& operator+=(const vec3 &v) {
        e[0] += v.e[0];
        e[1] += v.e[1];
        e[2] += v.e[2];
        return *this;
    }

    // Multiplying vector by factor t
    vec3& operator*=(const double t) {
        e[0] *= t;
        e[1] *= t;
        e[2] *= t;
        return *this;
    }

    // Dividing vector by factor t
    vec3& operator/=(const double t) {
        return *this *= 1/t;
    }

    // Getting length; pythag thorem but 3d
    double length() const {
        return sqrt(length_squared());
    }
    
    // Getting sum of all sides squared
    double length_squared() const {
        return e[0] * e[0] + e[1] * e[1] + e[2] * e[2];
    }

    public:
    // Coordinates
    double e[3];
};

// More vec3 util functions
// Printing a vector
inline std::ostream& operator<<(std::ostream &out, const vec3 &v) {
    return out << v.e[0] << ' ' << v.e[1] << ' ' << v.e[2];
}

// Summing two vectors
inline vec3 operator+(const vec3 &u, const vec3 &v) {
    return vec3(u.e[0] + v.e[0], u.e[1] + v.e[1], u.e[2] + v.e[2]);
}

// Subtracting two vectors
inline vec3 operator-(const vec3 &u, const vec3 &v) {
    return vec3(u.e[0] - v.e[0], u.e[1] - v.e[1], u.e[2] - v.e[2]);
}

// Multiplying two vectors
inline vec3 operator*(const vec3 &u, const vec3 &v) {
    return vec3(u.e[0] * v.e[0], u.e[1] * v.e[1], u.e[2] * v.e[2]);
}

// Multiplying vector by constant t 
inline vec3 operator*(double t, const vec3 &v) {
    return vec3(t*v.e[0], t*v.e[1], t*v.e[2]);
}
inline vec3 operator*(const vec3 &v, double t) {
    return t * v;
}

// Dividing vector by constant t 
inline vec3 operator/(vec3 &v, double t) {
    return (1/t) * v;
}

inline vec3 operator/(double t, vec3 &v)  {
    return (1/t) * v;
}

// Multiply vector and add all the values up
inline double dot(const vec3 &u, const vec3 &v) {
    return u.e[0] * v.e[0]
        + u.e[1] * v.e[1]
        + u.e[2] * v.e[2];
}

// Makes sure that the ratios of the vectors are the same and the sum of it is 1
inline vec3 unit_vector(vec3 v) {
    return v / v.length();
}

// Aliases for vector
using point3 = vec3; // 3D point
using color = vec3; // RGB color

#endif