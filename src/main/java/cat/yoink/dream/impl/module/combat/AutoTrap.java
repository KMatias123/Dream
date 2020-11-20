package cat.yoink.dream.impl.module.combat;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoTrap extends Module
{
    private final Setting blocksPerTick = new Setting("BPT", this, 1, 1, 10);

    private final Setting disable = new Setting("Disable", this, true);

    private final List<Vec3d> positions = new ArrayList<>(Arrays.asList(
            new Vec3d(0, -1, -1),
            new Vec3d(1, -1, 0),
            new Vec3d(0, -1, 1),
            new Vec3d(-1, -1, 0),
            new Vec3d(0, 0, -1),
            new Vec3d(1, 0, 0),
            new Vec3d(0, 0, 1),
            new Vec3d(-1, 0, 0),
            new Vec3d(0, 1, -1),
            new Vec3d(1, 1, 0),
            new Vec3d(0, 1, 1),
            new Vec3d(-1, 1, 0),
            new Vec3d(0, 2, -1),
            new Vec3d(0, 2, 1),
            new Vec3d(0, 2, 0)
    ));

    private boolean finished;

    public AutoTrap(final String name, final String description, final Category category)
    {
        super(name, description, category);

        addSetting(blocksPerTick);
        addSetting(disable);
    }

    @Override
    public void onEnable()
    {
        finished = false;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event)
    {
        if (nullCheck()) return;

        if (finished && disable.getBooleanValue()) disable();

        int blocksPlaced = 0;

        for (final Vec3d position : positions)
        {
            final EntityPlayer closestPlayer = getClosestPlayer();
            if (closestPlayer != null)
            {
                final BlockPos pos = new BlockPos(position.add(getClosestPlayer().getPositionVector()));

                if (mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR))
                {
                    final int oldSlot = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = PlayerUtil.getSlot(Blocks.OBSIDIAN);
                    PlayerUtil.placeBlock(pos);
                    mc.player.inventory.currentItem = oldSlot;
                    blocksPlaced++;

                    if (blocksPlaced == blocksPerTick.getIntegerValue()) return;
                }
            }
        }
        if (blocksPlaced == 0) finished = true;
    }

    private EntityPlayer getClosestPlayer()
    {
        EntityPlayer closestPlayer = null;
        double range = 1000;
        for (final EntityPlayer playerEntity : mc.world.playerEntities)
        {
            if (!playerEntity.equals(mc.player))
            {
                final double distance = mc.player.getDistance(playerEntity);
                if (distance < range)
                {
                    closestPlayer = playerEntity;
                    range = distance;
                }
            }
        }
        return closestPlayer;
    }
}
