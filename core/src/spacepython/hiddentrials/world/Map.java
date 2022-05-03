package spacepython.hiddentrials.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.physics.Updateable;
import spacepython.hiddentrials.render.Renderable;
import spacepython.hiddentrials.render.Renderer;
import spacepython.hiddentrials.render.Renderer.RenderPosition;

public class Map implements Updateable, Renderable{
    private Texture atlas;
    private TextureRegion[] tileTextures;
    private int tileCount;
    private int[][][] layers;
    public Vector2 pos, scale = new Vector2(3, 3);
    
    public Map(Vector2 pos, FileHandle file) {
        this.pos = pos;
        JsonValue base = new JsonReader().parse(file);
        JsonValue textureData = base.get("tileData");
        this.atlas = new Texture(file.parent().child(textureData.getString("atlas")));
        JsonValue positions = textureData.get("positions");
        this.tileCount = positions.size;
        this.tileTextures = new TextureRegion[this.tileCount];
        int[] position;
        for (int i = 0; i < tileCount; i++) {
            position = positions.get(i).asIntArray();
            this.tileTextures[i] = new TextureRegion(this.atlas, position[0], position[1], position[2], position[3]);
        }
        JsonValue layerData = base.get("layerData");
        int[] layerDimensions = layerData.get("dimensions").asIntArray();
        JsonValue layers = layerData.get("layers");
        this.layers = new int[layers.size][layerDimensions[1]][layerDimensions[0]];
        for (int l = 0; l < layers.size; l++) {
            for (int y = 0; y < layerDimensions[1]; y++) {
                this.layers[l][y] = layers.get(l).get(y).asIntArray();
            }
        }
    }

    public void render(Renderer renderer) {
        TextureRegion currentTexture;
        for (int l = 0; l < this.layers.length; l++) {
            for (int y = 0; y < this.layers[l].length; y++) {
                for (int x = 0; x < this.layers[l][y].length; x++) {
                    currentTexture = this.tileTextures[this.layers[l][y][x]];
                    renderer.render(
                        currentTexture, 
                        ((x*currentTexture.getRegionWidth())+this.pos.x)*this.scale.x, 
                        ((y*currentTexture.getRegionHeight())+this.pos.y)*this.scale.y, 
                        currentTexture.getRegionWidth()*this.scale.x, 
                        currentTexture.getRegionHeight()*this.scale.y
                    );
                }
            }
        }
    }

    public boolean shouldRender() {
        return true;
    }

    public boolean renderInMenu() {
        return false;
    }

    public void update(Physics physics) {
        
    }

    public boolean shouldUpdate() {
        return true;
    }

    public boolean updateInMenu() {
        return false;
    }
}