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

import java.lang.reflect.Array;
public class Level implements Updateable, Renderable{
    private static Level currentLevel;
    public Color bgColor;
    public Map[] maps;
    public String id, name;
    protected boolean isActive = false;
    
    public Level(FileHandle file) {
        Renderer.submitForRendering(this, RenderPosition.BACK);
        Physics.submitForUpdating(this);
        JsonValue base = new JsonReader().parse(file);
        int[] bgColor = base.get("bgColor").asIntArray();
        this.bgColor = new Color(bgColor[0]/256, bgColor[1]/256, bgColor[2]/256, 1.0f);
        this.id = base.getString("id");
        this.name = base.getString("name");
        JsonValue layers = base.get("layers");
        this.maps = new Map[layers.size];
        JsonValue layer;
        int[] tileSize;
        for (int l = 0; l < layers.size; l++) {
            layer = layers.get(l);
            tileSize = layer.get("tileDimensions").asIntArray();
            this.maps[l] = new Map();
            this.maps[l].loadTextures(file.parent().child(layer.getString("atlas")), tileSize[0], tileSize[1], this.intArray2dFromVal(layer.get("tileTextures")));
            this.maps[l].loadLayout(file.parent().child(layer.getString("layout")), this.colorArrayFromHexArray(layer.get("tileCIDs").asStringArray()));
        }
    }

    public void render(Renderer renderer) {
        if (!this.isActive) return;
        for (Map map: this.maps) {
            map.render(renderer);
        }
    }

    public boolean shouldRender() {
        return true;
    }

    public boolean renderInMenu() {
        return false;
    }

    public void update(Physics physics) {
        if (!this.isActive) return;
        for (Map map: this.maps) {
            map.update(physics);
        }
    }

    public boolean shouldUpdate() {
        return true;
    }

    public boolean updateInMenu() {
        return false;
    }

    public static void setLevel(Level level) {
        if (currentLevel != null) currentLevel.isActive = false;
        currentLevel = level;
        currentLevel.isActive = true;
    }

    public static Level getLevel() {
        return currentLevel;
    }

    public void dispose() {
        for (Map map: this.maps) {
            map.dispose();
        }
    }

    public int[][] intArray2dFromVal(JsonValue v) {
        int[][] res = new int[v.size][v.get(0).size];
        for (int i = 0; i < v.size; i++) {
            res[i] = v.get(i).asIntArray();
        }
        return res;
    }

    public int[] colorArrayFromHexArray(String[] v) {
        int[] res = new int[v.length];
        for (int i = 0; i < v.length; i++) {
            res[i] = Integer.decode(v[i]);
        }
        return res;
    }
}