import java.io.FileNotFoundException;

public class Main {
    // aspect ratio of the image
    public static final double aspectRatio = 16.0 / 9.0;
    // height of the image
    public static final int imageHeight = 400;
    // width based off ratio
    public static final int imageWidth = (int) (aspectRatio * imageHeight);
    public static void main(String[] args) throws FileNotFoundException {
        // create an image viewer using swing
        ImageViewer viewer = new ImageViewer(imageWidth, imageHeight);
        HittableList world = new HittableList();

        world.add(new Sphere(new Vector3(0, 0, -1), 0.5));
        world.add(new Sphere(new Vector3(0, -100.5, -1), 100));

        Camera cam = new Camera(viewer);
        cam.aspect_ratio = aspectRatio;
        cam.image_height = imageHeight;
        cam.image_width = imageWidth;

        cam.render(world);
        viewer.display();
    }
}