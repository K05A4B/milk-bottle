package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneBlindnessEffect extends ImmuneAnyEffect {
    public ImmuneBlindnessEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xE0E0DC, StatusEffects.BLINDNESS);
    }
}
