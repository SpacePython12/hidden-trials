package spacepython.hiddentrials.world;

import com.badlogic.gdx.math.Vector2;

import spacepython.hiddentrials.physics.Hitbox;
import spacepython.hiddentrials.render.Model;

public class Being extends Sprite {
    public Hitbox hitbox;
    public Model model;
    public Vector2 pos, size, velocity;
}