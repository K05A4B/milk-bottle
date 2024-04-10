package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.ModPotionEffect;
import net.minecraft.entity.effect.StatusEffect;

public class EffectModifier implements ModPotionModifier {
    private ModPotionEffect effect = null;

    public EffectModifier(StatusEffect effect, int duration, int amplifier) {
        this.effect = new ModPotionEffect(duration, amplifier, effect);
    }

    public static EffectModifier build(StatusEffect effect) {
        return new EffectModifier(effect, Constants.DEFAULT_DURATION, 0);
    }

    @Override
    public ModPotionData modify(ModPotionData data) {

        if (data.targetedAt() != null) {
            return data;
        }

        if (effect != null) {
            data.addEffect(effect);
        }

        return data;
    }
}
