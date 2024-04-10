package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class SlownessEffectImmunity extends BaseImmunityEffect {
    public SlownessEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.SLOWNESS);
    }
}