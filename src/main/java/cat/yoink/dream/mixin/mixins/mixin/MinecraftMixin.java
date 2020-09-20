package cat.yoink.dream.mixin.mixins.mixin;

import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author yoink
 * @since 9/20/2020
 */
@Mixin(Minecraft.class)
public class MinecraftMixin implements IMinecraft
{
	@Shadow @Final private Timer timer;

	@Override
	public Timer getTimer()
	{
		return timer;
	}
}
