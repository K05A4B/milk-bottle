package cn.kzhik.milkbottle;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class ClearHarmfulPotion extends PotionItem {

    private static final String MOD_ID = MilkBottle.MOD_ID;

    public ClearHarmfulPotion(Settings settings) {
        super(settings.maxCount(6));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        HashMap<String, StatusEffect> effects = MilkBottle.getHarmfulEffects();

        for (String name : effects.keySet()) {
            StatusEffect effect = effects.get(name);

            if (user.hasStatusEffect(effect)) {
                user.removeStatusEffect(effect);
            }
        }

        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20*25, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20*3, 2));

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.milk-bottle.clear_harmful_potion.tooltip"));
        tooltip.add(Text.translatable("item.milk-bottle.clear_harmful_potion.effect1").formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("item.milk-bottle.clear_harmful_potion.effect2").formatted(Formatting.AQUA));
    }

    public static void registerPotion(Settings settings) {
        Identifier id = new Identifier(MOD_ID,"clear_harmful_potion");
        ClearHarmfulPotion item = new ClearHarmfulPotion(settings);
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });
    }
}
