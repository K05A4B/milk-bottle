package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class BlindnessEffectImmunity extends BaseImmunityEffect {
    public BlindnessEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.BLINDNESS);
    }
}
