package com.tttsaurus.anothertips.mixin.early;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tttsaurus.anothertips.core.TipsManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiDownloadTerrain.class)
public class GuiDownloadTerrainMixin
{
    @WrapOperation(
            method = "drawScreen",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiDownloadTerrain;drawCenteredString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V"
            ))
    public void mixin_drawScreen_GuiDownloadTerrain$drawCenteredString(GuiDownloadTerrain instance, FontRenderer fontRenderer, String s, int i0, int i1, int i2, Operation<Void> original)
    {
        if (TipsManager.getActive())
            TipsManager.drawTips();

        original.call(instance, fontRenderer, s, i0, i1, i2);
    }
}
