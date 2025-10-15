package tfar.bonecraftingtable;

import net.fabricmc.api.ClientModInitializer;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClient.setup();
    }
}
