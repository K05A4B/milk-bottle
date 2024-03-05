package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneMiningFatigueEffect extends ImmuneAnyEffect {
    public ImmuneMiningFatigueEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xB5BDE8, StatusEffects.MINING_FATIGUE);
    }
}