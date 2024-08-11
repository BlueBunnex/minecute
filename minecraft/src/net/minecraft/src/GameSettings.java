package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class GameSettings {
	
	private static final String[] RENDER_DISTANCES = new String[]{"Far", "Normal", "Short", "Tiny"};
	private static final String[] DIFFICULTY_LEVELS = new String[]{"Peaceful", "Easy", "Normal", "Hard"};
	public boolean a = true;
	public boolean b = true;
	public boolean invertMouse = false;
	public boolean showDebugMenu = false;
	public int renderDistance = 0;
	public boolean viewBobbing = true;
	public boolean anaglyph = false;
	public boolean limitFramerate = false;
	public boolean fancyGraphics = true;
	public KeyBinding keyBindForward = new KeyBinding("Forward", 17);
	public KeyBinding keyBindLeft = new KeyBinding("Left", 30);
	public KeyBinding keyBindBack = new KeyBinding("Back", 31);
	public KeyBinding keyBindRight = new KeyBinding("Right", 32);
	public KeyBinding keyBindJump = new KeyBinding("Jump", 57);
	public KeyBinding keyBindInventory = new KeyBinding("Inventory", 18);
	public KeyBinding keyBindDrop = new KeyBinding("Drop", 16);
	public KeyBinding keyBindChat = new KeyBinding("Chat", 20);
	public KeyBinding[] keyBindings = new KeyBinding[]{this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindDrop, this.keyBindInventory, this.keyBindChat};
	protected Minecraft mc;
	private File optionsFile;
	public int numberOfOptions = 10;
	public int difficulty = 2;
	public boolean thirdPersonView = false;

	public GameSettings(Minecraft mc, File optionsFile) {
		this.mc = mc;
		this.optionsFile = new File(optionsFile, "options.txt");
		this.loadOptions();
	}

	public String getKeyBindingDescription(int var1) {
		return this.keyBindings[var1].keyDescription + ": " + Keyboard.getKeyName(this.keyBindings[var1].keyCode);
	}

	public void setKeyBinding(int var1, int var2) {
		this.keyBindings[var1].keyCode = var2;
		this.saveOptions();
	}

	public void setOptionValue(int optionIndex) {
		
		switch (optionIndex) {
			
			case 0:
				this.a = !this.a;
				this.mc.sndManager.onSoundOptionsChanged();
				break;
				
			case 1:
				this.b = !this.b;
				this.mc.sndManager.onSoundOptionsChanged();
				break;
				
			case 2:
				this.invertMouse = !this.invertMouse;
				break;
				
			case 3:
				this.showDebugMenu = !this.showDebugMenu;
				break;
				
			case 4:
				this.renderDistance++;
				this.renderDistance %= 4;
				break;
				
			case 5:
				this.viewBobbing = !this.viewBobbing;
				break;
				
			case 6:
				this.anaglyph = !this.anaglyph;
				this.mc.renderEngine.refreshTextures();
				break;
				
			case 7:
				this.limitFramerate = !this.limitFramerate;
				break;
				
			case 8:
				this.difficulty++;
				this.difficulty %= 4;
				break;
				
			case 9:
				this.fancyGraphics = !this.fancyGraphics;
				this.mc.renderGlobal.loadRenderers();
				break;
				
		}

		this.saveOptions();
	}

	public String getOptionDisplayString(int var1) {
		return var1 == 0 ? "Music: " + (this.a ? "ON" : "OFF") : (var1 == 1 ? "Sound: " + (this.b ? "ON" : "OFF") : (var1 == 2 ? "Invert mouse: " + (this.invertMouse ? "ON" : "OFF") : (var1 == 3 ? "Show debug: " + (this.showDebugMenu ? "ON" : "OFF") : (var1 == 4 ? "Render distance: " + RENDER_DISTANCES[this.renderDistance] : (var1 == 5 ? "View bobbing: " + (this.viewBobbing ? "ON" : "OFF") : (var1 == 6 ? "3d anaglyph: " + (this.anaglyph ? "ON" : "OFF") : (var1 == 7 ? "Limit framerate: " + (this.limitFramerate ? "ON" : "OFF") : (var1 == 8 ? "Difficulty: " + DIFFICULTY_LEVELS[this.difficulty] : (var1 == 9 ? "Graphics: " + (this.fancyGraphics ? "FANCY" : "FAST") : "")))))))));
	}

	public void loadOptions() {
		try {
			if(!this.optionsFile.exists()) {
				return;
			}

			BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
			String var2 = "";

			while(true) {
				var2 = var1.readLine();
				if(var2 == null) {
					var1.close();
					break;
				}

				String[] var3 = var2.split(":");
				if(var3[0].equals("music")) {
					this.a = var3[1].equals("true");
				}

				if(var3[0].equals("sound")) {
					this.b = var3[1].equals("true");
				}

				if(var3[0].equals("invertYMouse")) {
					this.invertMouse = var3[1].equals("true");
				}

				if(var3[0].equals("showFrameRate")) {
					this.showDebugMenu = var3[1].equals("true");
				}

				if(var3[0].equals("viewDistance")) {
					this.renderDistance = Integer.parseInt(var3[1]);
				}

				if(var3[0].equals("bobView")) {
					this.viewBobbing = var3[1].equals("true");
				}

				if(var3[0].equals("anaglyph3d")) {
					this.anaglyph = var3[1].equals("true");
				}

				if(var3[0].equals("limitFramerate")) {
					this.limitFramerate = var3[1].equals("true");
				}

				if(var3[0].equals("difficulty")) {
					this.difficulty = Integer.parseInt(var3[1]);
				}

				if(var3[0].equals("fancyGraphics")) {
					this.fancyGraphics = var3[1].equals("true");
				}

				for(int var4 = 0; var4 < this.keyBindings.length; ++var4) {
					if(var3[0].equals("key_" + this.keyBindings[var4].keyDescription)) {
						this.keyBindings[var4].keyCode = Integer.parseInt(var3[1]);
					}
				}
			}
		} catch (Exception var5) {
			System.out.println("Failed to load options");
			var5.printStackTrace();
		}

	}

	public void saveOptions() {
		try {
			PrintWriter var1 = new PrintWriter(new FileWriter(this.optionsFile));
			var1.println("music:" + this.a);
			var1.println("sound:" + this.b);
			var1.println("invertYMouse:" + this.invertMouse);
			var1.println("showFrameRate:" + this.showDebugMenu);
			var1.println("viewDistance:" + this.renderDistance);
			var1.println("bobView:" + this.viewBobbing);
			var1.println("anaglyph3d:" + this.anaglyph);
			var1.println("limitFramerate:" + this.limitFramerate);
			var1.println("difficulty:" + this.difficulty);
			var1.println("fancyGraphics:" + this.fancyGraphics);

			for(int var2 = 0; var2 < this.keyBindings.length; ++var2) {
				var1.println("key_" + this.keyBindings[var2].keyDescription + ":" + this.keyBindings[var2].keyCode);
			}

			var1.close();
		} catch (Exception var3) {
			System.out.println("Failed to save options");
			var3.printStackTrace();
		}

	}
}
