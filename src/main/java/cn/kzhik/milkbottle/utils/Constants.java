package cn.kzhik.milkbottle.utils;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

import java.util.HashMap;

public class Constants {

    public static int DEFAULT_DURATION = 3600;
    public static int VACCINE_DEFAULT_DURATION = DEFAULT_DURATION;
    public static int VACCINE_MAX_COUNT = 6;
    public static int ANTIDOTE_MAX_COUNT = 6;
    public static int MILK_BOTTLE_MAX_COUNT = 12;
    public static int MEDICINE_STOVE_DELAY = 400;
    public static String MEDICINE_STOVE_WORKING_PROCESSOR = "medicine_stove_working_processor";
    public static StatusEffect[] VANILLA_HARMFUL_EFFECTS = {
            StatusEffects.SLOWNESS,
            StatusEffects.MINING_FATIGUE,
            StatusEffects.NAUSEA,
            StatusEffects.BLINDNESS,
            StatusEffects.HUNGER,
            StatusEffects.WEAKNESS,
            StatusEffects.POISON,
            StatusEffects.WITHER,
            StatusEffects.LEVITATION,
            StatusEffects.UNLUCK,
            StatusEffects.DARKNESS
    };
    public static HashMap<StatusEffect, StatusEffect> EFFECT_TO_IMMUNITY_MAP = new HashMap<>();
    public static HashMap<StatusEffect, StatusEffect> IMMUNITY_TO_EFFECT_MAP = new HashMap<>();
    public Constants() {

    }
}
