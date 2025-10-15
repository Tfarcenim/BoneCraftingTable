package tfar.bonecraftingtable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class ShapelessBoneRecipe extends ShapelessRecipe{

    public ShapelessBoneRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
    }

    public ShapelessBoneRecipe(ShapelessRecipe recipe) {
        this(recipe.getGroup(),recipe.category(), recipe.getResultItem(null), recipe.getIngredients());
    }

    @Override
    public RecipeType<?> getType() {
        return Init.BONE_CRAFTING;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SHAPELESS;
    }


    public static class BoneSerializer extends Serializer {
        public static final MapCodec<ShapelessBoneRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(ShapelessRecipe.Serializer.CODEC.forGetter(upgradeRecipe -> upgradeRecipe))
                        .apply(instance,ShapelessBoneRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessBoneRecipe> STREAM_CODEC = StreamCodec.of(
                ShapelessRecipe.Serializer.STREAM_CODEC::encode, pBuffer -> new ShapelessBoneRecipe(ShapelessRecipe.Serializer.STREAM_CODEC.decode(pBuffer)));

        @Override
        public MapCodec<ShapelessRecipe> codec() {
            return (MapCodec<ShapelessRecipe>)(Object) CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessRecipe> streamCodec() {
            return  (StreamCodec<RegistryFriendlyByteBuf, ShapelessRecipe>) (Object) STREAM_CODEC;
        }
    }
}
