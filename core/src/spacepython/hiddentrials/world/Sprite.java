package spacepython.hiddentrials.world;

import java.util.ArrayList;

import spacepython.hiddentrials.physics.*;
import spacepython.hiddentrials.render.*;

public abstract class Sprite implements Updateable, Renderable {
    public static ArrayList<Sprite> instances = new ArrayList<>();
    public boolean shouldRender = true, shouldUpdate = true, updateInMenu = false, renderInMenu = false;

    public Sprite() {
        Physics.submitForUpdating(this);
        Renderer.submitForRendering(this);
        Sprite.instances.add(this);
    }

    public abstract void render(Renderer renderer);

    public abstract void update(Physics physics);

    public boolean shouldRender() {
        return this.shouldRender;
    }

    public boolean shouldUpdate() {
        return this.shouldUpdate;
    }

    public boolean renderInMenu() {
        return this.renderInMenu;
    }

    public boolean updateInMenu() {
        return this.updateInMenu;
    }
}