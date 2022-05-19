package pl.devlisuu.elytracapes;

import net.fabricmc.api.ModInitializer;
import pl.devlisuu.elytracapes.config.ConfigManager;

public class ElytraCapes implements ModInitializer {

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
    }
}
