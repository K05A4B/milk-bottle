package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneUnluckEffect extends  ImmuneAnyEffect {
    public ImmuneUnluckEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xf, StatusEffects.UNLUCK);
    }
}
