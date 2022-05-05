package spacepython.hiddentrials.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import org.lwjgl.Sys;

import spacepython.hiddentrials.Constants;
import spacepython.hiddentrials.GameMain;
import spacepython.hiddentrials.util.Profiler;
import spacepython.hiddentrials.world.Level;

public class Renderer {
    public SpriteBatch batch;
    public ShapeRenderer sbatch;
    public OrthographicCamera camera;
    public int width, height;
    public boolean ready = true, rendering = false;
    public volatile Profiler profiler;
    private float deltaTime = 0f;
    public enum RenderPosition {
        BACK,
        FRONT,
    }
    
    public Renderer() {
        Gdx.graphics.setTitle(Constants.title + " v" + Constants.version + " (" + Constants.status + ")");
        this.profiler = new Profiler();
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.sbatch = new ShapeRenderer();
        this.sbatch.setAutoShapeType(true);
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
        this.deltaTime += Gdx.graphics.getDeltaTime();
        if (!GameMain.getInstance().physics.isReady()) return;
        {
            this.profiler.start();
            this.profiler.push("clear");
            if (Level.getLevel() != null) {
                ScreenUtils.clear(Level.getLevel().bgColor);
            } else {
                ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1.0f);
            }
            this.profiler.popPush("renderScreen");
            if (GameMain.getInstance().getScreen() != null) {
                GameMain.getInstance().getScreen().render(this.getDeltaTime());
            }
            this.profiler.popPush("updateProjection");
            this.batch.setProjectionMatrix(this.camera.combined);
            this.sbatch.setProjectionMatrix(this.camera.combined);
            this.profiler.popPush("renderBatch");
            this.batch.begin();
            this.sbatch.begin();
            for (Renderable r: Renderable.instances) {
                if (r.shouldRender() && !(GameMain.getInstance().getScreen() != null ^ r.renderInMenu())) {
                    this.profiler.push("render" + r.getClass().getSimpleName());
                    r.render(this);
                    this.profiler.pop();
                }
            }
            this.batch.end();
            this.sbatch.end();
            this.profiler.pop();
            GameMain.getInstance().physics.isReady(false);
            this.profiler.stop();
            this.deltaTime = 0f;
        }
        // System.out.println("Finished rendering. Next frame please!");
    }

    public float getDeltaTime() {
        return this.deltaTime;
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
        System.out.println("\nRendersystem status:\n" + this.profiler.getAverageData());
    }

    public static void submitForRendering(Renderable r, RenderPosition p) {
        switch(p) {
            case BACK: 
                Renderable.instances.add(0, r);
                break;
            case FRONT:
                Renderable.instances.add(r);
                break;
        }
    }

    public static void submitForRendering(Renderable r) {
        submitForRendering(r, RenderPosition.FRONT);
    }

    public static void stopRendering(Renderable r) {
        Renderable.instances.remove(r);
    }

    public void setToLineMode() {
        this.sbatch.set(ShapeType.Line);
    }

    public void setToFillMode() {
        this.sbatch.set(ShapeType.Filled);
    }

    public void setToPointMode() {
        this.sbatch.set(ShapeType.Point);
    }
}