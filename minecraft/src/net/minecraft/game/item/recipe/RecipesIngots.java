package net.minecraft.game.item.recipe;

import net.minecraft.game.item.Item;
import net.minecraft.game.item.ItemStack;
import net.minecraft.game.level.block.Block;

public final class RecipesIngots {
	private Object[][] recipeItems = new Object[][]{{Block.blockGold, Item.ingotGold}, {Block.blockSteel, Item.ingotIron}, {Block.blockDiamond, Item.diamond}};

	public final void addRecipes(CraftingManager var1) {
		for(int var2 = 0; var2 < this.recipeItems.length; ++var2) {
			Block var3 = (Block)this.recipeItems[var2][0];
			Item var4 = (Item)this.recipeItems[var2][1];
			var1.addRecipe(new ItemStack(var3), new Object[]{"###", "###", "###", Character.valueOf('#'), var4});
			var1.addRecipe(new ItemStack(var4, 9), new Object[]{"#", Character.valueOf('#'), var3});
		}

	}
}
