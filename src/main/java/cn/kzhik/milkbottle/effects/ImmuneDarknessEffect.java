package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneDarknessEffect extends ImmuneAnyEffect {
    public ImmuneDarknessEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xD6D8DE, StatusEffects.DARKNESS);
    }
}
