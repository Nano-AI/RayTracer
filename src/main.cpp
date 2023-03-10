#include "utils.h"

#include "color.h"
#include "hittable_list.h"
#include "sphere.h"

#include "camera.h"

#include <iostream>

color ray_color(const ray& r, const hittable& world, int depth) {
    if (depth <= 0)
        return color(0, 0, 0);

    hit_record rec;
    if (world.hit(r, 0.001, infinity, rec)) {
        point3 target = rec.p + rec.normal + random_unit_vector();
        return 0.5 * ray_color(ray(rec.p, target - rec.p), world, depth - 1);
    }
    vec3 unit_direction = unit_vector(r.direction());
    // Get Y Value of the pixel
    auto t = 0.5 * (unit_direction.y() + 1.0);
    // Blended Value = (1-t) * startValue + t * endValue for any time t
    return (1.0 - t) * color(1.0, 1.0, 1.0) + t * color(0.5, 0.7, 1.0);
}

int main() {
    // Window's aspect ratio
    const auto aspect_ratio = 16.0 / 9.0;
    const int image_width = 400;
    const int image_height = static_cast<int>(image_width / aspect_ratio);
    const int samples_per_pixel = 50;
    const int max_depth = 50;
    
    // Camera
    camera cam;

    // World
    hittable_list world;
    world.add(make_shared<sphere>(point3(0, 0, -1), 0.5));
    world.add(make_shared<sphere>(point3(0, -100.5, -1), 100));
    world.add(make_shared<sphere>(point3(1, 0, -1), 0.5));

    // Render
    std::cout << "P3\n" << image_width << ' ' << image_height << "\n255\n";

            std::cerr << "test" << std::endl;
    for (int j = image_height - 1; j >= 0; --j) {
        std::cerr << "\rLines remaining: " << j << " " << std::flush;
        for (int i = 0; i < image_width; ++i) {
            // anti-aliasing
            color pixel_color(0, 0, 0);
            for (int s = 0; s < samples_per_pixel; ++s) {
                auto u = (i + random_double()) / (image_width - 1);
                auto v = (j + random_double()) / (image_height - 1);
                ray r = cam.get_ray(u, v);
                pixel_color += ray_color(r, world, max_depth);
            }
            write_color(std::cout, pixel_color, samples_per_pixel);
        }
    }
    std::cerr << "\nDone.\n";
}
