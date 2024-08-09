package net.minecraft.src.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumMobType;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.StepSound;
import net.minecraft.src.StepSoundGlass;
import net.minecraft.src.StepSoundSand;
import net.minecraft.src.Vec3D;
import net.minecraft.src.item.Item;
import net.minecraft.src.item.ItemBlock;
import net.minecraft.src.item.ItemStack;
import net.minecraft.src.world.World;

public class Block {
	
	public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
	public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
	public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
	public static final StepSound soundGlassFootstep = new StepSoundGlass("stone", 1.0F, 1.0F);
	public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
	public static final StepSound soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
	public static final Block[] blocksList = new Block[256];
	public static final boolean[] tickOnLoad = new boolean[256];
	public static final boolean[] opaqueCubeLookup = new boolean[256];
	public static final int[] lightOpacity = new int[256];
	public static final boolean[] canBlockGrass = new boolean[256];
	public static final int[] lightValue = new int[256];
	
	public static final Block stone = (new BlockBreakToCobble(1, 1)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final BlockGrass grass = (BlockGrass)(new BlockGrass(2)).setHardness(0.6F).setStepSound(soundGrassFootstep);
	public static final Block dirt = (new BlockDirt(3, 2)).setHardness(0.5F).setStepSound(soundGravelFootstep);
	public static final Block cobblestone = new BlockCobble(4, 16).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block planks = (new Block(5, 4, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep);
	public static final Block sapling = (new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block bedrock = (new Block(7, 17, Material.rock)).setHardness(-1.0F).setResistance(6000000.0F).setStepSound(soundStoneFootstep);
	public static final Block waterMoving = (new BlockFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3);
	public static final Block waterStill = (new BlockStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3);
	public static final Block lavaMoving = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255);
	public static final Block lavaStill = (new BlockStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setLightOpacity(255);
	public static final Block sand = (new BlockSand(12, 18)).setHardness(0.5F).setStepSound(soundSandFootstep);
	public static final Block gravel = (new BlockGravel(13, 19)).setHardness(0.6F).setStepSound(soundGravelFootstep);
	
	public static final Block oreIron = (new BlockOre(15, 33)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block oreCoal = (new BlockOre(16, 34)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block wood = (new BlockLog(17)).setHardness(2.0F).setStepSound(soundWoodFootstep);
	public static final BlockLeaves leaves = (BlockLeaves)(new BlockLeaves(18, 52)).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep);
	public static final Block sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep);
	public static final Block glass = (new BlockGlass(20, 49, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep);
	public static final Block clothRed = null;
	public static final Block clothOrange = null;
	public static final Block clothYellow = null;
	public static final Block clothChartreuse = null;
	public static final Block clothGreen = null;
	public static final Block clothSpringGreen = null;
	public static final Block clothCyan = null;
	public static final Block clothCapri = null;
	public static final Block clothUltramarine = null;
	public static final Block clothViolet = null;
	public static final Block clothPurple = null;
	public static final Block clothMagenta = null;
	public static final Block clothRose = null;
	public static final Block clothDarkGray = null;
	public static final Block cloth = (new Block(35, 64, Material.cloth)).setHardness(0.8F).setStepSound(soundClothFootstep);
	public static final Block clothWhite = null;
	public static final BlockFlower plantYellow = (BlockFlower)(new BlockFlower(37, 13)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final BlockFlower plantRed = (BlockFlower)(new BlockFlower(38, 12)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final BlockFlower mushroomBrown = (BlockFlower)(new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(2.0F / 16.0F);
	public static final BlockFlower mushroomRed = (BlockFlower)(new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	
	public static final Block blockIron = (new BlockOreBlock(42, 38)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep);
	public static final Block stairDouble = (new BlockStep(43, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block stairSingle = (new BlockStep(44, false)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block brick = (new Block(45, 7, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block tnt = (new BlockTNT(46, 8)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block bookshelf = (new BlockBookshelf(47, 35)).setHardness(1.5F).setStepSound(soundWoodFootstep);
	public static final Block cobblestoneMossy = (new Block(48, 36, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
	public static final Block obsidian = (new Block(49, 37, Material.rock)).setHardness(10.0F).setResistance(20.0F).setStepSound(soundStoneFootstep);
	public static final Block torch = (new BlockTorch(50, 80)).setHardness(0.0F).setLightValue(15.0F / 16.0F).setStepSound(soundWoodFootstep);
	public static final BlockFire fire = (BlockFire)((BlockFire)(new BlockFire(51, 31)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep));
	public static final Block mobSpawner = (new BlockMobSpawner(52, 65)).setHardness(5.0F).setStepSound(soundMetalFootstep);
	public static final Block stairCompactWood = new BlockStairs(53, planks);
	public static final Block chest = (new BlockChest(54)).setHardness(2.5F).setStepSound(soundWoodFootstep);
	
	public static final Block oreMithril = (new BlockOre(56, 50)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep);
	public static final Block blockMithril = (new BlockOreBlock(57, 40)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep);
	public static final Block workbench = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep);
	public static final Block crops = (new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(soundGrassFootstep);
	public static final Block tilledField = (new BlockFarmland(60)).setHardness(0.6F).setStepSound(soundGravelFootstep);
	public static final Block stoneOvenIdle = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep);
	public static final Block stoneOvenActive = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(14.0F / 16.0F);
	public static final Block signStanding = (new BlockSign(63, TileEntitySign.class, Item.sign.shiftedIndex, true)).setHardness(1.0F).setStepSound(soundWoodFootstep);
	public static final Block doorWood = (new BlockDoor(64, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep);
	public static final Block ladder = (new BlockLadder(65, 83)).setHardness(0.4F).setStepSound(soundWoodFootstep);
	public static final Block minecartTrack = (new BlockMinecartTrack(66, 128)).setHardness(0.7F).setStepSound(soundMetalFootstep);
	public static final Block stairCompactStone = new BlockStairs(67, cobblestone);
	public static final Block signWall = (new BlockSign(68, TileEntitySign.class, Item.sign.shiftedIndex, false)).setHardness(1.0F).setStepSound(soundWoodFootstep);
	
	public static final Block snow = (new BlockSnow(78, 66)).setHardness(0.1F).setStepSound(soundClothFootstep);
	public static final Block ice = (new BlockIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep);
	public static final Block blockSnow = (new BlockSnowBlock(80, 66)).setHardness(0.2F).setStepSound(soundClothFootstep);
	
	public static final Block jukebox = new BlockJukebox(81).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep);
	
	public int blockIndexInTexture;
	public final int blockID;
	protected float hardness;
	protected float resistance;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public StepSound stepSound;
	public float blockParticleGravity;
	public final Material material;
	public float slipperiness;

	protected Block(int blockID, Material material) {
		
		this.stepSound = soundPowderFootstep;
		this.blockParticleGravity = 1.0F;
		this.slipperiness = 0.6F;
		
		if(blocksList[blockID] != null) {
			
			throw new IllegalArgumentException("Slot " + blockID + " is already occupied by " + blocksList[blockID] + " when adding " + this);
			
		} else {
			
			this.material = material;
			blocksList[blockID] = this;
			this.blockID = blockID;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[blockID] = this.isOpaqueCube();
			lightOpacity[blockID] = this.isOpaqueCube() ? 255 : 0;
			canBlockGrass[blockID] = this.getCanBlockGrass();
		}
	}

	protected Block(int blockID, int indexInTexture, Material material) {
		this(blockID, material);
		this.blockIndexInTexture = indexInTexture;
	}

	protected Block setStepSound(StepSound var1) {
		this.stepSound = var1;
		return this;
	}

	protected Block setLightOpacity(int var1) {
		lightOpacity[this.blockID] = var1;
		return this;
	}

	protected Block setLightValue(float var1) {
		lightValue[this.blockID] = (int)(15.0F * var1);
		return this;
	}

	protected Block setResistance(float var1) {
		this.resistance = var1 * 3.0F;
		return this;
	}

	private boolean getCanBlockGrass() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderType() {
		return 0;
	}

	protected Block setHardness(float var1) {
		this.hardness = var1;
		if(this.resistance < var1 * 5.0F) {
			this.resistance = var1 * 5.0F;
		}

		return this;
	}

	protected void setTickOnLoad(boolean var1) {
		tickOnLoad[this.blockID] = var1;
	}

	public void setBlockBounds(float var1, float var2, float var3, float var4, float var5, float var6) {
		this.minX = (double)var1;
		this.minY = (double)var2;
		this.minZ = (double)var3;
		this.maxX = (double)var4;
		this.maxY = (double)var5;
		this.maxZ = (double)var6;
	}

	public float getBlockBrightness(IBlockAccess var1, int var2, int var3, int var4) {
		return var1.getBrightness(var2, var3, var4);
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return var5 == 0 && this.minY > 0.0D ? true : (var5 == 1 && this.maxY < 1.0D ? true : (var5 == 2 && this.minZ > 0.0D ? true : (var5 == 3 && this.maxZ < 1.0D ? true : (var5 == 4 && this.minX > 0.0D ? true : (var5 == 5 && this.maxX < 1.0D ? true : !var1.isBlockNormalCube(var2, var3, var4))))));
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return this.getBlockTextureFromSideAndMetadata(var5, var1.getBlockMetadata(var2, var3, var4));
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.getBlockTextureFromSide(var1);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.blockIndexInTexture;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)var3 + this.maxY, (double)var4 + this.maxZ);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
		AxisAlignedBB var7 = this.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
		if(var7 != null && var5.intersectsWith(var7)) {
			var6.add(var7);
		}

	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)var3 + this.maxY, (double)var4 + this.maxZ);
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public boolean canCollideCheck(int var1, boolean var2) {
		return this.isCollidable();
	}

	public boolean isCollidable() {
		return true;
	}
	
	// TODO onBreakReplaceBlockWith(Block block, int toolLevel) (?)

	public void updateTick(World world, int x, int y, int z, Random random) {}

	public void randomDisplayTick(World world, int x, int y, int z, Random random) {}

	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int SOMETHING) {}

	public void onNeighborBlockChange(World world, int x, int y, int z, int SOMETHING) {}

	public int tickRate() {
		return 10;
	}

	public void onBlockAdded(World world, int x, int y, int z) {}

	public void onBlockRemoval(World world, int x, int y, int z) {}

	// TODO should take in tool level
	public int quantityDropped(Random var1) {
		return 1;
	}

	// TODO should take in tool level
	public int idDropped(int var1, Random var2) {
		return this.blockID;
	}

	public float blockStrength(EntityPlayer var1) {
		return this.hardness < 0.0F ? 0.0F : (!var1.canHarvestBlock(this) ? 1.0F / this.hardness / 100.0F : var1.getCurrentPlayerStrVsBlock(this) / this.hardness / 30.0F);
	}

	public void dropBlockAsItem(World var1, int var2, int var3, int var4, int var5) {
		this.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, 1.0F);
	}

	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6) {
		int var7 = this.quantityDropped(var1.rand);

		for(int var8 = 0; var8 < var7; ++var8) {
			if(var1.rand.nextFloat() <= var6) {
				int var9 = this.idDropped(var5, var1.rand);
				if(var9 > 0) {
					float var10 = 0.7F;
					double var11 = (double)(var1.rand.nextFloat() * var10) + (double)(1.0F - var10) * 0.5D;
					double var13 = (double)(var1.rand.nextFloat() * var10) + (double)(1.0F - var10) * 0.5D;
					double var15 = (double)(var1.rand.nextFloat() * var10) + (double)(1.0F - var10) * 0.5D;
					EntityItem var17 = new EntityItem(var1, (double)var2 + var11, (double)var3 + var13, (double)var4 + var15, new ItemStack(var9));
					var17.delayBeforeCanPickup = 10;
					var1.spawnEntityInWorld(var17);
				}
			}
		}

	}

	public float getExplosionResistance(Entity var1) {
		return this.resistance / 5.0F;
	}

	public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		var5 = var5.addVector((double)(-var2), (double)(-var3), (double)(-var4));
		var6 = var6.addVector((double)(-var2), (double)(-var3), (double)(-var4));
		Vec3D var7 = var5.getIntermediateWithXValue(var6, this.minX);
		Vec3D var8 = var5.getIntermediateWithXValue(var6, this.maxX);
		Vec3D var9 = var5.getIntermediateWithYValue(var6, this.minY);
		Vec3D var10 = var5.getIntermediateWithYValue(var6, this.maxY);
		Vec3D var11 = var5.getIntermediateWithZValue(var6, this.minZ);
		Vec3D var12 = var5.getIntermediateWithZValue(var6, this.maxZ);
		if(!this.isVecInsideYZBounds(var7)) {
			var7 = null;
		}

		if(!this.isVecInsideYZBounds(var8)) {
			var8 = null;
		}

		if(!this.isVecInsideXZBounds(var9)) {
			var9 = null;
		}

		if(!this.isVecInsideXZBounds(var10)) {
			var10 = null;
		}

		if(!this.isVecInsideXYBounds(var11)) {
			var11 = null;
		}

		if(!this.isVecInsideXYBounds(var12)) {
			var12 = null;
		}

		Vec3D var13 = null;
		if(var7 != null && (var13 == null || var5.distanceTo(var7) < var5.distanceTo(var13))) {
			var13 = var7;
		}

		if(var8 != null && (var13 == null || var5.distanceTo(var8) < var5.distanceTo(var13))) {
			var13 = var8;
		}

		if(var9 != null && (var13 == null || var5.distanceTo(var9) < var5.distanceTo(var13))) {
			var13 = var9;
		}

		if(var10 != null && (var13 == null || var5.distanceTo(var10) < var5.distanceTo(var13))) {
			var13 = var10;
		}

		if(var11 != null && (var13 == null || var5.distanceTo(var11) < var5.distanceTo(var13))) {
			var13 = var11;
		}

		if(var12 != null && (var13 == null || var5.distanceTo(var12) < var5.distanceTo(var13))) {
			var13 = var12;
		}

		if(var13 == null) {
			return null;
		} else {
			byte var14 = -1;
			if(var13 == var7) {
				var14 = 4;
			}

			if(var13 == var8) {
				var14 = 5;
			}

			if(var13 == var9) {
				var14 = 0;
			}

			if(var13 == var10) {
				var14 = 1;
			}

			if(var13 == var11) {
				var14 = 2;
			}

			if(var13 == var12) {
				var14 = 3;
			}

			return new MovingObjectPosition(var2, var3, var4, var14, var13.addVector((double)var2, (double)var3, (double)var4));
		}
	}

	private boolean isVecInsideYZBounds(Vec3D var1) {
		return var1 == null ? false : var1.yCoord >= this.minY && var1.yCoord <= this.maxY && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXZBounds(Vec3D var1) {
		return var1 == null ? false : var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXYBounds(Vec3D var1) {
		return var1 == null ? false : var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.yCoord >= this.minY && var1.yCoord <= this.maxY;
	}

	public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3, var4);
		return var5 == 0 || blocksList[var5].material.getIsLiquid();
	}

	public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {}

	public void onBlockPlaced(World var1, int var2, int var3, int var4, int var5) {}

	/**
	 * Returns true only if something actually happened (and should swing arm).
	 * @param var1
	 * @param var2
	 * @param var3
	 * @param var4
	 * @param var5
	 * @return
	 */
	public boolean onBlockInteract(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		return false;
	}

	public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {}

	public int colorMultiplier(IBlockAccess var1, int var2, int var3, int var4) {
		return 16777215;
	}

	public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5) {}

	public void setBlockBoundsForItemRender() {}

	static {
		
		// make item forms
		for(int var0 = 0; var0 < 256; ++var0) {
			if(blocksList[var0] != null) {
				Item.itemsList[var0] = new ItemBlock(var0 - 256);
			}
		}
	}
}
