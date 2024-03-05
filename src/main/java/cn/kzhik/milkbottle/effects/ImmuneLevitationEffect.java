package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneLevitationEffect extends ImmuneAnyEffect {
    public ImmuneLevitationEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x310000, StatusEffects.LEVITATION);
    }
}
