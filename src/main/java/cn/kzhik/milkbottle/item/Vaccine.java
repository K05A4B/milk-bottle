package cn.kzhik.milkbottle.item;

import cn.kzhik.milkbottle.effects.ModEffects;
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


public class Vaccine extends ModPotionItem {

    public Vaccine(Settings settings) {
        super(settings.maxCount(Constants.VACCINE_MAX_COUNT));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ModPotionData nbt = new ModPotionData(stack);

        if (nbt.targetedAt() == null) {
            for (ModPotionEffect targetedAt : nbt.getEffects()) {
                StatusEffect effect = Constants.EFFECT_TO_IMMUNITY_MAP.get(targetedAt.getEffect());

                if (effect == null) {
                    continue;
                }

                int duration = targetedAt.getDuration();
                if (duration <= 0) {
                    duration = nbt.getDuration();
                }

                user.addStatusEffect(new StatusEffectInstance(effect, duration));
            }
        } else {
            if (nbt.targetedAt() == StatusEffectCategory.HARMFUL) {
                user.addStatusEffect(new StatusEffectInstance(ModEffects.HARMFUL_EFFECT_IMMUNITY, nbt.getDuration()));
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
        nbt.setDuration(Constants.VACCINE_DEFAULT_DURATION);
        return nbt.stack();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
