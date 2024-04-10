package cn.kzhik.milkbottle.utils.potion;

import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.utils.Mod;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModPotionUtils {
    public static String generateSuffix(ItemStack stack) {
        ModPotionData nbt = new ModPotionData(stack);
        StatusEffectCategory targetedAt = nbt.targetedAt();

        if (targetedAt != null) {
            return "targetedAt." + targetedAt.name().toLowerCase();
        }

        ArrayList<ModPotionEffect> effects = nbt.getEffects();
        if (effects.size() == 1) {
            Identifier id = Registries.STATUS_EFFECT.getId(effects.get(0).getEffect());

            if (id == null) {
                return "";
            }

            return id.getNamespace() + "." + id.getPath();
        }

        if (effects.size() > 1) {
            return "multi";
        }

        return "";
    }

    public static void generateTooltip(ItemStack stack, List<Text> tooltip) {
        ModPotionData nbt = new ModPotionData(stack);
        Text title = null;

        if (stack.getItem().equals(ModItems.VACCINE)) {
            title = Text.translatable("item.milk-bottle.vaccine.tooltip1")
                    .formatted(Formatting.GREEN);
        }

        if (stack.getItem().equals(ModItems.ANTIDOTE)) {
            title = Text.translatable("item.milk-bottle.antidote.tooltip1").formatted(Formatting.GREEN);
        }

        if (title != null) {
            tooltip.add(title);
        }

        ArrayList<ModPotionEffect> effects = nbt.getEffects();
        if (effects.isEmpty()) {
            return;
        }

        for (ModPotionEffect effect : effects) {
            MutableText effectName = Text.translatable((effect.getEffect().getTranslationKey()));
            MutableText showEffect = effectName;
            int duration = effect.getDuration();

            if (duration <= 0) {
                duration = nbt.getDuration();
            }

            if (stack.getItem().equals(ModItems.VACCINE)) {
                showEffect = Text.translatable("item.milk-bottle.vaccine.showEffect",
                        effectName, Mod.makeDurationString(duration));
            }


            tooltip.add(showEffect.formatted(Formatting.AQUA));
        }
    }
}
