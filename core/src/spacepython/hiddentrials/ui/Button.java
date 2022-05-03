package spacepython.hiddentrials.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.render.Renderer;

public class Button extends Widget {
    public TextureRegion normalTex, pressedTex;

    public Button(TextureRegion normal, TextureRegion pressed, float scale) {
        super();
        this.normalTex = normal;
        this.size = new Vector2(normal.getRegionWidth()*scale, normal.getRegionHeight()*scale);
        this.pressedTex = pressed;
    }

    public Button(TextureRegion normal, TextureRegion pressed) {
        this(normal, pressed, 1.0f);
    }

    public void update(Physics physics) {
        if (this.isClicked()) {
            onClick();
        }
    }

    public void render(Renderer renderer) {
        renderer.render(this.isHovered() ? this.pressedTex : this.normalTex, this.pos.x, this.pos.y, this.size.x, this.size.y);
    }

    public void onClick() {

    }
}
