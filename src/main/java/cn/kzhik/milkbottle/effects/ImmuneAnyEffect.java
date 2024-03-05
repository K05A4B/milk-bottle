package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.ArrayList;

public class ImmuneAnyEffect extends StatusEffect {

    private ArrayList<StatusEffect> effects = new ArrayList<>();
    private String effectName;

    public ImmuneAnyEffect(StatusEffectCategory type, int color, ArrayList<StatusEffect> effects) {
        super(type, color);
        this.effects = effects;
    }

    public ImmuneAnyEffect(StatusEffectCategory type, int color, StatusEffect effect) {
        super(type, color);
        this.effects.add(effect);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        // 清除effects内的所有的效果
        for (StatusEffect effect : effects) {

            if (!entity.hasStatusEffect(effect)) {
                continue;
            }

            entity.removeStatusEffect(effect);
        }
    }
}
