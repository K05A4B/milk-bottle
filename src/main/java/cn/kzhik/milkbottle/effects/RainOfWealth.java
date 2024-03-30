package cn.kzhik.milkbottle.effects;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Random;

public class RainOfWealth extends StatusEffect {

    static String EFFECT_ID = "rain_of_wealth";
    Random random = new Random();
    Loot[] loots = {
            new Loot(Items.COAL, 30.0),
            new Loot(Items.GOLD_NUGGET, 30.0),
            new Loot(Items.IRON_NUGGET, 30.0),
            new Loot(Items.IRON_INGOT, 25.0),
            new Loot(Items.RAW_IRON, 25.0),
            new Loot(Items.GOLD_INGOT, 25.0),
            new Loot(Items.RAW_GOLD, 25.0),
            new Loot(Items.COAL_BLOCK, 20.0),
            new Loot(Items.DIAMOND, 15.0),
            new Loot(Items.IRON_BLOCK, 15.0),
            new Loot(Items.GOLD_BLOCK, 15.0),
            new Loot(Items.NETHERITE_SCRAP, 10),
            new Loot(Items.DIAMOND_BLOCK, 10),
            new Loot(Items.ANCIENT_DEBRIS, 10),
            new Loot(Items.NETHERITE_INGOT, 2.5),
            new Loot(Items.NETHERITE_BLOCK, 0.27)
    };

    public RainOfWealth() {
        super(StatusEffectCategory.BENEFICIAL, 0xffd700);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        double probability = 0.2 * (amplifier + 1);
        return random.nextDouble() < probability;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        double x = offsetPosition(entity.getX());
        double y = entity.getY() + 4.0f;
        double z = offsetPosition(entity.getZ());
        World world = entity.getWorld();

        Item item = selectRandomItem();

        ItemEntity itemEntity = new ItemEntity(world, x, y, z, new ItemStack(item));
        world.spawnEntity(itemEntity);

        itemEntity.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.0f);
    }

    private double offsetPosition(double position) {
        double offset = random.nextDouble(6.0f) - 3.0f;

        return position + offset;
    }

    private Item selectRandomItem() {
        Item selectedItem = null;
        double maxWeight = Double.MIN_VALUE;

        // 寻找出最终权重最大的战利品
        for (Loot loot : loots) {
            double weight = loot.weight * random.nextFloat();
            if (weight > maxWeight) {
                maxWeight = weight;
                selectedItem = loot.item;
            }
        }

        return selectedItem;
    }

    static class Loot {
        public Item item;
        public double weight;

        public Loot(Item item, double weight) {
            this.item = item;
            this.weight = weight;
        }
    }
}
