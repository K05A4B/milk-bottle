package cn.kzhik.milkbottle.utils.potion;

import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.modifier.*;
import cn.kzhik.milkbottle.utils.potion.postProcessor.ModPotionPostProcessor;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.*;

public class ModPotionConverter {
    private static final int durationDelta = Constants.DEFAULT_DURATION;
    private final Stack<ItemStack> mainMaterials = new Stack<>();
    private final HashSet<Item> ancillaryMaterials = new HashSet<>();
    private final Queue<ItemStack> bufferQueue = new LinkedList<>();
    private final Queue<ItemStack> resultQueue = new LinkedList<>();
    // 辅助材料映射表
    private final HashMap<Item, ModPotionModifier> ancillaryMaterialMap = new HashMap<>();
    private final HashSet<ModPotionModifier> globalModifier = new HashSet<>();
    private final HashSet<ModPotionPostProcessor> postProcessSet = new HashSet<>();
    private int maxCount = 0;

    public ModPotionConverter(int maxCount) {
        this.maxCount = maxCount;

        // 登记效果材料
        registerAncillaryMaterial(Items.GOLDEN_APPLE, new TargetedAtModifier(StatusEffectCategory.HARMFUL));
        registerAncillaryMaterial(Items.FERMENTED_SPIDER_EYE, EffectModifier.build(StatusEffects.BLINDNESS));
        registerAncillaryMaterial(Items.ECHO_SHARD, EffectModifier.build(StatusEffects.DARKNESS));
        registerAncillaryMaterial(Items.ROTTEN_FLESH, EffectModifier.build(StatusEffects.HUNGER));
        registerAncillaryMaterial(Items.SHULKER_SHELL, EffectModifier.build(StatusEffects.LEVITATION));
        registerAncillaryMaterial(Items.PRISMARINE_SHARD, EffectModifier.build(StatusEffects.MINING_FATIGUE));
        registerAncillaryMaterial(Items.PUFFERFISH, EffectModifier.build(StatusEffects.NAUSEA));
        registerAncillaryMaterial(Items.SPIDER_EYE, EffectModifier.build(StatusEffects.POISON));
        registerAncillaryMaterial(Items.SOUL_SAND, EffectModifier.build(StatusEffects.SLOWNESS));
        registerAncillaryMaterial(Items.WITHER_ROSE, EffectModifier.build(StatusEffects.WITHER));
        registerAncillaryMaterial(Items.BONE_MEAL, EffectModifier.build(StatusEffects.WEAKNESS));

        // 红石能延长效果时间
        registerAncillaryMaterial(Items.REDSTONE, new DurationExtenderModifier());
        // 钻石能将解药变成疫苗
        registerAncillaryMaterial(Items.DIAMOND, new UpgradePotionModifier());

        addGlobalModifier(new PotionTypeModifier());
    }

    public static boolean isMainMaterial(ItemStack item) {
        return item.getItem() == ModItems.MILK_BOTTLE || item.getItem() == ModItems.ANTIDOTE || item.getItem() == ModItems.VACCINE;
    }

    public synchronized boolean addMaterial(ItemStack item) {
        if (item == null) {
            return false;
        }

        if (item.isEmpty() || item.getItem() == Items.AIR) {
            return false;
        }

        if (isMainMaterial(item)) {
            if (mainMaterials.size() >= this.maxCount) {
                return false;
            }

            // @BUG      材料压入栈内后再特定的条件下物品id会变成 minecraft:air，导致吞药水的情况
            // @CAUSE    如果外部对这个ItemStack进行了任意操作 那么就会影响到已经压入栈内的ItemStack
            // @SOLUTION 将ItemStack的拷贝压入栈内
            mainMaterials.add(item.copy());
            return true;
        }

        if (ancillaryMaterialMap.containsKey(item.getItem()) && !ancillaryMaterials.contains(item.getItem())) {
            ancillaryMaterials.add(item.getItem());
            return true;
        }

        return false;
    }

