package cn.kzhik.milkbottle.item;

import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.ModPotionEffect;
import cn.kzhik.milkbottle.utils.potion.ModPotionUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

public class Antidote extends ModPotionItem {

    public Antidote(Settings settings) {
        super(settings.maxCount(Constants.ANTIDOTE_MAX_COUNT));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ModPotionData nbt = new ModPotionData(stack);
        ArrayList<ModPotionEffect> effects = nbt.getEffects();

        if (nbt.targetedAt() == null) {
            for (ModPotionEffect effect : effects) {
                user.removeStatusEffect(effect.getEffect());
            }
        } else {
            // 针对性清空特定类型的效果效果
            for (StatusEffectInstance effect : user.getStatusEffects()) {
                StatusEffect statusEffect = effect.getEffectType();
                StatusEffectCategory category = statusEffect.getCategory();

                if (category == nbt.targetedAt()) {
                    user.removeStatusEffect(statusEffect);
                }
            }
        }

        return super.finishUsing(stack, world, user);
    }

    @Override
    public String getSuffix(ItemStack stack) {
        return ModPotionUtils.generateSuffix(stack);
    }

    @Override
    public ItemStack getDefaultStack() {
        ModPotionData nbt = new ModPotionData(new ItemStack(this));
        return nbt.stack();
    }
}
