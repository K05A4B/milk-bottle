package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.ModPotionEffect;
import net.minecraft.entity.effect.StatusEffect;

import java.util.ArrayList;
import java.util.Collection;

public class EffectModifier implements ModPotionModifier {
    private final ArrayList<ModPotionEffect> effects = new ArrayList<>();

    public EffectModifier(int duration, int amplifier, StatusEffect... effects) {
        for (StatusEffect effect : effects) {
            this.effects.add(new ModPotionEffect(duration, amplifier, effect));
        }
    }

    public EffectModifier(int duration, int amplifier, Collection<StatusEffect> effects) {
        for (StatusEffect effect : effects) {
            this.effects.add(new ModPotionEffect(duration, amplifier, effect));
        }
    }


    public static EffectModifier build(StatusEffect... effects) {
        return new EffectModifier(Constants.DEFAULT_DURATION, 0, effects);
    }

    public static EffectModifier build(Collection<StatusEffect> effects) {
        return new EffectModifier(Constants.DEFAULT_DURATION, 0, effects);
    }

    @Override
    public ModPotionData modify(ModPotionData data) {

        if (data.targetedAt() != null) {
            return data;
        }

        if (!effects.isEmpty()) {
            data.addEffectAll(effects);
        }

        return data;
    }
}