    public void converted() {
        int stackSize = mainMaterials.size();

        for (int i = 0; i < stackSize; i++) {
            ItemStack elem = mainMaterials.pop();
            ItemStack targetMaterial = elem.copy();

            if (targetMaterial == null) {
                continue;
            }

            ModPotionData potionData = new ModPotionData(targetMaterial);

            for (ModPotionModifier modifier : globalModifier) {
                ModPotionData data = modifier.modify(potionData);
                if (data != null) {
                    potionData = data;
                }
            }

            for (Item material : ancillaryMaterials) {
                if (!ancillaryMaterialMap.containsKey(material)) {
                    continue;
                }

                ModPotionModifier modifier = ancillaryMaterialMap.get(material);
                ModPotionData data = modifier.modify(potionData);
                if (data != null) {
                    potionData = data;
                }
            }

            potionData.addRevisionCount();
            bufferQueue.offer(potionData.stack().copy());
        }

        // 后处理
        Queue<ItemStack> buffer = new LinkedList<>(bufferQueue);
        for (ModPotionPostProcessor processor : postProcessSet) {
            buffer = processor.process(buffer, new HashSet<>(ancillaryMaterials));
        }

        resultQueue.addAll(buffer);
        resetAllMaterialList();
        bufferQueue.clear();
    }

    public ItemStack poll() {
        return resultQueue.poll();
    }

    public void resetAllMaterialList() {
        this.ancillaryMaterials.clear();
        this.mainMaterials.clear();
    }

    public NbtCompound marshal() {
        NbtCompound nbt = new NbtCompound();

        nbt.put("mainMaterials", marshalItemStack(mainMaterials));
        nbt.put("resultQueue", marshalItemStack(resultQueue));
        nbt.put("ancillaryMaterials", marshalItem(ancillaryMaterials));

        return nbt;
    }

    public void unmarshal(NbtCompound nbt) {
        if (nbt != null) {
            unmarshalItemStack((NbtList) nbt.get("mainMaterials"), mainMaterials);
            unmarshalItemStack((NbtList) nbt.get("resultQueue"), resultQueue);
            unmarshalItem((NbtList) nbt.get("ancillaryMaterials"), ancillaryMaterials);
        }
    }

    public Stack<ItemStack> getMainMaterials() {
        return mainMaterials;
    }

    public HashSet<Item> getAncillaryMaterials() {
        return new HashSet<>(ancillaryMaterials);
    }

    public Queue<ItemStack> getResultQueue() {
        return new LinkedList<>(resultQueue);
    }

    public void addGlobalModifier(ModPotionModifier modifier) {
        this.globalModifier.add(modifier);
    }

    public void registerAncillaryMaterial(Item material, ModPotionModifier modifier) {
        this.ancillaryMaterialMap.put(material, modifier);
    }

    private NbtList marshalItemStack(Collection<ItemStack> collection) {
        NbtList list = new NbtList();
        for (ItemStack elem : collection) {

            NbtCompound item = new NbtCompound();
            item.putString("id", Registries.ITEM.getId(elem.getItem()).toString());
            item.put("nbt", elem.getNbt());
            list.add(item);
        }

        return list;
    }

    private NbtList marshalItem(Collection<Item> collection) {
        NbtList list = new NbtList();

        for (Item elem : collection) {
            list.add(NbtString.of(Registries.ITEM.getId(elem).toString()));
        }

        return list;
    }

    private void unmarshalItem(NbtList list, Collection<Item> collection) {
        collection.clear();
        for (NbtElement elem : list) {
            String id = elem.asString();
            collection.add(Registries.ITEM.get(new Identifier(id)));
        }
    }

    private void unmarshalItemStack(NbtList list, Collection<ItemStack> collection) {
        for (NbtElement elem : list) {
            NbtCompound nbt = (NbtCompound) elem;
            ItemStack item = new ItemStack(Registries.ITEM.get(new Identifier(nbt.getString("id"))));
            item.setNbt(nbt.getCompound("nbt"));
            collection.add(item);
        }
    }
}
