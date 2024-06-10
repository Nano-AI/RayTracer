package com.objects;

import com.helper.Utils;
import com.helper.Vector2;
import com.helper.Vector3;

import java.awt.*;
import java.io.FileNotFoundException;

import com.helper.*;
import com.window.*;

public class Camera {
    public double aspect_ratio = 1.0;
    public int image_width;
    public int image_height;

    public int samples_per_pixel = 10; // count of random samples per pixel for anti-aliasing
    public int max_depth = 10;
    public double vfov = 90;
    public Vector3 lookfrom = new Vector3(0, 0, 0);
    public Vector3 lookat = new Vector3(0, 0, -1);
    public Vector3 vup = new Vector3(0, 1, 0);
    public double defocus_angle = 0;
    public double focus_dist = 10;
    private Vector3 defocus_disk_u;
    private Vector3 defocus_disk_v;

    private double pixel_samples_scale; // color scale factor for a sum of pixel samples
    private Vector3 u, v, w;

    private Vector3 center;
    private Vector3 pixel00_loc;
    private Vector3 pixel_delta_u;
    private Vector3 pixel_delta_v;
    private Vector2 pixelAt = new Vector2(0, 0);
    private ProgressBar progressBar;
    private FileManager output;
    // display the image using gui
    private ImageViewer image;
    // if the user wants to write the image to a .ppm file
    private boolean writeImage;


    // constructor for writing to file
    public Camera(String output, int width, int height) throws FileNotFoundException {
        this.writeImage = true;
        this.output = new FileManager(output, width, height);
        progressBar = new ProgressBar("Rendering...", 0, height);
        progressBar.display();
    }

    // constructor for displaying in gui
    public Camera(ImageViewer output) {
        // set the image output
        this.image = output;
        // set write image to false
        this.writeImage = false;
        // set the size
        this.image_height = output.getSize().getY();
        // create a progress bar
        progressBar = new ProgressBar("Rendering...", 0, image_height);
        // display progress bar
        progressBar.display();
    }

    private double linearToGamma(double linear) {
        if (linear > 0) {
            return Math.sqrt(linear);
        }
        return 0;
    }

    private Vector3 linearToGamma(Vector3 v) {
        return new Vector3(linearToGamma(v.getX()), linearToGamma(v.getY()), linearToGamma(v.getZ()));
    }
    public void render(Hittable world) {
        init();

        // iterate through width and height of image
        for (int j = 0; j < image_height; j++) {
            for (int i = 0; i < image_width; i++) {
                Vector3 pixel_color = new Vector3(0, 0, 0);
                for (int sample = 0; sample < samples_per_pixel; sample++) {
                    Ray r = getRay(i, j);
                    pixel_color.add(rayColor(r, max_depth, world));
                }
                // write it
                writeRGB(linearToGamma(Vector3.multiply(pixel_samples_scale, pixel_color)).toColor());
            }
        }
    }

    private void init() {
        pixel_samples_scale = 1.0 / samples_per_pixel;
        center = new Vector3();
        center.set(lookfrom);

//         double focal_length = Vector3.subtract(lookfrom, lookat).length();
//        double focal_length = 1.0;
        double theta = Math.toRadians(vfov);
        double h = Math.tan(theta / 2.0);
        double viewport_height = 2.0 * h * focus_dist;
        double viewport_width = viewport_height * (double) image_width / image_height;

        w = Vector3.unitVector(Vector3.subtract(lookfrom, lookat));
        u = Vector3.unitVector(Vector3.cross(vup, w));
        v = Vector3.cross(w, u);

//      TODO: PlEASE FIX ROTATION??/
//        Vector3 viewport_u = Vector3.multiply(viewport_width, u);
//        Vector3 viewport_v = Vector3.multiply(viewport_height, Vector3.multiply(-1, v));
        Vector3 viewport_u = new Vector3(viewport_width, 0, 0);
        Vector3 viewport_v = new Vector3(0, -1 * viewport_height, 0);

        pixel_delta_u = Vector3.divide(viewport_u, image_width);
        pixel_delta_v = Vector3.divide(viewport_v, image_height);

        Vector3 viewport_upper_left = Vector3.subtract(center, Vector3.multiply(focus_dist, w));
//        Vector3 viewport_upper_left = Vector3.subtract(center, new Vector3(0, 0, focal_length));
        viewport_upper_left.subtract(Vector3.divide(viewport_u, 2));
        viewport_upper_left.subtract(Vector3.divide(viewport_v, 2));

        pixel00_loc = Vector3.add(viewport_upper_left, Vector3.multiply(0.5, Vector3.add(pixel_delta_u, pixel_delta_v)));

        double defocus_radius = focus_dist * Math.tan(Math.toRadians(defocus_angle / 2));
        defocus_disk_u = Vector3.multiply(u, defocus_radius);
        defocus_disk_v = Vector3.multiply(v, defocus_radius);
    }

