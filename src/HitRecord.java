public class HitRecord {
    public Vector3 point;
    public Vector3 normal;
    public double t;
    public boolean frontFace;

    // pick the right normal; outside or inside to have it always point outwards
    public void setFaceNormal(Ray r, Vector3 outwardNormal) {
        frontFace = Vector3.dot(r.getDirection(), outwardNormal) < 0;
        normal = frontFace ? outwardNormal : Vector3.multiply(-1, outwardNormal);
    }

    @Override
    public String toString() {
        return String.format("{point: %s, normal: %s, t: %f, front: %d}", point, normal, t, frontFace ? 1 : 0);
    }

    public void set(HitRecord tempRec) {
        this.frontFace = tempRec.frontFace;
        this.normal = tempRec.normal;
        this.point = tempRec.point;
        this.t = tempRec.t;
    }
}
