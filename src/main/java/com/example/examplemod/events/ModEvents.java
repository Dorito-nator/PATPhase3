package com.example.examplemod.events;
import com.example.examplemod.AnxietyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = AnxietyMod.MODID)
    public static class ForgeEvents{
        public static int anxLvl = 0;
        public static int maxAnxLvl = 100;

        @SubscribeEvent
        public static void PlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event){
            Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Anxiety level is now set to 0"));
            anxLvl = 0;
        }

        @SubscribeEvent
        public static void onlivingHurt(LivingHurtEvent event){
            Minecraft minecraft = Minecraft.getInstance();
            if(anxLvl < maxAnxLvl){
                anxLvl = anxLvl + 10;
                minecraft.player.displayClientMessage(Component.nullToEmpty("You're hurt"),true);
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Anxiety level is now: " + anxLvl));

            }else{
                AnxietyAttack(minecraft.player);
            }


        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Minecraft minecraft = Minecraft.getInstance();
            if (!event.player.level().isClientSide) {
                Player player = event.player;
                int currentFood = player.getFoodData().getFoodLevel();
                if (currentFood < 6) {
                    if(anxLvl < maxAnxLvl){
                        anxLvl = anxLvl + 10;
                        minecraft.player.displayClientMessage(Component.nullToEmpty("Your Hunger makes you anxious"),true);
                        Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Anxiety level is now: " + anxLvl));

                    }else{
                        AnxietyAttack(minecraft.player);
                    }
                }
            }

            long timeOfDay = event.player.level().getDayTime() % 24000;
            if(timeOfDay >= 13000 && timeOfDay <= 23000){
                if(anxLvl < maxAnxLvl){
                    anxLvl = anxLvl + 10;
                    minecraft.player.displayClientMessage(Component.nullToEmpty("Its dark out, beware of threats"),true);
                }else{
                    AnxietyAttack(minecraft.player);
                }
            }

        }

        public static void AnxietyAttack(Player player){
             player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 1));
             Minecraft.getInstance().gui.getChat().addMessage(Component.literal("You're Having an Anxiety Attack "));


        }

    }

}
