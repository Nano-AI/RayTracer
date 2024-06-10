public class Ray {
    private Vector3 origin;
    private Vector3 direction;
    public Ray() {

    }

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getOrigin() {
        return this.origin;
    }

    public Vector3 getDirection() {
        return this.direction;
    }

    public Vector3 at(double t) {
        return Vector3.add(origin, Vector3.multiply(t, direction));
    }
}
