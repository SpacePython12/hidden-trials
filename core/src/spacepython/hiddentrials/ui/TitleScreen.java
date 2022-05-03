package spacepython.hiddentrials.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import spacepython.hiddentrials.GameMain;
import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.physics.Updateable;
import spacepython.hiddentrials.render.Renderable;
import spacepython.hiddentrials.render.Renderer;

public class TitleScreen implements Screen, Updateable, Renderable {
    private TextureAtlas atlas;
    private TextureRegion titleCard;
    private Button playButton;
    private Vector2 anchorPos = new Vector2();
    private static final float padding = 32;
    private static final float scale = 4;

    public TitleScreen() {
        Renderer.submitForRendering(this);
        Physics.submitForUpdating(this);
        this.atlas = new TextureAtlas(Gdx.files.internal("menu/titleMenu.atlas"));
        this.titleCard = this.atlas.findRegion("titleCard");
        this.playButton = new Button(this.atlas.findRegion("playButton", 0), this.atlas.findRegion("playButton", 1), scale) {
            @Override
            public void onClick() {
                GameMain.getInstance().setScreen(null);
            }
        };
        
    }

    public void render(Renderer renderer) {
        renderer.render(this.titleCard, (Gdx.graphics.getWidth()/2)-((this.titleCard.getRegionWidth()*scale)/2), (Gdx.graphics.getHeight()/2)+padding, this.titleCard.getRegionWidth()*scale, this.titleCard.getRegionHeight()*scale);
    }

    public boolean shouldRender() {
        return true;
    }

    public boolean renderInMenu() {
        return true;
    }

    
    public void update(Physics physics) {
        this.playButton.pos.set((Gdx.graphics.getWidth()/2)-((this.playButton.size.x)/2), (Gdx.graphics.getHeight()/2)-padding-(this.playButton.size.y));
    }

    
    public boolean shouldUpdate() {
        return true;
    }

    
    public boolean updateInMenu() {
        return true;
    }

    
    public void show() {

    }

    public void render(float delta) {
        
    }

    
    public void resize(int width, int height) {

    }

    
    public void pause() {
        
        
    }

    
    public void resume() {
        
        
    }

    
    public void hide() {
        
        
    }

    
    public void dispose() {
        
        
    }
    
}
