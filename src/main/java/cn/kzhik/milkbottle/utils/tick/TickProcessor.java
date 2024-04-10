package cn.kzhik.milkbottle.utils.tick;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface TickProcessor {
    default String getProcessorName() {
        return "TickProcessor";
    }

    void process(World world, BlockPos pos, BlockState state, BlockEntity entity);

    boolean processEnd();
}
