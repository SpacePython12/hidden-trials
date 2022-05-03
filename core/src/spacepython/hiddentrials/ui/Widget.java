package spacepython.hiddentrials.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import spacepython.hiddentrials.GameMain;
import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.render.Renderer;
import spacepython.hiddentrials.render.Sprite;

public abstract class Widget extends Sprite {
    public boolean inMenuOnly = true;

    public Widget() {
        super();
        this.pos = new Vector2();
        this.size = new Vector2();
        this.velocity = new Vector2();
    }

    public boolean isHovered() {
        Vector3 point = GameMain.getInstance().renderer.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return ((point.x > this.pos.x && point.x < this.pos.x+this.size.x) && (point.y > this.pos.y && point.y < this.pos.y+this.size.y));
    }

    public boolean isClicked() {
        return this.isHovered() && Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public void render(Renderer renderer) {
        renderer.render(this.model.getTexture(), this.pos.x, this.pos.y, this.size.x, this.size.y);
    }

    public abstract void update(Physics physics);

    public boolean renderInMenu() {
        return this.inMenuOnly;
    }

    public boolean updateInMenu() {
        return this.inMenuOnly;
    }
}
