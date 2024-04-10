package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class WeaknessEffectImmunity extends BaseImmunityEffect {
    public WeaknessEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.WEAKNESS);
    }
}
