package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class LevitationEffectImmunity extends BaseImmunityEffect {
    public LevitationEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.LEVITATION);
    }
}