    private Ray getRay(int i, int j) {
        Vector3 offset = sampleSquare();
        Vector3 pixel_sample = Vector3.add(pixel00_loc, Vector3.add(
                Vector3.multiply((i + offset.getX()),  pixel_delta_u),
                Vector3.multiply((j + offset.getY()), pixel_delta_v)
        ));

        Vector3 rayOrigin = (defocus_angle <= 0) ? center : defocus_disk_sample();
        Vector3 rayDirection = Vector3.subtract(pixel_sample, rayOrigin);

        return new Ray(rayOrigin, rayDirection);
    }

    public Vector3 defocus_disk_sample() {
        Vector3 p = Vector3.randomInUnitDisk();
        return Vector3.add(center, Vector3.multiply(p.getX(), defocus_disk_u)).add(Vector3.multiply(p.getY(), defocus_disk_v));
    }

    private Vector3 sampleSquare() {
        return new Vector3(Utils.randomDouble() - 0.5, Utils.randomDouble() - 0.5, 0);
    }

    private Vector3 rayColor(Ray r, int depth, Hittable world) {
        if (depth <= 0) {
            return new Vector3(0, 0, 0);
        }

        HitRecord rec = new HitRecord();

        if (world.hit(r, new Interval(0.001, Interval.p_infinity), rec)) {
            Ray scattered = new Ray();
            Vector3 attenuation = new Vector3();
            if (rec.mat.scatter(r, rec, attenuation, scattered)) {
                return Vector3.multiply(attenuation, rayColor(scattered, depth - 1, world));
            }
            return new Vector3(0, 0, 0);
//            Vector3 direction = Vector3.add(rec.normal, Vector3.randomUnitVector());
//            return Vector3.multiply(0.5, rayColor(new Ray(rec.point, direction), depth - 1, world));
        }

        Vector3 unitDir = Vector3.unitVector(r.getDirection());
        double a = 0.5 * (unitDir.getY() + 1.0);
        return
                Vector3.add(
                        Vector3.multiply(1.0 - a, new Vector3(1, 1, 1)),
                        Vector3.multiply(a, new Vector3(0.5, 0.7, 1.0))
                );
    }

    // overload for writing RGB value
    public void writeRGB(Color color) {
        writeRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    // write an rgb value to image
    public void writeRGB(int r, int g, int b) {
        // get current position
        int x = pixelAt.getX();
        int y = pixelAt.getY();

        // update progress bar
        progressBar.updateBar(y);

        // if write to file, write it throgh printstream
        // otherwise, write it on bufferimage
        if (writeImage) {
            output.printf("%d %d %d\n", r, g, b);
        } else {
            image.setRGB(x, y, new Color(r, g, b));
        }

        // update x position
        pixelAt.setX(x + 1);
        // if x position is out of bounds, reset and increment y
        if (image.getSize().getX() == pixelAt.getX()) {
            pixelAt.set(0, y + 1);
        }
    }
}
