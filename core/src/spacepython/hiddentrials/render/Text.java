package spacepython.hiddentrials.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class Text extends Renderable {
    public static final BitmapFont font = new BitmapFont();
    public String text;
    public Vector2 pos;
    
    public Text(Vector2 pos, String text) {
        super();
        this.pos = pos;
        this.text = text;
    }

    public void render(Renderer renderer) {
        Text.font.draw(renderer.batch, this.text, this.pos.x, this.pos.y);
    }
}