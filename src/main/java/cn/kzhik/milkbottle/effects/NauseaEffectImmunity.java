package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class NauseaEffectImmunity extends BaseImmunityEffect {

    public NauseaEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, StatusEffects.NAUSEA);
    }

}
