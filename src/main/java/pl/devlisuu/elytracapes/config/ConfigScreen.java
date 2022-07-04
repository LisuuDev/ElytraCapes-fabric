package pl.devlisuu.elytracapes.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {
    public static Screen build(Screen screen) {
        final ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle(Text.of("Elytra Capes"))
                .setSavingRunnable(ConfigManager.writeConfig);

        final ConfigCategory generalCategory = builder.getOrCreateCategory(Text.literal("You should not see this"));

        final ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        generalCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Mod enabled"), ConfigManager.getConfig().modEnabled)
                .setSaveConsumer(newValue -> ConfigManager.getConfig().modEnabled = newValue)
                .build());

        generalCategory.addEntry(entryBuilder.startEnumSelector(Text.literal("Cape style"), CapeStyleEnum.class, ConfigManager.getConfig().style)
                .setSaveConsumer(newValue -> ConfigManager.getConfig().style = newValue)
                .build());

        return builder.build();
    }
}
