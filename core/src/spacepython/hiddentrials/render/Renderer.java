package spacepython.hiddentrials.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import spacepython.hiddentrials.Constants;
import spacepython.hiddentrials.GameMain;

public class Renderer {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public int width, height;
    
    public Renderer() {
        Gdx.graphics.setTitle(Constants.title + " v" + Constants.version + " (" + Constants.status + ")");
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.batch.enableBlending();
        this.onResize();
    }

    public void onResize() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.camera.setToOrtho(false, this.width, this.height);
    }

    public void update() {
        this.camera.update();
    }

    public void renderFrame() {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1.0f);
        if (GameMain.getInstance().getScreen() != null) {
            GameMain.getInstance().getScreen().render(this.getDeltaTime());
        }
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        for (Renderable r: Renderable.instances) {
            if (r.shouldRender() && !(GameMain.getInstance().getScreen() != null ^ r.renderInMenu())) {
                r.render(this);
            }
        }
        this.batch.end();
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public void render(Texture texture, float x, float y) {
        this.batch.draw(texture, x, y);
    }

    public void render(TextureRegion texture, float x, float y) {
        this.batch.draw(texture, x, y);
    }

    public void render(Texture texture, float x, float y, float width, float height) {
        this.batch.draw(texture, x, y, width, height);
    }

    public void render(TextureRegion texture, float x, float y, float width, float height) {
        this.batch.draw(texture, x, y, width, height);
    }

    public void render(Texture texture, float x, float y, float width, float height, float rotation) {
        this.batch.draw(new TextureRegion(texture), x, y, 0, 0, width, height, 1, 1, rotation);
    }

    public void render(TextureRegion texture, float x, float y, float width, float height, float rotation) {
        this.batch.draw(texture, x, y, 0, 0, width, height, 1, 1, rotation);
    }

    public Vector2 getScreenCoords(float x, float y) {
        return new Vector2(x+this.camera.position.x-this.camera.viewportWidth/2, y+this.camera.position.y-this.camera.viewportHeight/2);
    }

    public void dispose() {
        this.batch.dispose();
    }

    public static void submitForRendering(Renderable r) {
        Renderable.instances.add(r);
    }
}