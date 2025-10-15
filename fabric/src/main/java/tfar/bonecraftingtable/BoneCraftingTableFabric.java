package tfar.bonecraftingtable;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class BoneCraftingTableFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.
        Init.init();
        // Use Fabric to bootstrap the Common mod.
        BoneCraftingTable.init();
    }

}
