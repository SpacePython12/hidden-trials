package spacepython.hiddentrials.physics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Hitbox implements Updateable {
    public static ArrayList<Hitbox> instances = new ArrayList<>();
    public Vector2 pos, size, velocity;
    public boolean noClip, shouldUpdate, updateInMenu;

    public Hitbox(Vector2 pos, Vector2 size, Vector2 velocity) {
        super();
        Hitbox.instances.add(this);
        this.pos = pos;
        this.size = size;
        this.velocity = velocity;
        this.noClip = false;
    }

    public Hitbox(Vector2 pos, Vector2 size) {
        this(pos, size, new Vector2(0, 0));
    }

    public void update(Physics physics) {
        physics.profiler.push("updatePosition");
        this.pos.add(this.velocity);
        physics.profiler.pop();
    }

    public float getLeft() {
        return this.pos.x;
    }

    public float getBottom() {
        return this.pos.y;
    }

    public float getWidth() {
        return this.size.x;
    }

    public float getHeight() {
        return this.size.y;
    }

    public float getRight() {
        return this.pos.x + this.size.x;
    }

    public float getTop() {
        return this.pos.y + this.size.y;
    }

    public float getCenterX() {
        return this.pos.x + (this.size.x / 2);
    }

    public float getCenterY() {
        return this.pos.y + (this.size.y / 2);
    }

    public void offset(Vector2 o) {
        this.pos.add(o);
    }

    public void accelerate(Vector2 a) {
        this.velocity.add(a);
    }
    
    // https://stackoverflow.com/a/31022629
    private static float intersects(float alow, float ahigh, float blow, float bhigh) {
        float hs = Math.max(alow, blow);
        float le = Math.min(ahigh, bhigh);
        return le - hs;
    }

    // https://stackoverflow.com/a/31022629
    public static Vector2 getOverlap(Hitbox a, Hitbox b) {
        if (a.noClip || b.noClip) return new Vector2(0, 0);
        float xi = intersects(a.getLeft(), a.getRight(), b.getLeft(), b.getRight());
        float yi = intersects(a.getBottom(), a.getTop(), b.getBottom(), b.getTop());
        if (xi > 0 && yi > 0) {
            if (a.getLeft() <= b.getLeft()) {
                xi = -xi;
            }
            if (a.getBottom() <= b.getBottom()) {
                yi = -yi;
            }
            if (Math.min(Math.abs(xi), Math.abs(yi)) == Math.abs(yi)) {
                return new Vector2(0, yi);
            } else if (Math.min(Math.abs(xi), Math.abs(yi)) == Math.abs(xi)) {
                return new Vector2(xi, 0);
            } else {
                return new Vector2(xi, yi);
            }
        } else {
            return new Vector2(0, 0);
        }
    }

    public static boolean overlaps(Hitbox a, Hitbox b) {
        return getOverlap(a, b).isZero();
    }

    public static float distance(Hitbox a, Hitbox b) {
        return (float) Math.sqrt(Math.pow(b.getCenterX() - a.getCenterX(), 2) + Math.pow(b.getCenterY() - a.getCenterY(), 2));
    }

    public boolean shouldUpdate() {
        return this.shouldUpdate;
    }

    public boolean updateInMenu() {
        return this.updateInMenu;
    }
}