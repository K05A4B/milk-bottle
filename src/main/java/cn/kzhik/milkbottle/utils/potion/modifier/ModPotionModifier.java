package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.utils.potion.ModPotionData;

public interface ModPotionModifier {
    ModPotionData modify(ModPotionData data);
}
