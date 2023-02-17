#include "vec3.h"
#include "color.h"
#include "ray.h"

#include <iostream>

double hit_sphere(const point3& center, double radius, const ray& r) {
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
    // -1 since it doesn't it
    if (discriminant < 0) {
        return -1.0;
    }
    // Return the point it hits
    else {
        return (-b - sqrt(discriminant)) / a;
    }
}

color ray_color(const ray& r) {
    // Create a sphere at 0, 0, -1 with a radius of 0.5, and pass the ray r
    auto t = hit_sphere(point3(0, 0, -1), 0.5, r);
    // Colored sphere
    if (t > 0.0) {
        // Get the point of intersection
        vec3 N = unit_vector(r.at(t) - vec3(0, 0, -1));
        return 0.5 * color(N.x()+1, N.y()+1, N.z()+1);
    }
    // Background of the image
    // Get direction of ray
    vec3 unit_direction = unit_vector(r.direction());
    // Get Y Value of the pixel
    t = 0.5 * (unit_direction.y() + 1.0);
    // Blended Value = (1-t) * startValue + t * endValue for any time t
    return (1.0 - t) * color(1.0, 1.0, 1.0) + t * color(0.5, 0.7, 1.0);
}

int main() {
    // Window's aspect ratio
    const auto aspect_ratio = 16.0 / 9.0;
    const int image_width = 400;
    const int image_height = static_cast<int>(image_width / aspect_ratio);
    
    // Camera
    auto viewport_height = 2.0;
    auto viewport_width = aspect_ratio * viewport_height;
    auto focal_length = 1.0;

    // Define some points
    auto origin = point3(0, 0, 0);
    // Horizontal offset of the viewport
    auto horizontal = vec3(viewport_width, 0, 0);
    // Vertical offset of the viewport
    auto vertical = vec3(0, viewport_height, 0);
    // Get the lower left corner of the viewport
    auto lower_left_corner = origin - horizontal/2 - vertical/2 - vec3(0, 0, focal_length);

    // Render
    std::cout << "P3\n" << image_width << ' ' << image_height << "\n255\n";

            std::cerr << "test" << std::endl;
    for (int j = image_height - 1; j >= 0; --j) {
        std::cerr << "\rLines remaining: " << j << " " << std::flush;
        for (int i = 0; i < image_width; ++i) {
            auto u = double(i) / (image_width - 1);
            auto v = double(j) / (image_height - 1);
            ray r(origin, lower_left_corner + u * horizontal + v * vertical - origin);
            color pixel_color = ray_color(r);
            write_color(std::cout, pixel_color);
        }
    }
    std::cerr << "\nDone.\n";
}
