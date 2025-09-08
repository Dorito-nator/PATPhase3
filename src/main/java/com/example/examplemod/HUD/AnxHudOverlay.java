package com.example.examplemod.HUD;

import com.example.examplemod.AnxietyMod;
import com.example.examplemod.events.ModEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayer;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;

public class AnxHudOverlay{

    public static final ResourceLocation anxFull = ResourceLocation.fromNamespaceAndPath(AnxietyMod.MODID
            , "textures/anxietyhud/fullanx.png");

    public static final ResourceLocation anxEmpty = ResourceLocation.fromNamespaceAndPath(AnxietyMod.MODID
            , "textures/anxietyhud/emptyanx.png");

    public static int anxlvl  = ModEvents.ForgeEvents.anxLvl;

    public static void initOverlays(AddGuiOverlayLayersEvent event) {
        ForgeLayeredDraw DrawStack = new ForgeLayeredDraw(ResourceLocation.fromNamespaceAndPath(AnxietyMod.MODID, "anxiety_root"));
        DrawStack
                .add(anxFull, anxOverlay)
                .add(anxEmpty, anxOverlay);

        event.getLayeredDraw().add(DrawStack.getName(), DrawStack, () -> true);
        event.getLayeredDraw().move(DrawStack.getName(), ForgeLayeredDraw.SLEEP_OVERLAY, ForgeLayeredDraw.LayerOffset.ABOVE);

    }

    public static final ForgeLayer anxOverlay = (guiGraphics, partialTick) -> {

        int x = guiGraphics.guiHeight();
        int y = guiGraphics.guiWidth() /2;

        for(int i = 0; i < 10; i++){
            guiGraphics.blit(anxEmpty,x - 94 + (i * 9), y - 54,0,0,12,12,
                    12,12);
        }

        for(int i = 0; i < 10; i++) {
            if( anxlvl > i) {
                guiGraphics.blit(anxFull,x - 94 + (i * 9),y - 54,0,0,12,12,
                        12,12);
            } else {
                break;
            }
        }

    };
}
