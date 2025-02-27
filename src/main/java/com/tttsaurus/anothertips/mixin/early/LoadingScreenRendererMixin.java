package com.tttsaurus.anothertips.mixin.early;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tttsaurus.anothertips.core.TipsManager;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LoadingScreenRenderer.class)
public class LoadingScreenRendererMixin
{
    @WrapOperation(
            method = "setLoadingProgress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I",
                    ordinal = 0
            ))
    public int mixin_setLoadingProgress_FontRenderer$drawStringWithShadow(FontRenderer instance, String text, float x, float y, int color, Operation<Integer> original)
    {
        if (TipsManager.getActive())
            TipsManager.drawTips();

        return original.call(instance, text, x, y, color);
    }
}
