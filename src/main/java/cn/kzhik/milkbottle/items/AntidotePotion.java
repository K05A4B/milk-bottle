package cn.kzhik.milkbottle.items;

import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class AntidotePotion extends PotionItem {

    private static final String MOD_ID = Mod.getModId();
    private final StatusEffect effect;
    private final StatusEffect rareEffect;
    private String name = null;

    private static final Random random = new Random();

    public AntidotePotion(Settings settings, StatusEffect effect, StatusEffect rareEffect, String name) {
        super(settings.maxCount(6));
        this.effect = effect;
        this.rareEffect = rareEffect;
        this.name = name;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.removeStatusEffect(effect);
        int duration = 20*60;
        double probability = 0.0095;

        if (random.nextDouble() < probability) {
            user.addStatusEffect(new StatusEffectInstance(rareEffect, duration));
        }

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.milk-bottle." + name + ".tooltip"));
    }

    public static void registerPotion(String name, Settings settings, StatusEffect effect, StatusEffect rareEffect) {
        Identifier id = new Identifier(MOD_ID, name);
        AntidotePotion item =  new AntidotePotion(settings, effect, rareEffect, name);
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });
    }
}
