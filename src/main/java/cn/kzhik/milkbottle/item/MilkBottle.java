package cn.kzhik.milkbottle.item;

import cn.kzhik.milkbottle.utils.Constants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MilkBottle extends ModPotionItem {

    public MilkBottle(Settings settings) {
        super(settings.maxCount(Constants.MILK_BOTTLE_MAX_COUNT));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.clearStatusEffects();
        return super.finishUsing(stack, world, user);
    }
}
