package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class MiningFatigueEffectImmunity extends BaseImmunityEffect {
    public MiningFatigueEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.MINING_FATIGUE);
    }
}