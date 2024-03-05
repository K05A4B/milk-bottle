package cn.kzhik.milkbottle;

import cn.kzhik.milkbottle.effects.ImmuneEffects;
import cn.kzhik.milkbottle.effects.ImmuneHarmfulEffect;
import cn.kzhik.milkbottle.items.*;
import cn.kzhik.milkbottle.utils.Mod;
import cn.kzhik.milkbottle.utils.StatusEffectsArray;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MilkBottle implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("milk-bottle");
    public static String MOD_ID = Mod.getModId();

    @Override
	public void onInitialize() {
		// 注册物品奶瓶
		MilkBottleItem.registerItem();
		registerAntidotePotion(); // 注册解药系列药水
		registerVaccinePotion(); // 注册免疫系列药水
	}

	private void registerAntidotePotion() {
		// 注册清害药水
		ClearHarmfulPotion.registerPotion(new FabricItemSettings());

		// 注册负面效果的解药
		HashMap<String, StatusEffect> harmfulEffects = StatusEffectsArray.getHarmfulEffectsMap();
		for (String effect : harmfulEffects.keySet()) {
			AntidotePotion.registerPotion(
					effect + "_antidote",
					new FabricItemSettings(),
					harmfulEffects.get(effect)
			);
		}

	}

	private void registerVaccinePotion() {
		HashMap<String, StatusEffect> effects = StatusEffectsArray.getImmuneEffectsMap();

		for (String name : effects.keySet()) {
			String ItemId = name.replace("immune_", "") + "_vaccine";
			VaccinePotion.register(ItemId, effects.get(name), 20*60*3);
		}

		HarmfulVaccine.register();
	}

}