package cn.kzhik.milkbottle.utils.potion;

import cn.kzhik.milkbottle.item.ModItems;
import cn.kzhik.milkbottle.resources.data.medicineStove.MedicineStoveDataPack;
import cn.kzhik.milkbottle.utils.potion.modifier.ModPotionModifier;
import cn.kzhik.milkbottle.utils.potion.modifier.PotionTypeModifier;
import cn.kzhik.milkbottle.utils.potion.modifier.TargetedAtModifier;
import cn.kzhik.milkbottle.utils.potion.postProcessor.ModPotionPostProcessor;
import net.minecraft.entity.effect.StatusEffectCategory;
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
    private final Stack<ItemStack> mainMaterials = new Stack<>();
    private final ArrayList<Item> ancillaryMaterials = new ArrayList<>();
    private final Queue<ItemStack> bufferQueue = new LinkedList<>();
    private final Queue<ItemStack> resultQueue = new LinkedList<>();
    // 辅助材料映射表
    private final HashMap<Item, ModPotionModifier> ancillaryMaterialMap = new HashMap<>();
    private final HashSet<ModPotionModifier> globalModifier = new HashSet<>();
    private final HashSet<ModPotionPostProcessor> postProcessSet = new HashSet<>();
    private final int maxCount;

    public ModPotionConverter(int maxCount) {
        this.maxCount = maxCount;

        // 读取dataPack内定义的ModPotionModifier并加入材料修改器表和全局修改器列表
        this.ancillaryMaterialMap.putAll(MedicineStoveDataPack.qualifiedModifiers);
        this.globalModifier.addAll(MedicineStoveDataPack.modifiers);

        // 净化药水的材料
        addQualifiedModifier(Items.GOLDEN_APPLE, new TargetedAtModifier(StatusEffectCategory.HARMFUL));
        addGlobalModifier(new PotionTypeModifier()); // 药水类型修改器
    }

    public static boolean isMainMaterial(ItemStack item) {
        return item.getItem() == ModItems.MILK_BOTTLE || item.getItem() == ModItems.ANTIDOTE || item.getItem() == ModItems.VACCINE;
    }

    public boolean canAddMaterial(ItemStack item) {
        if (item == null) {
            return false;
        }

        if (item.isEmpty() || item.getItem() == Items.AIR) {
            return false;
        }

        if (isMainMaterial(item)) {
            // 主材料数量大于 this.maxCount时不允许添加
            return mainMaterials.size() < this.maxCount;
        }

        // 不允许未知的材料和已经添加过的材料添加进列表
        return ancillaryMaterialMap.containsKey(item.getItem()) && !ancillaryMaterials.contains(item.getItem());
    }

    public synchronized boolean addMaterial(ItemStack item) {
        if (!canAddMaterial(item)) {
            return false;
        }

        if (isMainMaterial(item)) {

            // @BUG      材料压入栈内后再特定的条件下物品id会变成 minecraft:air，导致吞药水的情况
            // @CAUSE    如果外部对这个ItemStack进行了任意操作 那么就会影响到已经压入栈内的ItemStack
            // @SOLUTION 将ItemStack的拷贝压入栈内
            mainMaterials.add(item.copy());
        } else {
            ancillaryMaterials.add(item.getItem());
        }

        return true;
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

    public void addPostProcessor(ModPotionPostProcessor processor) {
        this.postProcessSet.add(processor);
    }

    public void addQualifiedModifier(Item material, ModPotionModifier modifier) {
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