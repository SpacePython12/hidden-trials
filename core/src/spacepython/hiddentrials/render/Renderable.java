package spacepython.hiddentrials.render;

import java.util.ArrayList;

public interface Renderable {
    public static ArrayList<Renderable> instances = new ArrayList<>();

    public void render(Renderer renderer);
    public boolean shouldRender();
    public boolean renderInMenu();
}