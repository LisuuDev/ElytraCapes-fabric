package pl.devlisuu.elytracapes.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

public class ConfigScreen {
    public static Screen build(Screen screen) {
        final ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle(new LiteralText("Elytra Capes"))
                .setSavingRunnable(ConfigManager.writeConfig);

        final ConfigCategory generalCategory = builder.getOrCreateCategory(new LiteralText("You should not see this"));

        final ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        generalCategory.addEntry(entryBuilder.startBooleanToggle(new LiteralText("Mod enabled"), ConfigManager.getConfig().modEnabled)
                .setSaveConsumer(newValue -> ConfigManager.getConfig().modEnabled = newValue)
                .build());

        generalCategory.addEntry(entryBuilder.startEnumSelector(new LiteralText("Cape style"), CapeStyleEnum.class, ConfigManager.getConfig().style)
                .setSaveConsumer(newValue -> ConfigManager.getConfig().style = newValue)
                .build());

        return builder.build();
    }
}
