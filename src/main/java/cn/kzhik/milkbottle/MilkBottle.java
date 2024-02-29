package cn.kzhik.milkbottle;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MilkBottle implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("milk-bottle");
    public static String MOD_ID = "milk-bottle";

    @Override
	public void onInitialize() {
		LOGGER.info("Loading MilkBottle...");

		// 注册物品奶瓶
		milkBottleItem.registerItem();

		// 注册清害药水
		ClearHarmfulPotion.registerPotion(new FabricItemSettings());

		// 注册负面效果的解药
		HashMap<String, StatusEffect> harmfulEffects = getHarmfulEffects();
		for (String effect : harmfulEffects.keySet()) {
			AntidotePotion.registerPotion(
					effect + "_antidote",
					new FabricItemSettings(),
					harmfulEffects.get(effect)
			);
		}
	}

	public static HashMap<String, StatusEffect> getHarmfulEffects() {
		HashMap<String, StatusEffect> map = new HashMap<>();
		map.put("slowness", StatusEffects.SLOWNESS);
		map.put("mining_fatigue", StatusEffects.MINING_FATIGUE);
		map.put("nausea", StatusEffects.NAUSEA);
		map.put("blindness", StatusEffects.BLINDNESS);
		map.put("hunger", StatusEffects.HUNGER);
		map.put("weakness", StatusEffects.WEAKNESS);
		map.put("poison", StatusEffects.POISON);
		map.put("wither", StatusEffects.WITHER);
		map.put("levitation", StatusEffects.LEVITATION);
		map.put("unluck", StatusEffects.UNLUCK);
		map.put("darkness", StatusEffects.DARKNESS);
		return map;
	}
}