package spacepython.hiddentrials.physics;

import java.util.ArrayList;

public interface Updateable {
    public static ArrayList<Updateable> instances = new ArrayList<>();

    public void update(Physics physics);
    public boolean shouldUpdate();
    public boolean updateInMenu();
}