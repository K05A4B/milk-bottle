package cn.kzhik.milkbottle.items.wealthBottles;

import cn.kzhik.milkbottle.effects.Effects;
import cn.kzhik.milkbottle.utils.Mod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class WealthBottle extends PotionItem {

    static String ITEM_NAME = "wealth_bottle";
    static Identifier ITEM_ID = new Identifier(Mod.getModId(), ITEM_NAME);
    protected int defaultAmplifier = 0;
    protected int defaultDuration = 5 * 20;
    Random random = new Random();

    public WealthBottle(Settings setting) {
        super(setting.maxCount(6));
    }

    public WealthBottle(Settings setting, int amplifier, int duration) {
        super(setting.maxCount(6));
        this.defaultAmplifier = amplifier;
        this.defaultDuration = duration;
    }

    private static String intToRoman(int i) {
        String[] RomanMap = {"I", "II", "III", "VI", "V"};

        return RomanMap[i % RomanMap.length];
    }

    public static void register() {
        Item item = new WealthBottle(new FabricItemSettings());
        Registry.register(Registries.ITEM, ITEM_ID, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            for (int i = 0; i < 5; i++) {
                int duration = 5 * (i + 1);

                ItemStack stack = new ItemStack(item);
                NbtCompound nbt = new NbtCompound();

                nbt.putInt("amplifier", i);
                nbt.putInt("duration", duration * 20);

                stack.setNbt(nbt);
                content.add(stack);
            }
        });
    }

    private int getAmplifier(NbtCompound nbt) {
        return nbt.getInt("amplifier");
    }

    private int getDuration(NbtCompound nbt) {
        return nbt.getInt("duration");
    }

    private String parseDuration(int duration) {
        duration = duration / 20; // 将单位 s 转换为 gt
        int seconds = duration % 60;
        int minutes = (duration - seconds) / 60;
        String result = "";

        result = minutes < 10 ? ("0" + minutes) : Integer.toString(minutes);
        result += ":";
        result += seconds < 10 ? ("0" + seconds) : Integer.toString(seconds);

        return result;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int amplifier = defaultAmplifier;
        int duration = defaultDuration;

        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            amplifier = getAmplifier(nbt);
            duration = getDuration(nbt);
        }

        Text text = Text.translatable("item.milk-bottle.wealth_bottle.tooltip1", intToRoman(amplifier), parseDuration(duration))
                .formatted(Formatting.AQUA);
        tooltip.add(text);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        NbtCompound nbt = stack.getNbt();
        int amplifier = defaultAmplifier;
        int duration = defaultDuration;

        if (nbt != null) {
            amplifier = getAmplifier(nbt);
            duration = getDuration(nbt);
        }

        StatusEffectInstance effectInstance = new StatusEffectInstance(Effects.RAIN_OF_WEALTH, duration, amplifier);
        user.addStatusEffect(effectInstance);

        return super.finishUsing(stack, world, user);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) {
            return false;
        }

        int amplifier = nbt.getInt("amplifier");

        return amplifier >= 2;
    }
}

