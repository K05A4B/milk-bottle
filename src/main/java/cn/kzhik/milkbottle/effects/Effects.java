package cn.kzhik.milkbottle.effects;

import cn.kzhik.milkbottle.utils.Mod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Effects {
    public static StatusEffect IMMUNE_HARMFUL = register("immune_harmful", new ImmuneHarmfulEffect());
    public static StatusEffect IMMUNE_SLOWNESS = register("immune_slowness", new ImmuneSlownessEffect());
    public static StatusEffect IMMUNE_MINING_FATIGUE = register("immune_mining_fatigue", new ImmuneMiningFatigueEffect());
    public static StatusEffect IMMUNE_NAUSEA = register("immune_nausea", new ImmuneNauseaEffect());
    public static StatusEffect IMMUNE_BLINDNESS = register("immune_blindness", new ImmuneBlindnessEffect());
    public static StatusEffect IMMUNE_HUNGER = register("immune_hunger", new ImmuneHungerEffect());
    public static StatusEffect IMMUNE_WEAKNESS = register("immune_weakness", new ImmuneWeaknessEffect());
    public static StatusEffect IMMUNE_POTION = register("immune_potion", new ImmunePoisonEffect());
    public static StatusEffect IMMUNE_WITHER = register("immune_wither", new ImmuneWitherEffect());
    public static StatusEffect IMMUNE_UNLUCK = register("immune_unluck", new ImmuneUnluckEffect());
    public static StatusEffect IMMUNE_DARKNESS = register("immune_darkness", new ImmuneDarknessEffect());
    public static StatusEffect IMMUNE_LEVITATION = register("immune_levitation", new ImmuneLevitationEffect());
    public static StatusEffect RAIN_OF_WEALTH = register(RainOfWealth.EFFECT_ID, new RainOfWealth());


    public Effects() {
    }

    private static StatusEffect register(String name, StatusEffect effect) {
        Identifier id = new Identifier(Mod.getModId(), name);
        return (StatusEffect) Registry.register(Registries.STATUS_EFFECT, id, effect);
    }

}
