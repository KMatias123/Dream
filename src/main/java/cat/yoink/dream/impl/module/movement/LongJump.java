package cat.yoink.dream.impl.module.movement;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.util.PlayerUtil;
import cat.yoink.dream.impl.event.MoveEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class LongJump extends Module
{
	private final Setting speed = new Setting("Speed", this, 30, 1, 100);

	private final Setting packet = new Setting("Packet", this, false);

	private boolean jumped = false;
	private boolean boostable = false;

	public LongJump(final String name, final String description, final Category category)
	{
		super(name, description, category);

		addSetting(speed);
		addSetting(packet);
	}

	@SubscribeEvent
	public void onTick(final TickEvent.ClientTickEvent event)
	{
		if (nullCheck()) return;

		if (jumped)
		{
			if (mc.player.onGround || mc.player.capabilities.isFlying)
			{
				jumped = false;

				mc.player.motionX = 0.0;
				mc.player.motionZ = 0.0;

				if (packet.getBooleanValue())
				{
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.onGround));
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, 0.0, mc.player.posZ + mc.player.motionZ, mc.player.onGround));
				}

				return;
			}

			if (!(mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f)) return;
			final double yaw = PlayerUtil.getDirection();
			mc.player.motionX = -Math.sin(yaw) * (((float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ)) * (boostable ? (speed.getIntegerValue() / 10f) : 1f));
			mc.player.motionZ = Math.cos(yaw) * (((float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ)) * (boostable ? (speed.getIntegerValue() / 10f) : 1f));

			boostable = false;
		}
	}

	@SubscribeEvent
	public void onMove(final MoveEvent event)
	{
		if (nullCheck()) return;

		if (!(mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f) && jumped)
		{
			mc.player.motionX = 0.0;
			mc.player.motionZ = 0.0;
			event.setX(0);
			event.setY(0);
		}
	}

	@SubscribeEvent
	public void onJump(final LivingEvent.LivingJumpEvent event)
	{
		if ((mc.player != null && mc.world != null) && event.getEntity() == mc.player && (mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f))
		{
			jumped = true;
			boostable = true;
		}
	}
}
