package net.minecraft.src;

import net.minecraft.src.block.Block;

public class PlayerControllerSP extends PlayerController {
	private int curBlockX = -1;
	private int curBlockY = -1;
	private int curBlockZ = -1;
	private float curBlockDamage = 0.0F;
	private float prevBlockDamage = 0.0F;
	private float blockDestroySoundCounter = 0.0F;
	private int blockHitWait = 0;
	private SpawnerAnimals monsterSpawner = new SpawnerMonsters(this, 200, EntityMob.class, new Class[]{EntityZombie.class, EntitySkeleton.class, EntityCreeper.class, EntitySpider.class});
	private SpawnerAnimals animalSpawner = new SpawnerAnimals(20, EntityAnimal.class, new Class[]{EntitySheep.class, EntityPig.class});

	public PlayerControllerSP(Minecraft var1) {
		super(var1);
	}

	public void flipPlayer(EntityPlayer var1) {
		var1.rotationYaw = -180.0F;
	}

	public void initController() {
	}

	public boolean sendBlockRemoved(int var1, int var2, int var3) {
		int var4 = this.mc.theWorld.getBlockId(var1, var2, var3);
		int var5 = this.mc.theWorld.getBlockMetadata(var1, var2, var3);
		boolean var6 = super.sendBlockRemoved(var1, var2, var3);
		ItemStack var7 = this.mc.thePlayer.getCurrentEquippedItem();
		if(var7 != null) {
			var7.onDestroyBlock(var4, var1, var2, var3);
			if(var7.stackSize == 0) {
				var7.onItemDestroyedByUse(this.mc.thePlayer);
				this.mc.thePlayer.destroyCurrentEquippedItem();
			}
		}

		if(var6 && this.mc.thePlayer.canHarvestBlock(Block.blocksList[var4])) {
			Block.blocksList[var4].dropBlockAsItem(this.mc.theWorld, var1, var2, var3, var5);
		}

		return var6;
	}

	public void hitBlock(int x, int y, int z) {
		
		int blockID = this.mc.theWorld.getBlockId(x, y, z);

		if (blockID > 0 && Block.blocksList[blockID].blockStrength(this.mc.thePlayer) >= 1.0F)
			this.sendBlockRemoved(x, y, z);

	}
	
	/**
	 * Tries to interact with the block. If block is air, or just can't interact, returns false.
	 */
	public boolean interactBlock(int x, int y, int z) {
		
		int blockID = this.mc.theWorld.getBlockId(x, y, z);
		
		if (blockID > 0)
			return Block.blocksList[blockID].onBlockInteract(this.mc.theWorld, x, y, z, this.mc.thePlayer);
		
		return false;
	}

	public void resetBlockRemoving() {
		this.curBlockDamage = 0.0F;
		this.blockHitWait = 0;
	}

	public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
		if(this.blockHitWait > 0) {
			--this.blockHitWait;
		} else {
			super.sendBlockRemoving(var1, var2, var3, var4);
			if(var1 == this.curBlockX && var2 == this.curBlockY && var3 == this.curBlockZ) {
				int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
				if(var5 == 0) {
					return;
				}

				Block var6 = Block.blocksList[var5];
				this.curBlockDamage += var6.blockStrength(this.mc.thePlayer);
				if(this.blockDestroySoundCounter % 4.0F == 0.0F && var6 != null) {
					this.mc.sndManager.playSound(var6.stepSound.getStepSound(), (float)var1 + 0.5F, (float)var2 + 0.5F, (float)var3 + 0.5F, (var6.stepSound.getVolume() + 1.0F) / 8.0F, var6.stepSound.getPitch() * 0.5F);
				}

				++this.blockDestroySoundCounter;
				if(this.curBlockDamage >= 1.0F) {
					this.sendBlockRemoved(var1, var2, var3);
					this.curBlockDamage = 0.0F;
					this.prevBlockDamage = 0.0F;
					this.blockDestroySoundCounter = 0.0F;
					this.blockHitWait = 5;
				}
			} else {
				this.curBlockDamage = 0.0F;
				this.prevBlockDamage = 0.0F;
				this.blockDestroySoundCounter = 0.0F;
				this.curBlockX = var1;
				this.curBlockY = var2;
				this.curBlockZ = var3;
			}

		}
	}

	public void setPartialTime(float var1) {
		if(this.curBlockDamage <= 0.0F) {
			this.mc.ingameGUI.damageGuiPartialTime = 0.0F;
			this.mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float var2 = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * var1;
			this.mc.ingameGUI.damageGuiPartialTime = var2;
			this.mc.renderGlobal.damagePartialTime = var2;
		}

	}

	public float getBlockReachDistance() {
		return 4.0F;
	}

	public void onWorldChange(World var1) {
		super.onWorldChange(var1);
	}

	public void onUpdate() {
		this.prevBlockDamage = this.curBlockDamage;
		this.monsterSpawner.onUpdate(this.mc.theWorld);
		this.animalSpawner.onUpdate(this.mc.theWorld);
	}
}
