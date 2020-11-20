package cat.yoink.dream.impl.module.combat;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.impl.event.PacketSendEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class Criticals extends Module
{
	public Criticals(final String name, final String description, final Category category)
	{
		super(name, description, category);
	}

	@SubscribeEvent
	public void onPacketSend(final PacketSendEvent event)
	{
		if (nullCheck()) return;

		if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround)
		{
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ, false));
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
		}
	}
}
