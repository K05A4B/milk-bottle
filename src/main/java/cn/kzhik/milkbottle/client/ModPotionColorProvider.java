package cn.kzhik.milkbottle.client;

import cn.kzhik.milkbottle.utils.Mod;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.ModPotionEffect;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ModPotionColorProvider implements ItemColorProvider {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {

        // 排除layer0
        if (tintIndex == 1) {
            return -1;
        }

        ModPotionData nbt = new ModPotionData(stack);
        StatusEffectCategory targetedAt = nbt.targetedAt();
        if (targetedAt == null) {
            ArrayList<ModPotionEffect> effects = nbt.getEffects();
            if (effects.isEmpty()) {
                return -1;
            }

            StatusEffect effect = effects.get(0).getEffect();
            if (effect == null) {
                return -1;
            }

            int color = effect.getColor();
            return Mod.getComplementaryColor(color);
        }

        return switch (targetedAt) {
            case BENEFICIAL -> 0x8B4513;
            case HARMFUL -> 0xfdf55f;
            case NEUTRAL -> 0xD3D3D3;
        };
    }
}