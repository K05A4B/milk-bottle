package cn.kzhik.milkbottle.effects;

import cn.kzhik.milkbottle.MilkBottle;
import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.Mod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static StatusEffect SLOWNESS_EFFECT_IMMUNITY = registerImmunityEffect("slowness_immunity", new SlownessEffectImmunity());
    public static StatusEffect MINING_FATIGUE_EFFECT_IMMUNITY = registerImmunityEffect("mining_fatigue_immunity", new MiningFatigueEffectImmunity());
    public static StatusEffect NAUSEA_EFFECT_IMMUNITY = registerImmunityEffect("nausea_immunity", new NauseaEffectImmunity());
    public static StatusEffect BLINDNESS_EFFECT_IMMUNITY = registerImmunityEffect("blindness_immunity", new BlindnessEffectImmunity());
    public static StatusEffect HUNGER_EFFECT_IMMUNITY = registerImmunityEffect("hunger_immunity", new HungerEffectImmunity());
    public static StatusEffect WEAKNESS_EFFECT_IMMUNITY = registerImmunityEffect("weakness_immunity", new WeaknessEffectImmunity());
    public static StatusEffect POISON_EFFECT_IMMUNITY = registerImmunityEffect("poison_immunity", new PoisonEffectImmunity());
    public static StatusEffect WITHER_EFFECT_IMMUNITY = registerImmunityEffect("wither_immunity", new WitherEffectImmunity());
    public static StatusEffect LEVITATION_EFFECT_IMMUNITY = registerImmunityEffect("levitation_immunity", new LevitationEffectImmunity());
    public static StatusEffect UNLUCK_EFFECT_IMMUNITY = registerImmunityEffect("unluck_immunity", new UnluckEffectImmunity());
    public static StatusEffect DARKNESS_EFFECT_IMMUNITY = registerImmunityEffect("darkness_immunity", new DarknessEffectImmunity());
    public static StatusEffect HARMFUL_EFFECT_IMMUNITY = registerEffect("harmful_immunity", new HarmfulEffectImmunity());

    private static StatusEffect registerImmunityEffect(String id, BaseImmunityEffect effect) {
        StatusEffect registered = registerEffect(id, effect);

        StatusEffect targetedAt = effect.getTargetedAt();
        Constants.EFFECT_TO_IMMUNITY_MAP.put(targetedAt, effect);
        Constants.IMMUNITY_TO_EFFECT_MAP.put(effect, targetedAt);

        return registered;
    }

    private static StatusEffect registerEffect(String id, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Mod.getModId(), id), effect);
    }

    public static void registerEffects() {
        MilkBottle.LOGGER.info("Registering effects for milk-bottle mod");
    }
}

