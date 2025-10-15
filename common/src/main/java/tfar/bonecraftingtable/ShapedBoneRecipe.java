package tfar.bonecraftingtable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class ShapedBoneRecipe extends ShapedRecipe {


    public ShapedBoneRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result) {
        super(group, category, pattern, result);
    }

    public ShapedBoneRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
    }

    public ShapedBoneRecipe(ShapedRecipe recipe) {
        this(recipe.getGroup(), recipe.category(), recipe.pattern, recipe.getResultItem(null));
    }



    @Override
    public RecipeType<?> getType() {
        return Init.BONE_CRAFTING;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SHAPED;
    }



    public static class BoneSerializer extends Serializer {
        public static final MapCodec<ShapelessBoneRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(ShapelessRecipe.Serializer.CODEC.forGetter(upgradeRecipe -> upgradeRecipe))
                        .apply(instance,ShapelessBoneRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedBoneRecipe> STREAM_CODEC = StreamCodec.of(
                ShapedRecipe.Serializer.STREAM_CODEC::encode, pBuffer -> new ShapedBoneRecipe(ShapedRecipe.Serializer.STREAM_CODEC.decode(pBuffer)));

        @Override
        public MapCodec<ShapedRecipe> codec() {
            return (MapCodec<ShapedRecipe>)(Object) CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedRecipe> streamCodec() {
            return  (StreamCodec<RegistryFriendlyByteBuf, ShapedRecipe>) (Object) STREAM_CODEC;
        }
    }
}
