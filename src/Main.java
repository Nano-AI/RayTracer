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

        Material material_center = new Lambertian(Utils.toColorVector(new Color(255, 0, 0)));
        Material material_left = new Dielectric(1.5);
        Material material_bubble = new Dielectric(1.00 / 1.50);
        Material material_right = new Metal(new Vector3(0.8, 0.6, 0.2), 0.1);

        Material material_ground = new Lambertian(Utils.toColorVector(new Color(124, 252, 0)));

        world.add(new Sphere(new Vector3(0, -1000.5, -1), -1000, material_ground));

        world.add(new Sphere(new Vector3(1, 0, -1), 0.5, material_left));
        world.add(new Sphere(new Vector3(0, 0.25, -3), 0.5, material_center));
        world.add(new Sphere(new Vector3(-1, 0, -5), 0.5, material_right));

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