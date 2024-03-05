package cn.kzhik.milkbottle.effects;

import cn.kzhik.milkbottle.utils.StatusEffectsArray;
import net.minecraft.entity.effect.StatusEffectCategory;


public class ImmuneHarmfulEffect extends ImmuneAnyEffect {
    public ImmuneHarmfulEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xfafad2, StatusEffectsArray.getHarmfulEffects());
    }

}
