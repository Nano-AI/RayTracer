public interface Hittable {
    boolean hit(Ray r, Interval ray_t, HitRecord rec);
}
