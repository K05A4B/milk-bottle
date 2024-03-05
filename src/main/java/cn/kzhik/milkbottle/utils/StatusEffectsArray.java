package cn.kzhik.milkbottle.utils;

import cn.kzhik.milkbottle.effects.ImmuneAnyEffect;
import cn.kzhik.milkbottle.effects.ImmuneEffects;
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

        map.put("immune_slowness", ImmuneEffects.IMMUNE_SLOWNESS);
        map.put("immune_mining_fatigue", ImmuneEffects.IMMUNE_MINING_FATIGUE);
        map.put("immune_nausea", ImmuneEffects.IMMUNE_NAUSEA);
        map.put("immune_blindness", ImmuneEffects.IMMUNE_BLINDNESS);
        map.put("immune_hunger", ImmuneEffects.IMMUNE_HUNGER);
        map.put("immune_weakness", ImmuneEffects.IMMUNE_WEAKNESS);
        map.put("immune_poison", ImmuneEffects.IMMUNE_POTION);
        map.put("immune_wither", ImmuneEffects.IMMUNE_WITHER);
        map.put("immune_levitation", ImmuneEffects.IMMUNE_LEVITATION);
        map.put("immune_unluck", ImmuneEffects.IMMUNE_UNLUCK);
        map.put("immune_darkness", ImmuneEffects.IMMUNE_DARKNESS);

        return map;
    }
}
