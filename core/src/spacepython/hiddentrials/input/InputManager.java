package spacepython.hiddentrials.input;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.*;

public class InputManager implements InputProcessor {
    private boolean[] keyStates;
    private boolean[] buttonStates;
    private ArrayList<String> sequences;
    private Controller controller;
    private float mouseX, mouseY;
    
    public InputManager() {
        Gdx.input.setInputProcessor(this);
        this.keyStates = new boolean[Input.Keys.MAX_KEYCODE];
        this.buttonStates = new boolean[8];
        this.sequences = new ArrayList<>();
        this.sequences.add("");
    }

    public boolean keyDown(int keycode) {
        this.keyStates[keycode] = true;
        return true;
    }

    public boolean keyUp(int keycode) {
        this.keyStates[keycode] = false;
        return true;
    }

    public boolean keyTyped(char character) {
        String line = this.sequences.get(this.sequences.size()-1);
        line += character;
        this.sequences.set(this.sequences.size()-1, line);
        if (character == '\n') {
            this.sequences.add("");
        }
        return true;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.buttonStates[button] = true;
        this.mouseX = screenX;
        this.mouseY = screenY;
        return true;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.buttonStates[button] = false;
        this.mouseX = screenX;
        this.mouseY = screenY;
        return true;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.mouseX = screenX;
        this.mouseY = screenY;
        return true;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        this.mouseX = screenX;
        this.mouseY = screenY;
        return true;
    }

    public boolean scrolled(float amountX, float amountY) {
        System.out.println("Mouse scrolled!");
        return true;
    }
}
