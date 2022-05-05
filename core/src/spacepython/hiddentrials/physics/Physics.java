package spacepython.hiddentrials.physics;

import spacepython.hiddentrials.GameMain;
import spacepython.hiddentrials.util.Profiler;

public class Physics {
    public boolean running;
    public static final float nsToSec = 1000000000F;
    public static final float ups = 60F; 
    public static final float rate = (1F / ups);
    private float deltaTime;
    private int tickID = 0;
    public volatile boolean ready;
    public Profiler profiler;

    public Physics() {
        this.running = true;
        this.profiler = new Profiler();
    }

    public void start() {
        long lastTime = System.nanoTime();
        long thisTime;

        while (this.running) {
            this.profiler.start();
            if (this.tickID >= ups) {
                this.tickID = 0;
            }
            this.profiler.push("update");
            this.isReady(false);
            update();
            this.tickID++;
            this.isReady(true);
            this.profiler.popPush("sync");
            while (this.isReady());
            this.profiler.popPush("cycleLimiter");
            thisTime = System.nanoTime();
            deltaTime = ((thisTime - lastTime) / nsToSec);
            if (deltaTime < rate) {
                try {
                    Thread.sleep((long) Math.round((rate - deltaTime) * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (deltaTime - rate > 0.1 ) {
                System.out.println("Update thread took too long: " + (deltaTime - rate) + "s");
            }
            lastTime = thisTime;
            this.profiler.pop();
            this.profiler.stop();
        }
    }

    public void update() {
        this.profiler.push("updateBatch");
        for (Updateable u: Updateable.instances) {
            if (u.shouldUpdate() && !(GameMain.getInstance().getScreen() != null ^ u.updateInMenu())) {
                this.profiler.push("update" + u.getClass().getSimpleName());
                u.update(this);
                this.profiler.pop();
            }
        }
        this.profiler.popPush("updateSystems");
        GameMain.getInstance().update();
        this.profiler.pop();
    }

    public void dispose() {
        this.running = false;
        System.out.println("Physics system status:\n" + this.profiler.getAverageData());
    }

    public float getDeltaTime() {
        return this.deltaTime;
    }

    public int getTickId() {
        return this.tickID;
    }

    public static void submitForUpdating(Updateable u) {
        Updateable.instances.add(u);
    }

    public static void stopUpdating(Updateable u) {
        Updateable.instances.remove(u);
    }

    public boolean isReady() {
        return this.ready;
    }

    public void isReady(boolean b) {
        this.ready = b;
    }
}