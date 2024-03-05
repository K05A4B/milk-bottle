package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class ImmuneSlownessEffect extends ImmuneAnyEffect {
    public ImmuneSlownessEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x74501F, StatusEffects.SLOWNESS);
    }
}