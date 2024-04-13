package cn.kzhik.milkbottle.utils.potion;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public record ModPotionData(ItemStack stack) {

    public static final String POTION_TAG_KEY = "Potion";

    public ModPotionData {
        NbtCompound nbt = stack.getOrCreateNbt();

        if (!nbt.contains(POTION_TAG_KEY)) {
            NbtCompound potionNbt = new NbtCompound();
            nbt.put(POTION_TAG_KEY, potionNbt);
        }
    }

    public ModPotionData addEffect(ModPotionEffect effect) {
        addEffectAll(Collections.singleton(effect));

        return this;
    }

    public ModPotionData addEffectAll(Collection<ModPotionEffect> collection) {
        ArrayList<ModPotionEffect> effects = getEffects();

        for (ModPotionEffect effect : collection) {
            if (!effects.contains(effect)) {
                effects.add(effect);
            }
        }

        setEffects(effects);
        return this;
    }

    public ArrayList<ModPotionEffect> getEffects() {
        NbtCompound nbt = getNbt();

        if (!nbt.contains("effects")) {
            return new ArrayList<>();
        }

        NbtList effectList = (NbtList) nbt.get("effects");
        ArrayList<ModPotionEffect> result = new ArrayList<>();

        if (effectList == null) {
            return result;
        }

        for (NbtElement nbtElement : effectList) {
            NbtCompound effectNbt = (NbtCompound) nbtElement;
            result.add(new ModPotionEffect().unmarshal(effectNbt));
        }

        return result;
    }

    public ModPotionData setEffects(Collection<ModPotionEffect> effects) {
        NbtCompound nbt = getNbt();
        NbtList effectList = new NbtList();

        for (ModPotionEffect effect : effects) {
            effectList.add(effect.marshal());
        }

        nbt.put("effects", effectList);
        return this;
    }

    public ModPotionData setEffects(ModPotionEffect... effects) {

        ArrayList<ModPotionEffect> result = new ArrayList<>(Arrays.asList(effects));

        setEffects(result);

        return this;
    }

    public int findEffectIndex(StatusEffect effect) {
        ArrayList<ModPotionEffect> effects = getEffects();
        int size = effects.size();

        for (int i = 0; i < size; i++) {
            ModPotionEffect targetEffect = effects.get(i);
            if (targetEffect.getEffect().equals(effect)) {
                return i;
            }
        }

        return -1;
    }

    public ModPotionEffect findEffect(StatusEffect effect) {
        int index = findEffectIndex(effect);
        if (index < 0) {
            return null;
        }

        return getEffects().get(index);
    }

    public void removeEffect(int index) {
        if (index < 0) {
            return;
        }

        ArrayList<ModPotionEffect> effects = getEffects();
        effects.remove(index);
        setEffects(effects);
    }

    public void updateEffect(ModPotionEffect effect) {
        int effectIdx = findEffectIndex(effect.getEffect());
        if (effectIdx < 0) {
            return;
        }

        removeEffect(effectIdx);
        addEffect(effect);
    }

    public StatusEffectCategory targetedAt() {
        NbtCompound nbt = getNbt();

        if (!nbt.contains("targetedAt")) {
            return null;
        }


        int targetedAt = nbt.getInt("targetedAt");

        return switch (targetedAt) {
            case 0 -> StatusEffectCategory.BENEFICIAL;
            case 1 -> StatusEffectCategory.HARMFUL;
            case 2 -> StatusEffectCategory.NEUTRAL;
            default -> null;
        };
    }

    public ModPotionData setTargetedAt(StatusEffectCategory category) {
        if (category == null) {
            return this;
        }

        NbtCompound nbt = getNbt();
        nbt.putInt("targetedAt", category.ordinal());

        return this;
    }

    // 获取默认的amplifier
    public int getAmplifier() {
        NbtCompound nbt = getNbt();
        return nbt.getInt("amplifier");
    }

    // 设置`默认`的amplifier
    public ModPotionData setAmplifier(int amplifier) {
        NbtCompound nbt = getNbt();
        nbt.putInt("amplifier", amplifier);
        return this;
    }

    // 获取默认的duration
    public int getDuration() {
        NbtCompound nbt = getNbt();
        return nbt.getInt("duration");
    }

    // 设置默认的duration
    public ModPotionData setDuration(int duration) {
        NbtCompound nbt = getNbt();
        nbt.putInt("duration", duration);

        return this;
    }

    public ModPotionData addRevisionCount() {
        NbtCompound nbt = getNbt();
        nbt.putInt("revisionCount", getRevisionCount() + 1);
        return this;
    }

    public int getRevisionCount() {
        NbtCompound nbt = getNbt();
        return nbt.getInt("revisionCount");
    }

    public NbtCompound getNbt() {
        NbtCompound nbt = getRawNbt();

        return nbt.getCompound(POTION_TAG_KEY);
    }

    public NbtCompound getRawNbt() {
        return this.stack.getOrCreateNbt();
    }
}
