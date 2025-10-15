package tfar.bonecraftingtable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public class ShapedRecipeBuilderCustom extends ShapedRecipeBuilder {


    public ShapedRecipeBuilderCustom(RecipeCategory category, ItemLike result, int count) {
        super(category, result, count);
    }

    public ShapedRecipeBuilderCustom(RecipeCategory p_249996_, ItemStack result) {
        super(p_249996_, result);
    }


    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedRecipeBuilderCustom customShaped(RecipeCategory pCategory, ItemLike pResult) {
        return customShaped(pCategory, pResult, 1);
    }

    /**
     * Creates a new builder for a shaped recipe.
     */
    public static ShapedRecipeBuilderCustom customShaped(RecipeCategory pCategory, ItemLike pResult, int pCount) {
        return new ShapedRecipeBuilderCustom(pCategory, pResult, pCount);
    }

    public static ShapedRecipeBuilderCustom customShaped(RecipeCategory p_251325_, ItemStack result) {
        return new ShapedRecipeBuilderCustom(p_251325_, result);
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        ShapedRecipePattern shapedrecipepattern = this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        ShapedBoneRecipe shapedrecipe = new ShapedBoneRecipe(
                Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category),
                shapedrecipepattern,
                this.resultStack,
                this.showNotification);
        pRecipeOutput.accept(pId, shapedrecipe, advancement$builder.build(pId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }
}
