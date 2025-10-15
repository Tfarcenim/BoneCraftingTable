package tfar.bonecraftingtable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public record BonePattern(short pattern, ResourceLocation texture) {

    public static final BonePattern ZERO = new BonePattern((short) 0,tex("bonepress0"));

    public static final BonePattern ONE = new BonePattern((short) 0b10_000,tex("bonepress1"));
    public static final BonePattern TWO = new BonePattern((short) 0b1_0010_000,tex("bonepress2"));
    public static final BonePattern THREE = new BonePattern((short) 0b111,tex("bonepress3"));
    public static final BonePattern FOUR = new BonePattern((short) 0b111_111,tex("bonepress4"));
    public static final BonePattern FIVE = new BonePattern((short) 0b110_110_110,tex("bonepress5"));
    public static final BonePattern SIX = new BonePattern((short) 0b111_111_111,tex("bonepress6"));

    public static final Map<Item,BonePattern> patternMap = new HashMap<>();

    static {
        patternMap.put(Items.OAK_PLANKS,ONE);
        patternMap.put(Items.COBBLESTONE,TWO);
        patternMap.put(Items.IRON_INGOT,THREE);
        patternMap.put(Items.GOLD_INGOT,FOUR);
        patternMap.put(Items.DIAMOND,FIVE);
        patternMap.put(Items.NETHERITE_INGOT,SIX);
    }


    public static ResourceLocation tex(String shorthand) {
        return BoneCraftingTable.id("textures/gui/"+shorthand+".png");
    }

    public static BonePattern findPattern(ItemStack stack) {
        return patternMap.getOrDefault(stack.getItem(),ZERO);
    }

    public boolean canPlace(int slot) {
        return ((1 << slot) & pattern) != 0;
    }
}
