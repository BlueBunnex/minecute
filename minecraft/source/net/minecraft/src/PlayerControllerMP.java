package net.minecraft.src;

public class PlayerControllerMP extends PlayerController {
	private int currentBlockX = -1;
	private int currentBlockY = -1;
	private int currentBlockZ = -1;
	private float curBlockDamageMP = 0.0F;
	private float prevBlockDamageMP = 0.0F;
	private float stepSoundTickCounter = 0.0F;
	private int blockHitDelay = 0;

	public PlayerControllerMP(Minecraft var1) {
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

	public void clickBlock(int var1, int var2, int var3) {
		int var4 = this.mc.theWorld.getBlockId(var1, var2, var3);
		if(var4 > 0 && this.curBlockDamageMP == 0.0F) {
			Block.blocksList[var4].onBlockClicked(this.mc.theWorld, var1, var2, var3, this.mc.thePlayer);
		}

		if(var4 > 0 && Block.blocksList[var4].blockStrength(this.mc.thePlayer) >= 1.0F) {
			this.sendBlockRemoved(var1, var2, var3);
		}

	}

	public void resetBlockRemoving() {
		this.curBlockDamageMP = 0.0F;
		this.blockHitDelay = 0;
	}

	public void sendBlockRemoving(int var1, int var2, int var3, int var4) {
		if(this.blockHitDelay > 0) {
			--this.blockHitDelay;
		} else {
			super.sendBlockRemoving(var1, var2, var3, var4);
			if(var1 == this.currentBlockX && var2 == this.currentBlockY && var3 == this.currentBlockZ) {
				int var5 = this.mc.theWorld.getBlockId(var1, var2, var3);
				if(var5 == 0) {
					return;
				}

				Block var6 = Block.blocksList[var5];
				this.curBlockDamageMP += var6.blockStrength(this.mc.thePlayer);
				if(this.stepSoundTickCounter % 4.0F == 0.0F && var6 != null) {
					this.mc.sndManager.playSound(var6.stepSound.getStepSound(), (float)var1 + 0.5F, (float)var2 + 0.5F, (float)var3 + 0.5F, (var6.stepSound.getVolume() + 1.0F) / 8.0F, var6.stepSound.getPitch() * 0.5F);
				}

				++this.stepSoundTickCounter;
				if(this.curBlockDamageMP >= 1.0F) {
					this.sendBlockRemoved(var1, var2, var3);
					this.curBlockDamageMP = 0.0F;
					this.prevBlockDamageMP = 0.0F;
					this.stepSoundTickCounter = 0.0F;
					this.blockHitDelay = 5;
				}
			} else {
				this.curBlockDamageMP = 0.0F;
				this.prevBlockDamageMP = 0.0F;
				this.stepSoundTickCounter = 0.0F;
				this.currentBlockX = var1;
				this.currentBlockY = var2;
				this.currentBlockZ = var3;
			}

		}
	}

	public void setPartialTime(float var1) {
		if(this.curBlockDamageMP <= 0.0F) {
			this.mc.ingameGUI.damageGuiPartialTime = 0.0F;
			this.mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float var2 = this.prevBlockDamageMP + (this.curBlockDamageMP - this.prevBlockDamageMP) * var1;
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
		this.prevBlockDamageMP = this.curBlockDamageMP;
	}
}
