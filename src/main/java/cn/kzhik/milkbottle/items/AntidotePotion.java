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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AntidotePotion extends PotionItem {

    private static final String MOD_ID = Mod.getModId();
    private StatusEffect rareEffect = null;
    private ArrayList<StatusEffect> effects = new ArrayList<>();

    private static final Random random = new Random();

    public AntidotePotion(Settings settings, ArrayList<StatusEffect> effects, StatusEffect rareEffect) {
        super(settings.maxCount(6));
        this.effects = effects;
        this.rareEffect = rareEffect;
    }

    public static AntidotePotion register(String name, Settings settings, ArrayList<StatusEffect> effects, StatusEffect rareEffect) {
        Identifier id = new Identifier(MOD_ID, name);
        AntidotePotion item = new AntidotePotion(settings, effects, rareEffect);
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });

        return item;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
    }

    public static AntidotePotion register(String name, Settings settings, StatusEffect effect, StatusEffect rareEffect) {
        ArrayList<StatusEffect> effects = new ArrayList<>();
        effects.add(effect);

        return AntidotePotion.register(name, settings, effects, rareEffect);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        for (StatusEffect effect : effects) {
            if (user.hasStatusEffect(effect)) {
                user.removeStatusEffect(effect);
            }
        }

        int max = 60;
        int min = 40;
        int duration = 20 * (random.nextInt(max) % (max - min + 1) + min);
        double probability = 0.0095;

        if (random.nextDouble() < probability && rareEffect != null) {
            user.addStatusEffect(new StatusEffectInstance(rareEffect, duration));
        }

        return super.finishUsing(stack, world, user);
    }
}
