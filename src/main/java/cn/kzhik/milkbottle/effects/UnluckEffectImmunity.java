package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class UnluckEffectImmunity extends BaseImmunityEffect {
    public UnluckEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.UNLUCK);
    }
}
