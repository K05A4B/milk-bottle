package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneHungerEffect extends ImmuneAnyEffect {
    public ImmuneHungerEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xA789AC, StatusEffects.HUNGER);
    }
}
