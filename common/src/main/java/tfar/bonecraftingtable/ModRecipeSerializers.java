package tfar.bonecraftingtable;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModRecipeSerializers {
    public static final ShapelessBoneRecipe.BoneSerializer SHAPELESS = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,BoneCraftingTable.id("shapeless")
            ,new ShapelessBoneRecipe.BoneSerializer());
    public static final ShapedBoneRecipe.BoneSerializer SHAPED =  Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,BoneCraftingTable.id("shaped")
            ,new ShapedBoneRecipe.BoneSerializer());

    public static void init() {}
}
