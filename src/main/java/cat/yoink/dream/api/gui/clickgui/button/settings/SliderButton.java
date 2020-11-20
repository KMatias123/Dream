package cat.yoink.dream.api.gui.clickgui.button.settings;

import cat.yoink.dream.Client;
import cat.yoink.dream.api.gui.clickgui.button.SettingButton;
import cat.yoink.dream.api.module.Module;
import cat.yoink.dream.api.setting.Setting;
import cat.yoink.dream.api.setting.SettingManager;
import cat.yoink.dream.api.util.font.FontUtil;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class SliderButton extends SettingButton
{
	private final Setting setting;
	protected boolean dragging;
	protected int sliderWidth;

	SliderButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H)
	{
		super(module, X, Y, W, H);
		this.dragging = false;
		this.sliderWidth = 0;
		this.setting = setting;
	}

	protected void updateSlider(final int mouseX)
	{
	}

	@Override
	public void render(final int mX, final int mY)
	{
		updateSlider(mX);

		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			Client.clickGUI.drawGradient(getX() + (sliderWidth) + 6, getY(), getX() + getW(), getY() + getH(), new Color(220, 220, 220, 232).getRGB(), new Color(218, 218, 218, 232).getRGB());
			switch (SettingManager.INSTANCE.getSetting("ClickGUI", "Color").getEnumValue())
			{
				case "Red":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(210, 30, 30, 232).getRGB(), new Color(206, 30, 30, 232).getRGB());
					break;
				case "Green":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(20, 210, 20, 232).getRGB(), new Color(20, 206, 20, 232).getRGB());
					break;
				case "Blue":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(20, 20, 210, 232).getRGB(), new Color(20, 20, 206, 232).getRGB());
					break;
				default:
					break;
			}
		}
		else
		{
			Client.clickGUI.drawGradient(getX() + (sliderWidth) + 6, getY(), getX() + getW(), getY() + getH(), new Color(240, 240, 240, 232).getRGB(), new Color(238, 238, 238, 232).getRGB());
			switch (SettingManager.INSTANCE.getSetting("ClickGUI", "Color").getEnumValue())
			{
				case "Red":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(220, 30, 30, 232).getRGB(), new Color(216, 30, 30, 232).getRGB());
					break;
				case "Green":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(30, 220, 30, 232).getRGB(), new Color(30, 216, 30, 232).getRGB());
					break;
				case "Blue":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(30, 30, 220, 232).getRGB(), new Color(30, 30, 216, 232).getRGB());
					break;
				default:
					break;
			}
		}


		FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
		FontUtil.drawStringWithShadow(String.valueOf(setting.getIntegerValue()), (float) ((getX() + getW() - 6) - FontUtil.getStringWidth(String.valueOf(setting.getIntegerValue()))), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
	}

	public void mouseDown(final int mX, final int mY, final int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			dragging = true;
		}
	}

	public void mouseUp(final int mouseX, final int mouseY)
	{
		dragging = false;
	}

	public void close()
	{
		dragging = false;
	}

	public static class IntSlider extends SliderButton
	{
		private final Setting intSetting;

		public IntSlider(final Module module, final Setting setting, final int X, final int Y, final int W, final int H)
		{
			super(module, setting, X, Y, W, H);
			intSetting = setting;
		}

		@Override
		protected void updateSlider(final int mouseX)
		{
			final double diff = Math.min(getW(), Math.max(0, mouseX - getX()));
			final double min = intSetting.getMinIntegerValue();
			final double max = intSetting.getMaxIntegerValue();
			sliderWidth = (int) ((getW() - 6) * (intSetting.getIntegerValue() - min) / (max - min));
			if (dragging)
			{
				if (diff == 0.0)
				{
					intSetting.setIntegerValue(intSetting.getIntegerValue());
				}
				else
				{
					final DecimalFormat format = new DecimalFormat("##");
					final String newValue = format.format(diff / getW() * (max - min) + min);
					intSetting.setIntegerValue(Integer.parseInt(newValue));
				}
			}
		}
	}
}
