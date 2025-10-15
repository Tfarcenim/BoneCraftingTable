package tfar.bonecraftingtable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BoneCraftingTableBlock extends CraftingTableBlock {
    public BoneCraftingTableBlock(Properties $$0) {
        super($$0);
    }

    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
        return new SimpleMenuProvider(($$2x, $$3, $$4) -> {
            return new BoneCraftingMenu($$2x, $$3, ContainerLevelAccess.create($$1, $$2));
        }, CONTAINER_TITLE);
    }


}
