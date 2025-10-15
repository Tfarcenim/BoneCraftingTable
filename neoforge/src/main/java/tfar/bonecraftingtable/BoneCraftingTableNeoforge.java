package tfar.bonecraftingtable;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.concurrent.CompletableFuture;

@Mod(BoneCraftingTable.MOD_ID)
public class BoneCraftingTableNeoforge {

    public BoneCraftingTableNeoforge(IEventBus eventBus, Dist dist) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        eventBus.addListener(this::register);
        eventBus.addListener(this::datagen);
        if (dist.isClient()) {
            Client.init(eventBus);
        }
        BoneCraftingTable.init();

    }

    void register(RegisterEvent event) {
        Init.init();
    }

    void datagen(GatherDataEvent event) {
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeServer(),new ModRecipeProvider(packOutput,lookupProvider));
    }

    public static class Client {
        public static void init(IEventBus bus) {
            bus.addListener(Client::setup);
        }

        public static void setup(FMLClientSetupEvent event) {
            ModClient.setup();
        }
    }
}