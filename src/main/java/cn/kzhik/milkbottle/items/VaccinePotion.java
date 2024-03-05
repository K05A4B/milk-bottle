package cn.kzhik.milkbottle.items;

import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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

public class VaccinePotion extends PotionItem {

    private ArrayList<StatusEffect> effects = new ArrayList<>();
    private final int effectDuration;

    public VaccinePotion(Settings settings, ArrayList<StatusEffect> effects, int duration) {
        super(settings.maxCount(6));
        this.effects = effects;
        this.effectDuration = duration;
    }

    public VaccinePotion(Settings settings, StatusEffect effect, int duration) {
        super(settings.maxCount(6));
        this.effects.add(effect);
        this.effectDuration = duration;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        for (StatusEffect effect : effects) {
            StatusEffectInstance effectInstance = new StatusEffectInstance(effect, this.effectDuration);
            user.addStatusEffect(effectInstance);
        }

        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public static VaccinePotion register(String name, StatusEffect effect, int duration) {
        Identifier id = new Identifier(Mod.getModId(), name);
        VaccinePotion item = new VaccinePotion(new FabricItemSettings(), effect, duration);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(item);
        });

        return Registry.register(Registries.ITEM, id, item);
    }
}
