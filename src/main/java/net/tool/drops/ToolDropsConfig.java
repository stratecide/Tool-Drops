package net.tool.drops;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ToolDropsConfig {

    private static final String CONFIG_FOLDER = "config/tool drops/";
    private static final String LOOT_TABLES_FOLDER = CONFIG_FOLDER + "loot tables/";

    public static void init() {
        File directory = new File(LOOT_TABLES_FOLDER);
        if (!directory.exists())
            directory.mkdirs();
    }

    public static Map<String, JsonObject> getLootTables() {
        Map<String, JsonObject> result = new HashMap<>();
        File directory = new File(LOOT_TABLES_FOLDER);
        for (File file : directory.listFiles()) {
            if (!file.getName().endsWith(".json"))
                continue;
            try (Scanner scanner = new Scanner(file)) {
                StringBuilder builder = new StringBuilder();
                while (scanner.hasNextLine())
                    builder.append(scanner.nextLine());
                result.put(file.getName().substring(0, file.getName().length() - 5), new JsonParser().parse(builder.toString()).getAsJsonObject());
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
