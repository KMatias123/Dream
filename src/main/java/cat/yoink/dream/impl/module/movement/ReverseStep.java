package cat.yoink.dream.impl.module.movement;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ReverseStep extends Module
{
    Setting holeOnly = new Setting("HoleOnly", this, true);

    public ReverseStep(final String name, final String description, final Category category)
    {
        super(name, description, category);

        addSetting(holeOnly);
    }

    @SubscribeEvent
    public void onTickClientTick(final TickEvent.ClientTickEvent event)
    {
        if (nullCheck() || mc.player.isInLava() || mc.player.isInWater()) return;

        if ((!holeOnly.getBooleanValue() && mc.player.onGround) || (holeOnly.getBooleanValue() && mc.player.onGround && fallingIntoHole()))
        {
            mc.player.motionY--;
        }
    }

    private boolean fallingIntoHole()
    {
        final Vec3d vec = new Vec3d(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * mc.getRenderPartialTicks(), mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * mc.getRenderPartialTicks(), mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * mc.getRenderPartialTicks());
        final BlockPos pos = new BlockPos(vec.x, vec.y - 1, vec.z);
        final BlockPos[] posList = {pos.north(), pos.south(), pos.east(), pos.west(), pos.down()};

        int blocks = 0;
        for (final BlockPos blockPos : posList)
        {
            final Block block = mc.world.getBlockState(blockPos).getBlock();
            if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) blocks++;
        }
        return blocks == 5;
    }
}
