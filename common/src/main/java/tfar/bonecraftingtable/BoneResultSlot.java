package tfar.bonecraftingtable;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;

public class BoneResultSlot extends ResultSlot {
    public BoneResultSlot(Player player, CraftingContainer craftSlots, Container container, int slot, int xPosition, int yPosition) {
        super(player, craftSlots, container, slot, xPosition, yPosition);
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        CraftingInput.Positioned craftinginput$positioned = this.craftSlots.asPositionedCraftInput();
        CraftingInput craftinginput = craftinginput$positioned.input();
        int i = craftinginput$positioned.left();
        int j = craftinginput$positioned.top();
        NonNullList<ItemStack> nonnulllist = player.level().getRecipeManager().getRemainingItemsFor(Init.BONE_CRAFTING, craftinginput, player.level());

        for (int k = 0; k < craftinginput.height(); k++) {
            for (int l = 0; l < craftinginput.width(); l++) {
                int i1 = l + i + (k + j) * this.craftSlots.getWidth();
                ItemStack itemstack = this.craftSlots.getItem(i1);
                ItemStack itemstack1 = nonnulllist.get(l + k * craftinginput.width());
                if (!itemstack.isEmpty()) {
                    this.craftSlots.removeItem(i1, 1);
                    itemstack = this.craftSlots.getItem(i1);
                }

                if (!itemstack1.isEmpty()) {
                    if (itemstack.isEmpty()) {
                        this.craftSlots.setItem(i1, itemstack1);
                    } else if (ItemStack.isSameItemSameComponents(itemstack, itemstack1)) {
                        itemstack1.grow(itemstack.getCount());
                        this.craftSlots.setItem(i1, itemstack1);
                    } else if (!this.player.getInventory().add(itemstack1)) {
                        this.player.drop(itemstack1, false);
                    }
                }
            }
        }
    }
}
