package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneNauseaEffect extends ImmuneAnyEffect {

    public ImmuneNauseaEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xAAE2B, StatusEffects.NAUSEA);
    }

}
