package spacepython.hiddentrials.render;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import spacepython.hiddentrials.physics.*;

public abstract class Sprite implements Updateable, Renderable {
    public static ArrayList<Sprite> instances = new ArrayList<>();
    public boolean shouldRender = true, shouldUpdate = true, updateInMenu = false, renderInMenu = false;
    public Model model;
    public Vector2 pos, size, velocity;

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

    public void allowRender(boolean state) {
        this.shouldRender = state;
    }

    public void allowUpdate(boolean state) {
        this.shouldUpdate = state;
    }
}