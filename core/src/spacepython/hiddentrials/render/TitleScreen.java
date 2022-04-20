package spacepython.hiddentrials.render;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class TitleScreen implements Screen, Renderable {

    public TitleScreen() {
        super();
        Renderer.submitForRendering(this);
    }

    public void show() {
        
    }

    public void render(float delta) {
        // TODO Auto-generated method stub

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

    public void render(Renderer renderer) {
        this.render(renderer.getDeltaTime());   
    }

    public boolean shouldRender() {
        return true;
    }

    public boolean renderInMenu() {
        return true;
    }
    
}