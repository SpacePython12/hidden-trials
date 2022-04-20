package spacepython.hiddentrials.render;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

import spacepython.hiddentrials.physics.Physics;

public class Model {
    private TextureAtlas texture;
    private HashMap<String, Animation<TextureRegion>> animations;
    private HashMap<String, Integer> animationDefaults;
    private Animation<TextureRegion> currentAnimation;
    private String currentName;
    private TextureRegion currentTexture;
    private float animationTime = 0f;
    private boolean locked = false;

    public Model(FileHandle manifest) {
        this.animations = new HashMap<>();
        this.animationDefaults = new HashMap<>();
        String rawManifestData = manifest.readString();
        JsonReader reader = new JsonReader();
        JsonValue manifestData = reader.parse(rawManifestData);
        this.texture = new TextureAtlas(Gdx.files.internal(manifest.parent().path() + "/" + manifestData.getString("atlas", manifest.nameWithoutExtension() + ".atlas")));
        for (JsonValue pair: manifestData.get("animations")) {
            this.animations.put(pair.name, new Animation<TextureRegion>(1.0f / ((pair.getFloat("fps") / Physics.ups) * (2.0f * Physics.ups)), this.texture.findRegions(pair.get("framesource").getString("section")), Animation.PlayMode.valueOf(pair.getString("playType"))));
            this.animationDefaults.put(pair.name, pair.getInt("defaultFrame"));
        }
        this.setAnimation(manifestData.getString("defaultAnimation"));
        this.currentTexture = this.currentAnimation.getKeyFrames()[this.animationDefaults.get(manifestData.getString("defaultAnimation"))];
    }

    public void setAnimation(String name) {
        this.currentName = name;
        this.currentAnimation = this.animations.get(name);
    }

    public Animation<TextureRegion> getAnimation() {
        return this.currentAnimation;
    }

    public void setLocked(boolean state) {
        this.locked = state;
        if (state) {
            this.animationTime = 0f;
            this.currentTexture = this.currentAnimation.getKeyFrames()[this.animationDefaults.get(this.currentName)];
        }
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void keyFrame() {
        if (!locked) {
            this.animationTime += Gdx.graphics.getDeltaTime();
            this.currentTexture = this.currentAnimation.getKeyFrame(this.animationTime);
        }
    }

    public TextureRegion getTexture() {
        return this.currentTexture;
    }
}