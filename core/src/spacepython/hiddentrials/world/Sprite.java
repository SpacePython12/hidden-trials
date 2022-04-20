package spacepython.hiddentrials.world;

import java.util.ArrayList;

import spacepython.hiddentrials.physics.*;
import spacepython.hiddentrials.render.*;

public class Sprite extends Renderable{
    public static ArrayList<Sprite> instances = new ArrayList<>();
    public boolean shouldUpdate = true;
    public boolean updateInMenu = false;

    public Sprite() {
        super();
        Sprite.instances.add(this);
    }

    public void update(Physics phys) {

    }
}