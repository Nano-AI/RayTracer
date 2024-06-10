import com.helper.Utils;
import com.helper.Vector3;
import com.materials.Dielectric;
import com.materials.Lambertian;
import com.materials.Material;
import com.materials.Metal;
import com.objects.Camera;
import com.objects.HittableList;
import com.objects.Sphere;
import com.window.ImageViewer;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Collection;

public class Main {
    // aspect ratio of the image
    public static final double aspectRatio = 16.0 / 9.0;
    // height of the image
    public static final int imageHeight = 800;
    // width based off ratio
    public static final int imageWidth = (int) (aspectRatio * imageHeight);
    public static void main(String[] args) {
        // create an image viewer using swing
        ImageViewer viewer = new ImageViewer(imageWidth, imageHeight);
        HittableList world = new HittableList();

        Material material_ground = new Lambertian(new Vector3(0.0, 0.0, 0));
        world.add(new Sphere(new Vector3(0, -100.5, -1.0), 100.0, material_ground));
        for (double i = -1.0; i < 3; i++) {
            for (double j = -1.0; j < 3; j++) {
                double t = Math.random();
                Material mat;
                if (t < 1.0 / 3.0) {
                    mat = new Lambertian(Vector3.random());
                } else if (t < 2.0 / 3.0) {
                    mat = new Dielectric(Utils.randomDouble(0.001, 2));
                } else {
                    mat = new Metal(Vector3.random(), Math.random());
                }
                world.add(new Sphere(new Vector3(i, j, -1), Utils.randomDouble(0.1, 0.3), mat));
            }
        }
//        Material material_center = new Lambertian(new Vector3(0.1, 0.2, 0.5));
//        Material material_left = new Dielectric(1.5);
//        Material material_bubble = new Dielectric(1.00 / 1.50);
//        Material material_right = new Metal(new Vector3(0.8, 0.6, 0.2), 0.1);
//
//        world.add(new Sphere(new Vector3(1, 0, -1), 0.5, material_center));
//        world.add(new Sphere(new Vector3(0, 0, -3), 0.5, material_center));
//        world.add(new Sphere(new Vector3(-1, 0, -5), 0.5, material_center));

        Camera cam = new Camera(viewer);
        cam.aspect_ratio = aspectRatio;
        cam.image_height = imageHeight;
        cam.image_width = imageWidth;
//         cam.samples_per_pixel = 50;
        cam.samples_per_pixel = 10;
        cam.max_depth = 10;

        cam.vfov = 90;

        cam.render(world);
        viewer.display();
    }
}