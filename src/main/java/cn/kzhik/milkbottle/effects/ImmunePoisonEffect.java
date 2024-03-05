package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmunePoisonEffect extends ImmuneAnyEffect {
    public ImmunePoisonEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x785C9C, StatusEffects.POISON);
    }
}
