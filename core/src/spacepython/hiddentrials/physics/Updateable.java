package spacepython.hiddentrials.physics;

import java.util.ArrayList;

public class Updateable {
    public static ArrayList<Updateable> instances = new ArrayList<>();
    public boolean shouldUpdate = true;
    public boolean updateInMenu = false;

    public Updateable() {
        Updateable.instances.add(this);
    }

    public void update(Physics physics) {

    }
}