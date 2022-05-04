package spacepython.hiddentrials.world;

import java.util.Arrays;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
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
    private int[][] layout;
    public Vector2 pos, scale = new Vector2(3, 3);
    public boolean loaded = true;
    
    public Map() {
        this.pos = new Vector2();
        this.tileTextures = new TextureRegion[0];
        this.layout = new int[0][0];
    }

    public void loadTextures(FileHandle atlas, int width, int height, int[][] positions) {
        this.atlas = new Texture(atlas);
        this.tileCount = positions.length;
        this.tileTextures = new TextureRegion[this.tileCount];
        int[] pos;
        for (int i = 0; i < positions.length; i++) {
            pos = positions[i];
            tileTextures[i] = new TextureRegion(this.atlas, pos[0]*width, pos[1]*height, width, height);
        }
    }

    public void loadLayout(FileHandle layout, int[] cids) {
        Pixmap pmap = new Pixmap(layout);
        this.layout = new int[pmap.getHeight()][pmap.getWidth()];
        int pix;
        for (int y = 0; y < pmap.getHeight(); y++) {
            for (int x = 0; x < pmap.getWidth(); x++) {
                this.layout[y][x] = -1;
                pix = pmap.getPixel(x, y);
                for (int c = 0; c < cids.length; c++) {
                    if (cids[c] == pix) {
                        this.layout[y][x] = c;
                        break;
                    }
                }
            }
        }
    }

    public void render(Renderer renderer) {
        TextureRegion currentTexture;
        for (int y = this.layout.length == 0 ? 0 : this.layout.length-1; y >= 0; y--) {
            for (int x = 0; x < this.layout[y].length; x++) {
                if (this.layout[y][x] < 0) continue;
                currentTexture = this.tileTextures[this.layout[y][x]];
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

    public boolean shouldRender() {
        return this.loaded;
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

    public void dispose() {
        this.atlas.dispose();
    }

    public int[][] getLayerData() {
        return this.layout;
    }

    public void setLayerData(int[][] ld) {
        this.layout = ld;
    }
    
    public TextureRegion[] getTileTextures() {
        return this.tileTextures;
    }

    public void getTileTextures(TextureRegion[] tt) {
        this.tileTextures = tt;
    }
}