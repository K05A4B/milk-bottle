package cn.kzhik.milkbottle;

import cn.kzhik.milkbottle.items.*;
import cn.kzhik.milkbottle.items.wealthBottles.WealthBottle;
import cn.kzhik.milkbottle.items.wealthBottles.WealthBottleII;
import cn.kzhik.milkbottle.utils.StatusEffectsArray;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MilkBottle implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("milk-bottle");

    @Override
	public void onInitialize() {
		// 注册物品奶瓶
		MilkBottleItem.registerItem();
		registerAntidotePotion(); // 注册解药系列药水
		registerVaccinePotion(); // 注册免疫系列药水

		// 注册财富药水
		WealthBottle.register();
		WealthBottleII.register();
	}

	private void registerAntidotePotion() {
		// 注册净化药水
		HarmfulAntidote.register(new FabricItemSettings());

		// 注册负面效果的解药
		HashMap<String, StatusEffect> harmfulEffects = StatusEffectsArray.getHarmfulEffectsMap();
		HashMap<String, StatusEffect> immuneEffects = StatusEffectsArray.getImmuneEffectsMap();
		for (String effect : harmfulEffects.keySet()) {

			Item.Settings settings = new FabricItemSettings();

			AntidotePotion antidote = AntidotePotion.register(
					effect + "_antidote",
					settings,
					harmfulEffects.get(effect),
					immuneEffects.get("immune_" + effect)
			);
		}

	}

	private void registerVaccinePotion() {
		HashMap<String, StatusEffect> effects = StatusEffectsArray.getImmuneEffectsMap();

		for (String name : effects.keySet()) {
			String ItemId = name.replace("immune_", "") + "_vaccine";
			VaccinePotion.register(ItemId, effects.get(name), 20*60*3);
		}

		// 注册免疫药水
		HarmfulVaccine.register();
	}

}