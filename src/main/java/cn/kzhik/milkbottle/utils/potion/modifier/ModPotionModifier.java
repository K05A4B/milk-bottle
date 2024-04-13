package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.utils.potion.ModPotionData;

public interface ModPotionModifier {


    // 注意 需要返回修改后的 ModPotionData, 否则修改的地方可能不会被应用
    ModPotionData modify(ModPotionData data);
}
