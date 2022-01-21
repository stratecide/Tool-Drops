package net.tool.drops;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ToolDropsMod implements ModInitializer {

	private static final Gson GSON = LootGsons.getTableGsonBuilder().create();

	public static final Map<String, LootTable> lootTables = new HashMap<>();

	@Override
	public void onInitialize() {
		ToolDropsConfig.init();
		Map<String, JsonObject> lootTableData = ToolDropsConfig.getLootTables();
		for (String key : lootTableData.keySet()) {
			LootTable lootTable = GSON.fromJson(lootTableData.get(key), LootTable.class);
			lootTables.put(key, lootTable);
		}
	}

	public static String identifierToString(Identifier identifier) {
		return identifier.toString().replace(":", ".");
	}
}
