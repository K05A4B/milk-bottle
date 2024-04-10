package cn.kzhik.milkbottle.utils.block;

import cn.kzhik.milkbottle.block.entity.MedicineStoveEntity;
import cn.kzhik.milkbottle.utils.Constants;
import cn.kzhik.milkbottle.utils.potion.ModPotionConverter;
import cn.kzhik.milkbottle.utils.tick.TickProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MedicineStoveTickProcessor implements TickProcessor {

    public int waitingTick = Constants.MEDICINE_STOVE_DELAY;
    ModPotionConverter converter;
    MedicineStoveEntity entity;

    public MedicineStoveTickProcessor(ModPotionConverter converter, MedicineStoveEntity entity) {
        this.converter = converter;
        this.entity = entity;
    }

    public MedicineStoveTickProcessor(ModPotionConverter converter, MedicineStoveEntity entity, int waitingTick) {
        this(converter, entity);
        this.waitingTick = waitingTick;
    }

    @Override
    public void process(World world, BlockPos pos, BlockState state, BlockEntity entity) {
        if (waitingTick % 100 == 0 || waitingTick == 100) {
            if (world != null) {
                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }

        if (waitingTick <= 0) {
            converter.converted();
            this.entity.setWorkState(MedicineStoveEntity.WorkState.RELEASE);
            this.entity.markDirty(); // 完成后更新nbt数据

            if (world != null) {
                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }

        }

        waitingTick--;
    }

    @Override
    public boolean processEnd() {
        return waitingTick < 0;
    }
}
