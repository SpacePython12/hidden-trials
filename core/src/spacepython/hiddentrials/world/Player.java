package spacepython.hiddentrials.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import spacepython.hiddentrials.GameMain;
import spacepython.hiddentrials.physics.Hitbox;
import spacepython.hiddentrials.physics.Physics;
import spacepython.hiddentrials.render.Model;
import spacepython.hiddentrials.render.Renderer;

public class Player extends Being {
    public static final float stepsPerUpdate = 4F / Physics.ups; // Yes, I DO need this value.
    public static final float walkFrameRate = 1F / (stepsPerUpdate * (2F * Physics.ups));
    public static final float walkSpeed = 3F;
    public static final float brakingPower = walkSpeed * stepsPerUpdate; // Yup.
    public Vector2 size = new Vector2(16*3, 28*3);
    public Model model;
    public boolean isWalking = false;
    
    public Player(Vector2 pos, Vector2 velocity) {
        super();
        this.model = new Model(Gdx.files.internal("sprite/player/player.json"));
        this.model.setLocked(true);
        this.hitbox = new Hitbox(pos, size, velocity);
        this.pos = this.hitbox.pos;
        this.velocity = this.hitbox.velocity;
    }

    public void update(Physics physics) {
        boolean south = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean north = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean west = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean east = Gdx.input.isKeyPressed(Input.Keys.D);
        if (this.isWalking) {
            // Completely stopped.
            if ((!south && !north) && (!west && !east)) {
                this.isWalking = false;
                this.model.setLocked(true);
            }
        }
        if ((south ^ north) || (west ^ east)) {
            this.isWalking = true;
            this.model.setLocked(false);
        }
        if (south && !north) {
            this.model.setAnimation("walkSouth");
            this.velocity.y = -walkSpeed;
        } else if (!south && north) {
            this.model.setAnimation("walkNorth");
            this.velocity.y = walkSpeed;
        } else if (!south && !north) {
            this.velocity.y -= brakingPower * this.velocity.y;
            if (this.velocity.y < 0.01 && this.velocity.y > -0.01) {
                this.velocity.y = 0;
            }
        }
        if (west && !east) {
            this.model.setAnimation("walkWest");
            this.velocity.x = -walkSpeed;
        } else if (!west && east) {
            this.model.setAnimation("walkEast");
            this.velocity.x = walkSpeed;
        } else if (!west && !east) {
            this.velocity.x -= brakingPower * this.velocity.x;
            if (this.velocity.x < 0.01 && this.velocity.x > -0.01) {
                this.velocity.x = 0;
            }
        }
        for (Hitbox hb: Hitbox.instances) {
            if (hb != this.hitbox && !hb.noClip) {
                this.hitbox.offset(Hitbox.getOverlap(this.hitbox, hb));
            }
        }
        this.hitbox.update();
        GameMain.getInstance().renderer.camera.position.x = this.hitbox.getCenterX();
        GameMain.getInstance().renderer.camera.position.y = this.hitbox.getCenterY();
    }

    public void render(Renderer renderer) {
        this.model.keyFrame();
        renderer.render(this.model.getTexture(), this.hitbox.getLeft(), this.hitbox.getBottom(), this.size.x, this.size.y);
    }
}