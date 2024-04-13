package cn.kzhik.milkbottle.resources.data.medicineStove;

import cn.kzhik.milkbottle.utils.Mod;
import cn.kzhik.milkbottle.utils.potion.ModPotionData;
import cn.kzhik.milkbottle.utils.potion.modifier.EffectModifier;
import cn.kzhik.milkbottle.utils.potion.modifier.ModPotionModifier;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MedicineStoveReloadListener implements SimpleSynchronousResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return new Identifier(Mod.getModId(), "medicine_stove");
    }

    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> resources = manager.findResources("medicine_stove", id -> id.getPath().endsWith(".json"));

        for (Identifier resourceId : resources.keySet()) {
            Resource resource = resources.get(resourceId);
            Reader reader = null;

            try {
                reader = resource.getReader();
            } catch (IOException e) {
                Mod.LOGGER.warn("Failed to load resource \"{}\". Reason: {}", resourceId.toString(), e.getMessage());
                continue;
            }

            Gson gson = new Gson();
            DataPackStruct dataPack = gson.fromJson(reader, DataPackStruct.class);
            ArrayList<StatusEffect> effects = convertToStatusEffectArray(dataPack.effects);

            ModPotionModifier modifier = switch (dataPack.modifier) {
                case "effect_modifier" -> EffectModifier.build(effects);
                case "qualified_modifier", "global_modifier" -> getModifier(dataPack.entry);
                default -> null;
            };

            if (modifier == null) {
                Mod.LOGGER.warn("\"{}\" is not modifier", dataPack.modifier);
                continue;
            }

            if (Objects.equals(dataPack.modifier, "global_modifier")) {
                MedicineStoveDataPack.modifiers.add(modifier);
            } else {
                Item material = Registries.ITEM.get(new Identifier(dataPack.material));
                if (material == Items.AIR || dataPack.material == null) {
                    Mod.LOGGER.warn("Unknown item \"{}\", ignoring!", dataPack.material);
                    return;
                }

                MedicineStoveDataPack.qualifiedModifiers.put(material, modifier);
            }
        }


    }

    private ModPotionModifier getModifier(String entry) {
        ModPotionModifier modifier = null;

        try {
            Class<?> modifierClass = Class.forName(entry);
            modifierClass.getMethod("modify", ModPotionData.class);

            Object modifierObject = modifierClass.getDeclaredConstructor().newInstance();
            modifier = (ModPotionModifier) modifierObject;
        } catch (ClassNotFoundException error) {
            Mod.LOGGER.error("Failed to find modifier entry for {}! Reason: {}", entry, error.getMessage());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            Mod.LOGGER.error("Failed to instantiate modifier entry for {}! Reason: {}", entry, e.getMessage());
        }

        if (modifier == null) {
            Mod.LOGGER.warn("Modifier \"{}\" is null!", entry);
        }

        return modifier;
    }

    private ArrayList<StatusEffect> convertToStatusEffectArray(String[] effects) {
        ArrayList<StatusEffect> processedEffects = new ArrayList<>();

        if (effects == null) {
            return processedEffects;
        }

        for (String effectStr : effects) {
            StatusEffect effect = Registries.STATUS_EFFECT.get(new Identifier(effectStr));

            if (effect == null) {
                Mod.LOGGER.warn("Unknown effect \"{}\", ignoring!", effectStr);
                continue;
            }

            processedEffects.add(effect);
        }

        return processedEffects;
    }

    public static class DataPackStruct {
        // modifier:
        //  - effect_modifier: 这个modifier会在辅助材料列表内有指定材料时被调用, 可以为药水增加效果
        //  - qualified_modifier: 这个modifier允许您拥有指定材料时才调用您自定义的modifier, 同样需要您指定入口
        //  - global_modifier: 这个modifier可以对主材料进行修改，不需要指定材料, 但是需要提供你的modifier的入口
        // modifier 只需要是`cn.kzhik.milkbottle.utils.potion.modifier.ModPotionModifier`这个接口就行了
        public String modifier;

        // modifier的入口
        public String entry;
        public String material;
        public String[] effects;
    }
}
