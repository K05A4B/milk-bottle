package cn.kzhik.milkbottle.utils.potion;

import cn.kzhik.milkbottle.utils.Constants;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModPotionEffect {
    private int amplifier = 0;
    private int duration = Constants.DEFAULT_DURATION;
    private StatusEffect effect = null;

    public ModPotionEffect() {
    }

    public ModPotionEffect(int duration) {
        this.duration = duration;
    }

    public ModPotionEffect(int duration, int amplifier) {
        this(duration);
        this.amplifier = amplifier;
    }

    public ModPotionEffect(int duration, int amplifier, StatusEffect effect) {
        this(duration, amplifier);
        this.effect = effect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public ModPotionEffect setAmplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public ModPotionEffect setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public StatusEffect getEffect() {
        return this.effect;
    }

    public ModPotionEffect setEffect(StatusEffect effect) {
        this.effect = effect;

        return this;
    }

    public NbtCompound marshal() {
        NbtCompound nbt = new NbtCompound();

        nbt.putInt("duration", getDuration());
        nbt.putInt("amplifier", getAmplifier());

        Identifier effectId = Registries.STATUS_EFFECT.getId(getEffect());

        if (effectId != null) {
            nbt.putString("effect", effectId.toString());
        }

        return nbt;
    }

    public ModPotionEffect unmarshal(NbtCompound nbt) {
        setAmplifier(nbt.getInt("amplifier"));
        setDuration(nbt.getInt("duration"));

        if (nbt.contains("effect")) {
            setEffect(Registries.STATUS_EFFECT.get(new Identifier(nbt.getString("effect"))));
        }

        return this;
    }
}
