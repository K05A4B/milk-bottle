package cn.kzhik.milkbottle.utils.potion.modifier;

import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotionTypeModifier implements ModPotionModifier {
    @Override
    public ModPotionData modify(ModPotionData data) {
        ItemStack itemStack = data.stack();
        Item item = itemStack.getItem();

        if (item == ModItems.MILK_BOTTLE || item == ModItems.ANTIDOTE) {
            return getNewModPotionData(ModItems.ANTIDOTE, data);
        }

        if (item == ModItems.VACCINE) {
            return getNewModPotionData(ModItems.VACCINE, data);
        }

        return null;
    }

    private ModPotionData getNewModPotionData(Item item, ModPotionData data) {
        ItemStack stack = new ItemStack(item);
        stack.setNbt(data.getRawNbt());

        return new ModPotionData(stack);
    }
}
