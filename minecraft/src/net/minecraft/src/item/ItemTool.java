package net.minecraft.src.item;

import net.minecraft.src.block.AllBlocks;
import net.minecraft.src.block.Block;
import net.minecraft.src.entity.Entity;
import net.minecraft.src.entity.EntityLiving;

public class ItemTool extends Item {
	
	private float efficiencyOnProperMaterial = 4.0F;
	private int damageVsEntity;
	
	public final ToolLevel level;
	public final ToolType type;
	
	private AllBlocks[] pickaxe = new AllBlocks[] { AllBlocks.cobblestone, AllBlocks.stairDouble, AllBlocks.stairSingle, AllBlocks.stone, AllBlocks.cobblestoneMossy, AllBlocks.oreIron, AllBlocks.blockIron, AllBlocks.oreCoal, AllBlocks.oreMithril, AllBlocks.blockMithril, AllBlocks.ice };
	private AllBlocks[] axe = new AllBlocks[] { AllBlocks.planks, AllBlocks.bookshelf, AllBlocks.wood, AllBlocks.chest };
	private AllBlocks[] shovel = new AllBlocks[] { AllBlocks.grass, AllBlocks.dirt, AllBlocks.sand, AllBlocks.gravel, AllBlocks.snow };

	public ItemTool(int index, ToolLevel level, ToolType type) {
		super(index);
		
		this.level = level;
		this.type = type;
		
		this.maxStackSize = 1;
		
		// calculate durability
		this.maxDamage = 32 << level.ordinal();
		
		if(level.ordinal() == 3)
			this.maxDamage *= 4;

		// calculate mining efficiency
		this.efficiencyOnProperMaterial = (float) ((level.ordinal() + 1) * 2);
		
		// calculate damage
		if (type == ToolType.SWORD) {
			
			this.damageVsEntity = level.ordinal() * 2 + 4;
		} else {
			this.damageVsEntity = level.ordinal();
		}
	}

	public float getStrVsBlock(Block block) {
		
		AllBlocks[] blocksEffectiveAgainst = null;
		
		switch (type) {
			case PICKAXE: blocksEffectiveAgainst = pickaxe; break;
			case AXE:     blocksEffectiveAgainst = axe;     break;
			case SHOVEL:  blocksEffectiveAgainst = shovel;  break;
			default: return 1.0F;
		}
		
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			
			if (blocksEffectiveAgainst[i].block == block) {
				return this.efficiencyOnProperMaterial;
			}
		}

		return 1.0F;
	}

	public void hitEntity(ItemStack itemStack, EntityLiving hitEntity) {
		itemStack.damageItem(1);
	}

	public void onBlockDestroyed(ItemStack itemStack, int var2, int var3, int var4, int var5) {
		itemStack.damageItem(1);
	}

	public int getDamageVsEntity(Entity var1) {
		return this.damageVsEntity;
	}
}
