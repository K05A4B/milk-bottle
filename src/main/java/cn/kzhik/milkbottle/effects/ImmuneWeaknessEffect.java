package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneWeaknessEffect extends ImmuneAnyEffect {
    public ImmuneWeaknessEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xB7B2B7, StatusEffects.WEAKNESS);
    }
}
