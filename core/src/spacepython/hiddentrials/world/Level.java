package spacepython.hiddentrials.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.physics.Updateable;
import spacepython.hiddentrials.render.Renderable;
import spacepython.hiddentrials.render.Renderer;
import spacepython.hiddentrials.render.Renderer.RenderPosition;

public class Level implements Updateable, Renderable{
    public Color bgColor;
    public Map map;
    public String id, name;
    
    public Level(FileHandle file) {
        Renderer.submitForRendering(this, RenderPosition.BACK);
        Physics.submitForUpdating(this);
        JsonValue base = new JsonReader().parse(file);
        int[] bgColor = base.get("bgColor").asIntArray();
        this.bgColor = new Color(bgColor[0]/256, bgColor[1]/256, bgColor[2]/256, 1.0f);
        this.id = base.getString("id");
        this.name = base.getString("name");
        this.map = new Map(new Vector2(), file.parent().child(base.getString("map")));
    }

    public void render(Renderer renderer) {
        this.map.render(renderer);
    }

    public boolean shouldRender() {
        return true;
    }

    public boolean renderInMenu() {
        return false;
    }

    public void update(Physics physics) {
        this.map.update(physics);
    }

    public boolean shouldUpdate() {
        return true;
    }

    public boolean updateInMenu() {
        return false;
    }
}