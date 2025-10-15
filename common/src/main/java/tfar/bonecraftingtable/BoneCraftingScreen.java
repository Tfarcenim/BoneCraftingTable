package tfar.bonecraftingtable;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BoneCraftingScreen extends AbstractContainerScreen<BoneCraftingMenu> {

    public BoneCraftingScreen(BoneCraftingMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
        this.titleLabelX = 29;
    }

    @Override
    public void render(GuiGraphics pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics matrices, int $$1, int $$2) {
        super.renderLabels(matrices, $$1, $$2);
    }

    @Override
    protected void renderBg(GuiGraphics pPoseStack, float pPartialTick, int pX, int pY) {
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pPoseStack.blit(menu.getCraftSlots().pattern.texture(), i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

}
