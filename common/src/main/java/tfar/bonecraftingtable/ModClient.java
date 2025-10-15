package tfar.bonecraftingtable;

import net.minecraft.client.gui.screens.MenuScreens;

public class ModClient {

    public static void setup() {
        MenuScreens.register(Init.MENU_TYPE, BoneCraftingScreen::new);
    }

}
