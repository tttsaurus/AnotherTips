package com.tttsaurus.anothertips.mixin.early;

import com.tttsaurus.anothertips.core.TipsManager;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FMLClientHandler.class)
public class FMLClientHandlerMixin
{
    @Inject(method = "tryLoadExistingWorld", at = @At("HEAD"), remap = false)
    public void tryLoadExistingWorld(GuiWorldSelection selectWorldGUI, WorldSummary comparator, CallbackInfo ci)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
        {
            TipsManager.setListenChunkBuild(true);
            TipsManager.resetTimer();
            TipsManager.setActive(true);
        }
    }
}
