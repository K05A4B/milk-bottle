package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class PoisonEffectImmunity extends BaseImmunityEffect {
    public PoisonEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.POISON);
    }
}
