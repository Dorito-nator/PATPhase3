package com.example.examplemod.HUD;

import com.example.examplemod.AnxietyMod;
import com.example.examplemod.events.ModEvents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayer;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

public class AnxHudOverlay{

    public static final ResourceLocation anxFull = ResourceLocation.fromNamespaceAndPath(AnxietyMod.MODID
            , "gui/anxietyhud/fullanx");

    public static final ResourceLocation anxEmpty = ResourceLocation.fromNamespaceAndPath(AnxietyMod.MODID
            , "gui/anxietyhud/emptyanx");

    public static int anxlvl  = ModEvents.ForgeEvents.anxLvl;

    public static void init(BusGroup group) {
        AddGuiOverlayLayersEvent.getBus(group).addListener(AnxHudOverlay::initOverlays);
    }

    @SubscribeEvent
    public static void initOverlays(AddGuiOverlayLayersEvent event) {
        ForgeLayeredDraw DrawStack = new ForgeLayeredDraw(ResourceLocation.fromNamespaceAndPath(AnxietyMod.MODID, "anxiety_root"));
        DrawStack
                .add(anxEmpty, anxOverlay);

        event.getLayeredDraw().add(DrawStack.getName(), DrawStack, () -> true);
        event.getLayeredDraw().move(DrawStack.getName(), ForgeLayeredDraw.SLEEP_OVERLAY, ForgeLayeredDraw.LayerOffset.ABOVE);

    }

    public static final ForgeLayer anxOverlay = (guiGraphics, partialTick) -> {

        //guiGraphics.fill(10, 10, 50, 20, 0xFFFF0000);
        int y = guiGraphics.guiHeight();
        int x = guiGraphics.guiWidth() /2;


        for(int i = 0; i < 10; i++){
            guiGraphics.blit(anxEmpty,
                    x - 94 + (i * 9)
                    , y - 54,
                    0,
                    0,
                    16, 16,
                    16, 16);
        }

        for(int i = 0; i < 10; i++) {
            if( anxlvl > i) {
                guiGraphics.blit(anxFull,
                        x - 94 + (i * 9),
                        y - 54,
                        0,
                        0,
                        16,16,
                        16,16);
            } else {
                break;
            }
        }

    };
}
