package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class HungerEffectImmunity extends BaseImmunityEffect {
    public HungerEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.HUNGER);
    }
}
