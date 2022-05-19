package pl.devlisuu.elytracapes.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setLenient().create();
    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "elytracapes.json");
    private static Config config;

    public static Config getConfig() {
        return config;
    }

    public static void loadConfig() {
        try{
            if(configFile.exists()) {
                String json = IOUtils.toString(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));

                config = GSON.fromJson(json, Config.class);
            }else{
                config = new Config();
            }

            writeConfig.run();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public final static Runnable writeConfig = () -> {
        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8));
            writer.write(GSON.toJson(config));
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    };
}
