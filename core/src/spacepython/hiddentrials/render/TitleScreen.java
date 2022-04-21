package spacepython.hiddentrials.render;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import spacepython.hiddentrials.GameMain;
import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.physics.Updateable;

public class TitleScreen implements Screen, Renderable, Updateable {
    private TextureAtlas atlas;
    private Vector2 anchorPos;
    private Group group;
    private Button playButton, optionsButton, webButton;
    private Image titleCard;
    private float unit = 80; // A button should be one unit tall.

    public TitleScreen() {
        super();
        Renderer.submitForRendering(this);
        Physics.submitForUpdating(this);
        this.anchorPos = new Vector2();
        this.atlas = new TextureAtlas(Gdx.files.internal("menu/titleMenu.atlas"));
        this.group = new Group();
        this.titleCard = new Image(this.atlas.findRegion("titleCard"));
        this.titleCard.scaleBy(5);
        this.group.addActor(this.titleCard);
        this.playButton = new Button(new Image(this.atlas.findRegion("playButton", 0)).getDrawable(), new Image(this.atlas.findRegion("playButton", 1)).getDrawable());
        this.playButton.setTransform(true);
        this.playButton.scaleBy(4);
        this.group.addActor(this.playButton);
        this.optionsButton = new Button(new Image(this.atlas.findRegion("optionsButton", 0)).getDrawable(), new Image(this.atlas.findRegion("optionsButton", 1)).getDrawable());
        this.optionsButton.setTransform(true);
        this.optionsButton.scaleBy(4);
        this.group.addActor(this.optionsButton);
        this.webButton = new Button(new Image(this.atlas.findRegion("webButton", 0)).getDrawable(), new Image(this.atlas.findRegion("webButton", 1)).getDrawable());
        this.webButton.setTransform(true);
        this.webButton.scaleBy(4);
        this.group.addActor(this.webButton);
    }

    public void show() {
        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMain.getInstance().setScreen(null);
            }
        });
        this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void render(float delta) {
        this.group.act(delta);
    }

    public void resize(int width, int height) {
        this.anchorPos.x = width/2;
        this.anchorPos.y = height/2;
        this.titleCard.setPosition(this.anchorPos.x - ((this.titleCard.getWidth() * this.titleCard.getScaleX())/2), this.anchorPos.y + this.unit, Align.bottom);
        this.playButton.setPosition(this.anchorPos.x - ((this.playButton.getWidth() * this.playButton.getScaleX())/2), this.anchorPos.y - this.unit, Align.top);
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
        this.group.draw(renderer.batch, 1.0f);
        renderer.setToLineMode();
        this.group.drawDebug(renderer.sbatch);
    }

    public boolean shouldRender() {
        return true;
    }

    public boolean renderInMenu() {
        return true;
    }

    public void update(Physics physics) {
        this.render(physics.getDeltaTime());
    }

    public boolean shouldUpdate() {
        return true;
    }

    public boolean updateInMenu() {
        return true;
    }
    
}