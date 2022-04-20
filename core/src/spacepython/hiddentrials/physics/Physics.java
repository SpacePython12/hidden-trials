package spacepython.hiddentrials.physics;

import com.badlogic.gdx.math.Vector2;

import spacepython.hiddentrials.world.Sprite;
import spacepython.hiddentrials.GameMain;

public class Physics {
    public boolean running;
    private static final float nsToSec = 1000000000F;
    public static final float ups = 60F;
    private static final float rate = (1F / ups);
    private float deltaTime;

    public Physics() {
        this.running = true;
    }

    public void start() {
        long lastTime = System.nanoTime();
        long thisTime;
        while (this.running) {
            update();
            thisTime = System.nanoTime();
            deltaTime = ((thisTime - lastTime) / nsToSec);
            if (deltaTime < rate) {
                try {
                    Thread.sleep((long) Math.round((rate - deltaTime) * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lastTime = thisTime;
        }
    }

    public void update() {
        for (Updateable u: Updateable.instances) {
            if (u.shouldUpdate && !(GameMain.getInstance().getScreen() != null ^ u.updateInMenu)) {
                u.update(this);
            }
        }
        for (Sprite s: Sprite.instances) {
            if (s.shouldUpdate && !(GameMain.getInstance().getScreen() != null ^ s.updateInMenu)) {
                s.update(this);
            }
        }
        GameMain.getInstance().update();
    }

    public void dispose() {
        this.running = false;
    }

    public float getDeltaTime() {
        return this.deltaTime;
    }
}