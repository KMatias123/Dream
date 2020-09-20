package cat.yoink.dream.mixin.mixins.mixin;

import cat.yoink.dream.impl.event.MoveEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author yoink
 * @since 9/20/2020
 */
@Mixin(value = EntityPlayerSP.class)
public class EntityPlayerSPMixin extends AbstractClientPlayer
{
	public EntityPlayerSPMixin(World p_i47378_2_, NetHandlerPlayClient p_i47378_3_)
	{
		super(p_i47378_2_, p_i47378_3_.getGameProfile());
	}

	@Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
	public void move(final AbstractClientPlayer player, final MoverType moverType, final double x, final double y, final double z)
	{
		MoveEvent event = new MoveEvent(moverType, x, y, z);
		MinecraftForge.EVENT_BUS.post(event);
		if (!event.isCanceled())
		{
			super.move(event.getType(), event.getX(), event.getY(), event.getZ());
		}
	}
}
