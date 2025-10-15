package tfar.bonecraftingtable;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;

public class BoneCraftingContainer extends TransientCraftingContainer {

    public BonePattern pattern = BonePattern.ZERO;

    public BoneCraftingContainer(AbstractContainerMenu menu, int width, int height) {
        super(menu, width, height);
    }

    public BoneCraftingContainer(AbstractContainerMenu menu, int width, int height, NonNullList<ItemStack> items) {
        super(menu, width, height, items);
    }

    public void updatePattern(BonePattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return pattern.canPlace(slot);
    }
}
