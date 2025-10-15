package tfar.bonecraftingtable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class ShapelessRecipeBuilderCustom extends ShapelessRecipeBuilder {


    public ShapelessRecipeBuilderCustom(RecipeCategory category, ItemLike result, int count) {
        super(category, result, count);
    }

    public ShapelessRecipeBuilderCustom(RecipeCategory p_249996_, ItemStack result) {
        super(p_249996_, result);
    }


    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapelessRecipeBuilderCustom customShapeless(RecipeCategory pCategory, ItemLike pResult) {
        return customShapeless(pCategory, pResult, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapelessRecipeBuilderCustom customShapeless(RecipeCategory pCategory, ItemLike pResult, int pCount) {
        return new ShapelessRecipeBuilderCustom(pCategory, pResult, pCount);
    }

    public static ShapelessRecipeBuilderCustom customShapeless(RecipeCategory p_251325_, ItemStack result) {
        return new ShapelessRecipeBuilderCustom(p_251325_, result);
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        ShapelessBoneRecipe shapedrecipe = new ShapelessBoneRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                this.resultStack,ingredients);
        pRecipeOutput.accept(pId, shapedrecipe, advancement$builder.build(pId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }
}
