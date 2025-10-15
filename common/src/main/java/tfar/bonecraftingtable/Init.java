package tfar.bonecraftingtable;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Init {

    public static final Block BLOCK = Registry.register(BuiltInRegistries.BLOCK,BoneCraftingTable.id("bone_crafting_table"),
            new BoneCraftingTableBlock(BlockBehaviour.Properties.of().strength(2.5f,5)));
    public static final Item ITEM = Registry.register(BuiltInRegistries.ITEM,BoneCraftingTable.id("bone_crafting_table"),new BlockItem(BLOCK,new Item.Properties()));
    public static final MenuType<BoneCraftingMenu> MENU_TYPE = Registry.register(BuiltInRegistries.MENU,BoneCraftingTable.id("bone_crafting_table"),
            new MenuType<>(BoneCraftingMenu::new, FeatureFlags.VANILLA_SET));

    public static final RecipeType<CraftingRecipe> BONE_CRAFTING = register("bone_crafting");

    static <T extends Recipe<?>> RecipeType<T> register(final String identifier) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, BoneCraftingTable.id(identifier), new RecipeType<T>() {
            @Override
            public String toString() {
                return identifier;
            }
        });
    }

    public static void init() {
        ModRecipeSerializers.init();
    }
}
