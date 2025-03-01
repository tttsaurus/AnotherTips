package com.tttsaurus.anothertips.core;

import com.tttsaurus.anothertips.AnotherTipsConfig;
import com.tttsaurus.anothertips.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.time.StopWatch;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class TipsManager
{
    private static boolean active = false;
    private static boolean listenIngame = false;

    private static final Random random = new Random();
    private static final StopWatch stopWatch = new StopWatch();
    private static boolean roll = true;
    private static int lastTipIndex = -1;
    private static final List<String> currentTip = new ArrayList<>();
    private static float currentWaitTime;
    private static String currentLang = "";

    //<editor-fold desc="getters & setters">
    public static boolean getActive() { return active; }
    public static void setActive(boolean flag) { active = flag; }

    public static void setListenIngame(boolean flag) { listenIngame = flag; }

    public static void resetTimer()
    {
        if (stopWatch.isStarted())
        {
            stopWatch.stop();
            stopWatch.reset();
        }
    }
    //</editor-fold>

    private static void nextTip()
    {
        currentLang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        int length = AnotherTipsConfig.TIP_LANG_KEYS.size();
        String rawTip;

        if (length == 0)
            rawTip = "";
        else if (length == 1)
            rawTip = AnotherTipsConfig.TIP_LANG_KEYS.get(0);
        else
        {
            if (lastTipIndex == -1)
            {
                int index = random.nextInt(length);
                rawTip = AnotherTipsConfig.TIP_LANG_KEYS.get(index);
                lastTipIndex = index;
            }
            else
            {
                int index = random.nextInt(length);
                index = index == lastTipIndex ? index + 1 : index;
                index = index >= length ? 0 : index;
                rawTip = AnotherTipsConfig.TIP_LANG_KEYS.get(index);
                lastTipIndex = index;
            }
        }

        currentTip.clear();
        String[] sections = rawTip.split("<br>");
        currentTip.addAll(Arrays.asList(sections));

        int charCount = 0;
        for (int i = 0; i < currentTip.size(); i++)
        {
            String i18nTip = I18n.format(currentTip.get(i));
            charCount += i18nTip.length();
            currentTip.set(i, i18nTip);
        }

        if (!AnotherTipsConfig.TIP_WAIT_TIME.containsKey(currentLang))
            currentWaitTime = 1f;
        else
            currentWaitTime = AnotherTipsConfig.TIP_WAIT_TIME.get(currentLang) * charCount;
    }

    public static void drawTips()
    {
        if (roll)
        {
            roll = false;
            nextTip();
        }

        if (!stopWatch.isStarted())
            stopWatch.start();

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        //int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();

        int lineNum = currentTip.size();
        RenderUtils.renderText("Tips", 20, height - 20 - lineNum * 10, 1f, Color.YELLOW.getRGB(), true);
        for (int i = 0; i < lineNum; i++)
            RenderUtils.renderText(currentTip.get(i), 20, height - 20 + i * 10 - (lineNum - 1) * 10, 1f, Color.WHITE.getRGB(), true);

        if (stopWatch.getNanoTime() / 1E9d >= currentWaitTime)
        {
            resetTimer();
            stopWatch.start();
            roll = true;
        }
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
    {
        if (listenIngame)
        {
            listenIngame = false;
            active = false;
            resetTimer();
        }
    }
}
