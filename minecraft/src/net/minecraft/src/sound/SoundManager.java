package net.minecraft.src.sound;

import java.io.File;
import java.util.Random;

import net.minecraft.src.GameSettings;
import net.minecraft.src.MathHelper;
import net.minecraft.src.entity.EntityLiving;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
	
	private SoundSystem sndSystem;
	private SoundPool soundPoolSounds = new SoundPool();
	private SoundPool soundPoolMusic = new SoundPool();
	private int playedSoundsCount = 0;
	private GameSettings options;
	private boolean loaded = false;
	private Random rand = new Random();
	private int ticksBeforeMusic = this.rand.nextInt(12000);

	public void loadSoundSettings(GameSettings var1) {
		
		this.options = var1;
		if(!this.loaded && (var1.b || var1.a)) {
			this.tryToSetLibraryAndCodecs();
		}
	}

	private void tryToSetLibraryAndCodecs() {
		try {
			boolean var1 = this.options.b;
			boolean var2 = this.options.a;
			this.options.b = false;
			this.options.a = false;
			this.options.saveOptions();
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			this.sndSystem = new SoundSystem();
			this.options.b = var1;
			this.options.a = var2;
			this.options.saveOptions();
		} catch (Throwable var3) {
			System.err.println("error linking with the LibraryJavaSound plug-in");
		}

		this.loaded = true;
	}

	public void onSoundOptionsChanged() {
		if(!this.loaded && (this.options.b || this.options.a)) {
			this.tryToSetLibraryAndCodecs();
		}

		if(!this.options.a) {
			this.sndSystem.stop("BgMusic");
		}

	}

	public void closeMinecraft() {
		if(this.loaded) {
			this.sndSystem.cleanup();
		}

	}

	public void addSound(String resName, File file) {
		this.soundPoolSounds.addSound(resName, file);
	}

	public void addMusic(String resName, File file) {
		this.soundPoolMusic.addSound(resName, file);
	}

	public void setListener(EntityLiving var1, float var2) {
		if(this.loaded && this.options.b) {
			if(var1 != null) {
				float var3 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var2;
				float var4 = var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var2;
				double var5 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var2;
				double var7 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var2;
				double var9 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var2;
				float var11 = MathHelper.cos(-var4 * ((float)Math.PI / 180.0F) - (float)Math.PI);
				float var12 = MathHelper.sin(-var4 * ((float)Math.PI / 180.0F) - (float)Math.PI);
				float var13 = MathHelper.cos(-var3 * ((float)Math.PI / 180.0F));
				float var14 = MathHelper.sin(-var3 * ((float)Math.PI / 180.0F));
				float var15 = -var12 * var13;
				float var17 = -var11 * var13;
				float var18 = -var12 * var14;
				float var20 = -var11 * var14;
				this.sndSystem.setListenerPosition((float)var5, (float)var7, (float)var9);
				this.sndSystem.setListenerOrientation(var15, var14, var17, var18, var13, var20);
			}
		}
	}
	
	public float getAttenuation(float volume) {
		
		float attenuation = 16.0F;
		
		if (volume > 1.0F)
			attenuation *= volume;
		
		return attenuation;
	}
	
	public float getAttenuationSq(float volume) {
		
		float attenuation = getAttenuation(volume);
		
		return attenuation * attenuation; 
	}
	
	private String play(SoundPoolEntry soundEntry, float x, float y, float z, float volume, float pitch) {
		
		// set up and play sound
		this.playedSoundsCount = (this.playedSoundsCount + 1) % 256;
		String sourceID = "sound_" + this.playedSoundsCount;

		this.sndSystem.newSource(volume > 1.0F, sourceID, soundEntry.soundUrl, soundEntry.soundName, false, x, y, z, 2, getAttenuation(volume));
		this.sndSystem.setPitch(sourceID, pitch);
		
		if (volume > 1.0F)
			volume = 1.0F;

		this.sndSystem.setVolume(sourceID, volume);
		this.sndSystem.play(sourceID);
		
		return sourceID;
	}
	
	public void stopAudioSource(String sourceID) {
		// TODO implement
		System.err.println("SoundManager.java > stopAudioSource() not implemented");
	}

	/**
	 * @param sound
	 * @param x
	 * @param y
	 * @param z
	 * @param volume
	 * @param pitch
	 * @return sourceID of audio source, or null if failed to play
	 */
	public String playSound(String sound, float x, float y, float z, float volume, float pitch) {
		
		if (!this.loaded || !this.options.b || volume <= 0.0F)
			return null;
			
		SoundPoolEntry soundEntry = this.soundPoolSounds.getRandomSoundFromSoundPool(sound);
		
		if (soundEntry == null)
			return null;
		
		return play(soundEntry, x, y, z, volume, pitch);
	}
	
	/**
	 * 
	 * @param music
	 * @param x
	 * @param y
	 * @param z
	 * @return sourceID of audio source, or null if failed to play
	 */
	public String playMusic(String music, float x, float y, float z) {
		
		if (!this.loaded || !this.options.b)
			return null;
		
		SoundPoolEntry soundEntry = this.soundPoolMusic.getRandomSoundFromSoundPool(music);
		
		if (soundEntry == null)
			return null;
		
		return play(soundEntry, x, y, z, 1f, 1f);
	}

	public void playSoundFX(String var1, float var2, float var3) {
		
		if(this.loaded && this.options.b) {
			SoundPoolEntry var4 = this.soundPoolSounds.getRandomSoundFromSoundPool(var1);
			if(var4 != null) {
				this.playedSoundsCount = (this.playedSoundsCount + 1) % 256;
				String var5 = "sound_" + this.playedSoundsCount;
				this.sndSystem.newSource(false, var5, var4.soundUrl, var4.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
				if(var2 > 1.0F) {
					var2 = 1.0F;
				}

				var2 *= 0.25F;
				this.sndSystem.setPitch(var5, var3);
				this.sndSystem.setVolume(var5, var2);
				this.sndSystem.play(var5);
			}

		}
	}
}
