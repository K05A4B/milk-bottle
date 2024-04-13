package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.ModPotionEffect;

public class DurationExtenderModifier implements ModPotionModifier {
    @Override
    public ModPotionData modify(ModPotionData data) {
        if (data.getRevisionCount() > 9) {
            return data;
        }

        if (data.targetedAt() != null) {
            data.setDuration(getNewDuration(data, data.getDuration()));
            return data;
        }

        for (ModPotionEffect effect : data.getEffects()) {
            int duration = getNewDuration(data, effect.getDuration());
            effect.setDuration(duration);
            data.updateEffect(effect);
        }

        return data;
    }

    private int getNewDuration(ModPotionData data, int duration) {
        return (int) Math.round(duration + Constants.DEFAULT_DURATION * (1 - data.getRevisionCount() * 0.1));
    }
}
