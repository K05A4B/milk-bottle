package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class DarknessEffectImmunity extends BaseImmunityEffect {
    public DarknessEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.DARKNESS);
    }
}
