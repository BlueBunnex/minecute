package net.minecraft.src.block;

// TODO remove this class, but add a transparency flag elsewhere in the block class
public class Material {
	
	public static final Material air = new MaterialTransparent();
	public static final Material grass = new Material();
	public static final Material wood = new Material();
	public static final Material rock = new Material();
	public static final Material iron = new Material();
	public static final Material leaves = new Material();
	public static final Material plants = new Material();
	public static final Material sponge = new Material();
	public static final Material cloth = new Material();
	public static final Material fire = new MaterialTransparent();
	public static final Material sand = new Material();
	public static final Material glass = new Material();
	public static final Material tnt = new Material();
	public static final Material unused = new Material();
	public static final Material ice = new Material();
	public static final Material snow = new Material();

	public boolean getIsLiquid() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}

	public boolean getCanBlockGrass() {
		return true;
	}

	public boolean getIsSolid() {
		return true;
	}
}
