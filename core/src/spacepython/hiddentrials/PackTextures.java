package spacepython.hiddentrials;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PackTextures {
	public static void main (String[] args) throws Exception {
		TexturePacker.process("assets", "assets", "compiled");
	}
}