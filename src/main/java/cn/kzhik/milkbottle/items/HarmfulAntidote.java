package cn.kzhik.milkbottle.items;

import cn.kzhik.milkbottle.effects.ImmuneEffects;
import cn.kzhik.milkbottle.utils.Mod;
import cn.kzhik.milkbottle.utils.StatusEffectsArray;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarmfulAntidote extends AntidotePotion {

    private static final String MOD_ID = Mod.getModId();

    public HarmfulAntidote(Settings settings) {
        super(settings, StatusEffectsArray.getHarmfulEffects(), ImmuneEffects.IMMUNE_HARMFUL);
    }

    public static void register(Settings settings) {
        Identifier id = new Identifier(MOD_ID, "harmful_antidote");
        HarmfulAntidote item = new HarmfulAntidote(settings);
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20 * 25, 0));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 3, 2));

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.milk-bottle.harmful_antidote.effect1").formatted(Formatting.AQUA));
        tooltip.add(Text.translatable("item.milk-bottle.harmful_antidote.effect2").formatted(Formatting.AQUA));
    }
}
