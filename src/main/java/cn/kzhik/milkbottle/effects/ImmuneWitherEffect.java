package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneWitherEffect extends  ImmuneAnyEffect {
    public ImmuneWitherEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x8C9EA9, StatusEffects.WITHER);
    }
}
