package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class WitherEffectImmunity extends BaseImmunityEffect {
    public WitherEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.WITHER);
    }
}
