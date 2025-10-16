package tfar.bonecraftingtable;

import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BoneCraftingMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final BoneCraftingContainer craftSlots = new BoneCraftingContainer(this, 3, 3);
    private final ResultContainer resultSlots = new ResultContainer();
    private final SimpleContainer extra = new SimpleContainer(1) {
        @Override
        public void setChanged() {
            super.setChanged();
            updatePattern(getItem(0));
        }
    };
    private final Player player;
    public CraftingRecipe current;

    /**
     * Stores the game time of the last time the player took items from the the crafting result slot. This is used to prevent the sound from being played multiple times on the same tick.
     */
    long lastSoundTime;

    public BoneCraftingContainer getCraftSlots() {
        return craftSlots;
    }

    public void updatePattern(ItemStack stack) {
        BonePattern pattern = BonePattern.findPattern(stack);
        craftSlots.updatePattern(pattern);
        for (int i = 0; i < craftSlots.getContainerSize();i++) {
            ItemStack s = craftSlots.getItem(i);
            if (!craftSlots.canPlaceItem(i,s) && !craftSlots.getItem(i).isEmpty()) {
                quickMoveStack(player,i + 2);
                if (!s.isEmpty()) {
                    player.drop(s,true);
                    craftSlots.setItem(i,ItemStack.EMPTY);
                }
            }
        }
    }

    public BoneCraftingMenu(int id, Inventory $$1) {
        this(id, $$1, ContainerLevelAccess.NULL);
    }


    protected BoneCraftingMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(Init.MENU_TYPE, id);
        this.access = access;
        this.player = inventory.player;

        this.addSlot(new BoneResultSlot(inventory.player, this.craftSlots, this.resultSlots, 0, 129, 35){
            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                access.execute((p_40364_, p_40365_) -> {
                    long l = p_40364_.getGameTime();
                    if (lastSoundTime != l) {
                        p_40364_.playSound(null, p_40365_, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        lastSoundTime = l;
                    }
                });
            }
        });

        this.addSlot(new Slot(extra, 0, 8, 17));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftSlots, j + i * 3, 36 + j * 18, 17 + i * 18){
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return craftSlots.canPlaceItem(getContainerSlot(),stack);
                    }
                });
            }
        }



        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 142));
        }
    }


    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer,Init.BLOCK);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(Container pInventory) {
        this.access.execute((p_39386_, p_39387_) -> {
            slotChangedCraftingGrid(this,p_39386_, this.player, this.craftSlots, this.resultSlots,null);
        });
    }


    public static final int RESULT_SLOT = 0;

    private static final int CRAFT_SLOT_START = 1+1;
    private static final int CRAFT_SLOT_END = 10+1;
    private static final int INV_SLOT_START = 10+1;
    private static final int INV_SLOT_END = 37+1;
    private static final int USE_ROW_SLOT_START = 37+1;
    private static final int USE_ROW_SLOT_END = 46+1;


    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 0) {
                this.access.execute((p_39378_, p_39379_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, player));
                if (!this.moveItemStackTo(itemstack1, CRAFT_SLOT_END, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex >= CRAFT_SLOT_END && pIndex < USE_ROW_SLOT_END) {
                if (!this.moveItemStackTo(itemstack1, 1, CRAFT_SLOT_END, false)) {
                    if (pIndex < USE_ROW_SLOT_START+1) {
                        if (!this.moveItemStackTo(itemstack1, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, CRAFT_SLOT_END, INV_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, CRAFT_SLOT_END, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((p_39371_, p_39372_) -> {
            this.clearContainer(pPlayer, this.craftSlots);
            this.clearContainer(pPlayer, this.extra);
        });
    }

    protected static void slotChangedCraftingGrid(
            AbstractContainerMenu menu,
            Level level,
            Player player,
            CraftingContainer craftSlots,
            ResultContainer resultSlots,
            @Nullable RecipeHolder<CraftingRecipe> recipe
    ) {
        if (!level.isClientSide) {
            CraftingInput craftinginput = craftSlots.asCraftInput();
            ServerPlayer serverplayer = (ServerPlayer)player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<RecipeHolder<CraftingRecipe>> optional = level.getServer()
                    .getRecipeManager()
                    .getRecipeFor(Init.BONE_CRAFTING, craftinginput, level, recipe);
            if (optional.isPresent()) {
                RecipeHolder<CraftingRecipe> recipeholder = optional.get();
                CraftingRecipe craftingrecipe = recipeholder.value();
                if (resultSlots.setRecipeUsed(level, serverplayer, recipeholder)) {
                    ItemStack itemstack1 = craftingrecipe.assemble(craftinginput, level.registryAccess());
                    if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                        itemstack = itemstack1;
                    }
                }
            }

            resultSlots.setItem(0, itemstack);
            menu.setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));
        }
    }

}
