package cn.kzhik.milkbottle.effects;

import cn.kzhik.milkbottle.utils.Mod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BaseImmunityEffect extends StatusEffect {

    // 存储针对的某一个效果
    private final StatusEffect targetedAt;

    public BaseImmunityEffect(StatusEffectCategory type, StatusEffect effect) {
        super(type, Mod.getComplementaryColor(effect.getColor()));
        this.targetedAt = effect;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (targetedAt == null) {
            return;
        }

        if (!entity.hasStatusEffect(targetedAt)) {
            return;
        }

        entity.removeStatusEffect(targetedAt);
    }

    public StatusEffect getTargetedAt() {
        return this.targetedAt;
    }
}
