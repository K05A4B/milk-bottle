package cn.kzhik.milkbottle.utils;

import cn.kzhik.milkbottle.effects.Effects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

import java.util.ArrayList;
import java.util.HashMap;

public class StatusEffectsArray {

    public StatusEffectsArray() {}

    // 获取所有的负面效果
    public static HashMap<String, StatusEffect> getHarmfulEffectsMap() {
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

    public static ArrayList<StatusEffect> getHarmfulEffects() {
        ArrayList<StatusEffect> effects = new ArrayList<StatusEffect>();

        HashMap<String, StatusEffect> map = StatusEffectsArray.getHarmfulEffectsMap();

        for (String name : map.keySet()) {
            effects.add(map.get(name));
        }

        return effects;
    }

    public static HashMap<String, StatusEffect> getImmuneEffectsMap() {
        HashMap<String, StatusEffect> map = new HashMap<>();

        map.put("immune_slowness", Effects.IMMUNE_SLOWNESS);
        map.put("immune_mining_fatigue", Effects.IMMUNE_MINING_FATIGUE);
        map.put("immune_nausea", Effects.IMMUNE_NAUSEA);
        map.put("immune_blindness", Effects.IMMUNE_BLINDNESS);
        map.put("immune_hunger", Effects.IMMUNE_HUNGER);
        map.put("immune_weakness", Effects.IMMUNE_WEAKNESS);
        map.put("immune_poison", Effects.IMMUNE_POTION);
        map.put("immune_wither", Effects.IMMUNE_WITHER);
        map.put("immune_levitation", Effects.IMMUNE_LEVITATION);
        map.put("immune_unluck", Effects.IMMUNE_UNLUCK);
        map.put("immune_darkness", Effects.IMMUNE_DARKNESS);

        return map;
    }
}
