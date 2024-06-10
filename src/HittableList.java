import java.util.ArrayList;

public class HittableList implements Hittable {
    ArrayList<Hittable> objects;

    public HittableList() {
        objects = new ArrayList<Hittable>();
    }

    public HittableList(Hittable object) {
        objects.add(object);
    }

    public void clear() {
        objects.clear();
    }

    public void add(Hittable object) {
        objects.add(object);
    }

    @Override
    public boolean hit(Ray r, Interval ray_t, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;
        double closest = ray_t.max;

        for (Hittable object : objects) {
            if (object.hit(r, new Interval(ray_t.min, closest), tempRec)) {
                hitAnything = true;
                closest = tempRec.t;
                rec.set(tempRec);
                rec = tempRec;
            }
        }
        return hitAnything;
    }
}
