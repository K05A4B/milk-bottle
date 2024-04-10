package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import net.minecraft.item.ItemStack;

public class UpgradePotionModifier implements ModPotionModifier {
    @Override
    public ModPotionData modify(ModPotionData data) {
        ItemStack stack = new ItemStack(ModItems.VACCINE);
        stack.setNbt(data.getRawNbt());

        return new ModPotionData(stack);
    }
}
