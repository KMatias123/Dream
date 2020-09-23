package cat.yoink.dream.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;

import java.util.Objects;

public class PlayerUtil
{
	public static double vanillaSpeed()
	{
		double baseSpeed = 0.272;
		if (Minecraft.getMinecraft().player.isPotionActive(MobEffects.SPEED))
		{
			final int amplifier = Objects.requireNonNull(Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * amplifier;
		}
		return baseSpeed;
	}

	public static boolean isMoving()
	{
		return Minecraft.getMinecraft().player.moveForward != 0.0 || Minecraft.getMinecraft().player.moveStrafing != 0.0;
	}
}
