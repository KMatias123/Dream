package cat.yoink.dream.impl.module.movement;

import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.setting.SettingType;
import cat.yoink.dream.api.util.MoveUtil;
import cat.yoink.dream.impl.event.MoveEvent;
import cat.yoink.dream.impl.event.WalkEvent;
import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;
import cat.yoink.dream.mixin.mixins.accessor.ITimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 9/21/2020
 */
public class Strafe extends Module
{
	private final Setting speed = new Setting.Builder(SettingType.INTEGER)
			.setName("Speed")
			.setModule(this)
			.setIntegerValue(9)
			.setMinIntegerValue(1)
			.setMaxIntegerValue(100)
			.build();

	private final Setting useTimer = new Setting.Builder(SettingType.BOOLEAN)
			.setName("UseTimer")
			.setModule(this)
			.setBooleanValue(false)
			.build();

	private final Setting timerSpeed = new Setting.Builder(SettingType.INTEGER)
			.setName("TimerSpeed")
			.setModule(this)
			.setIntegerValue(7)
			.setMinIntegerValue(1)
			.setMaxIntegerValue(20)
			.build();
	
	private int currentStage;
	private double currentSpeed;
	private double distance;
	private int cooldown;
	
	public Strafe(String name, String description, Category category)
	{
		super(name, description, category);

		addSetting(speed);
		addSetting(useTimer);
		addSetting(timerSpeed);
	}

	public void onEnable()
	{
		currentSpeed = MoveUtil.vanillaSpeed();
		
		if (!mc.player.onGround) currentStage = 3;
	}

	@Override
	public void onDisable()
	{
		currentSpeed = 0.0;
		currentStage = 2;

		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (useTimer.getBooleanValue()) ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f / ((timerSpeed.getIntegerValue() + 100) / 100f));
		else ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}

	@SubscribeEvent
	public void onUpdateWalkingPlayer(WalkEvent event)
	{
		distance = Math.sqrt((mc.player.posX - mc.player.prevPosX) * (mc.player.posX - mc.player.prevPosX) + (mc.player.posZ - mc.player.prevPosZ) * (mc.player.posZ - mc.player.prevPosZ));
	}

	@SubscribeEvent
	public void onMove(MoveEvent event)
	{
		float forward = mc.player.movementInput.moveForward;
		float strafe = mc.player.movementInput.moveStrafe;
		float yaw = mc.player.rotationYaw;
		
		if (currentStage == 1 && MoveUtil.isMoving())
		{
			currentStage = 2;
			currentSpeed = 1.18f * MoveUtil.vanillaSpeed() - 0.01;
		}
		else if (currentStage == 2)
		{
			currentStage = 3;
			
			if (MoveUtil.isMoving())
			{
				event.setY(mc.player.motionY = 0.4);
				if (cooldown > 0) --cooldown;
				currentSpeed *= speed.getIntegerValue() / 5f;
			}
		}
		else if (currentStage == 3)
		{
			currentStage = 4;
			currentSpeed = distance - (0.66 * (distance - MoveUtil.vanillaSpeed()));
		}
		else
		{
			if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, mc.player.motionY, 0.0)).size() > 0 || mc.player.collidedVertically) currentStage = 1;
			currentSpeed = distance - distance / 159.0;
		}

		currentSpeed = Math.max(currentSpeed, MoveUtil.vanillaSpeed());

		if (forward == 0.0f && strafe == 0.0f)
		{
			event.setX(0.0);
			event.setZ(0.0);

			currentSpeed = 0.0;
		}
		else if (forward != 0.0f)
		{
			if (strafe >= 1.0f)
			{
				yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
				strafe = 0.0f;
			}
			else
			{
				if (strafe <= -1.0f)
				{
					yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
					strafe = 0.0f;
				}
			}
			if (forward > 0.0f) forward = 1.0f;
			else if (forward < 0.0f) forward = -1.0f;
		}

		double motionX = Math.cos(Math.toRadians(yaw + 90.0f));
		double motionZ = Math.sin(Math.toRadians(yaw + 90.0f));

		if (cooldown == 0)
		{
			event.setX(forward * currentSpeed * motionX + strafe * currentSpeed * motionZ);
			event.setZ(forward * currentSpeed * motionZ - strafe * currentSpeed * motionX);
		}

		if (forward == 0.0f && strafe == 0.0f)
		{
			event.setX(0.0);
			event.setZ(0.0);
		}
	}
}