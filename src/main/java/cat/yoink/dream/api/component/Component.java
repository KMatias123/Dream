package cat.yoink.dream.api.component;

import net.minecraft.client.Minecraft;

public class Component
{
    protected final Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean showing;

    public Component(final String name)
    {
        this.name = name;
    }

    public void render()
    {
    }

    public String getName()
    {
        return name;
    }

    public int getX()
    {
        return x;
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public int getW()
    {
        return w;
    }

    public void setW(final int w)
    {
        this.w = w;
    }

    public int getH()
    {
        return h;
    }

    public void setH(final int h)
    {
        this.h = h;
    }

    public boolean isShowing()
    {
        return showing;
    }

    public void setShowing(final boolean showing)
    {
        this.showing = showing;
    }
}
