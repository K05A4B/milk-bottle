package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;


public class HarmfulEffectImmunity extends StatusEffect {
    public HarmfulEffectImmunity() {
        super(StatusEffectCategory.BENEFICIAL, 0xfafad2);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            StatusEffect effectType = effect.getEffectType();

            if (effectType.getCategory() == StatusEffectCategory.HARMFUL) {
                entity.removeStatusEffect(effectType);
            }
        }
    }

}
