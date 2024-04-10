package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TargetedAtModifier implements ModPotionModifier {

    private StatusEffectCategory category = null;

    public TargetedAtModifier(StatusEffectCategory category) {
        this.category = category;
    }

    @Override
    public ModPotionData modify(ModPotionData data) {
        if (!data.getEffects().isEmpty()) {
            return data;
        }

        data.setTargetedAt(category);
        data.setDuration(Constants.DEFAULT_DURATION);

        return data;
    }
}
