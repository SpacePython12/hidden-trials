package spacepython.hiddentrials.render;

import java.util.ArrayList;

public class Renderable {
    public static ArrayList<Renderable> instances = new ArrayList<>();
    public boolean shouldRender = true;
    public boolean renderInMenu = false;

    public Renderable() {
        Renderable.instances.add(this);
    }

    public void render(Renderer renderer) {

    }
}