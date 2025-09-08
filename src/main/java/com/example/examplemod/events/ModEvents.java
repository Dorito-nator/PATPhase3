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
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = AnxietyMod.MODID)
    public static class ForgeEvents{

        //current Anxiety Level
        public static int anxLvl = 0;
        //Max Level of anxiety that can be reached
        public static int maxAnxLvl = 10;

        /*
         -----------------------Decreases ANX Level---------------------------------
         */

        /*
        This Event Triggers whenever the player dies and respanws

        Setup: Resets the anxiety level to 0
         */
        @SubscribeEvent
        public static void PlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event){
            Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Anxiety level is now set to 0"));
            anxLvl = 0;
        }

        /*
        This even triggers whenever the player completes and achivement in game

        Setup Decreases Anx Level by 1
         */
        @SubscribeEvent
        public static void AdvancementEvent(AdvancementEvent event){
            if (anxLvl > 0){
                anxLvl  = anxLvl -1;
                Minecraft.getInstance().gui.getChat()
                        .addMessage(Component.literal("Anxiety level has decreased"));
            }
        }

        @SubscribeEvent
        public static void spownPoint(PlayerSetSpawnEvent event){
            if (anxLvl > 0){
                anxLvl  = anxLvl -1;
                Minecraft.getInstance().gui.getChat()
                        .addMessage(Component.literal("Anxiety level has decreased"));
            }
        }

        /*
        -------------------------------------Increase ANX Level-------------------------------------
         */

        /*
        This Event triggers when an entitys health decreases
         */
        @SubscribeEvent
        public static void onlivingHurt(LivingHurtEvent event){
            Minecraft minecraft = Minecraft.getInstance();

            //checks if the entity is the player
            if(event.getEntity().getName().equals(minecraft.player.getName())) {

                //Increases Anxiety Level
                if (anxLvl < maxAnxLvl) {
                    anxLvl = anxLvl + 1;
                    minecraft.player.displayClientMessage(Component.nullToEmpty("You're hurt"), true);
                    Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Anxiety level is now: " + anxLvl));

                } else {
                    //trigers Anxiety Attack bc the player reaches max ANX lvl
                    AnxietyAttack(minecraft.player);
                }
            }
        }

        /*
        This event triggers every tick( a tick is essentially a unit of in game time)
         */
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Minecraft minecraft = Minecraft.getInstance();
            //checks for player
            if (!event.player.level().isClientSide) {
                Player player = event.player;
                //checks current hunger/food level
                int currentFood = player.getFoodData().getFoodLevel();
                //if the player is hungry increase anxiety level
                if (currentFood < 6) {
                    if(anxLvl < maxAnxLvl){
                        anxLvl = anxLvl + 1;
                        minecraft.player
                                .displayClientMessage(Component.nullToEmpty("Your Hunger makes you anxious"),true);

                        Minecraft.getInstance().gui.getChat()
                                .addMessage(Component.literal("Anxiety level is now: " + anxLvl));

                    }else{
                        //anx attack
                        AnxietyAttack(minecraft.player);
                    }
                }
            }

            //get time of day
            long timeOfDay = event.player.level().getDayTime() % 24000;
            if(timeOfDay == 13000){
                //checks if its turning night-time
                if(anxLvl < maxAnxLvl){
                    anxLvl = anxLvl + 1;
                    minecraft.player.displayClientMessage(Component.nullToEmpty("Its dark out, beware of threats"),true);
                }else{
                    AnxietyAttack(minecraft.player);
                }
            }

        }


        public static void AnxietyAttack(Player player){
            //Ingame status effects
             player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
             player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 1));

             Minecraft.getInstance().gui.getChat()
                     .addMessage(Component.literal("You're Having an Anxiety Attack. Learn more about anxiety disorders at https://my.clevelandclinic.org/health/diseases/9536-anxiety-disorders"));


        }

    }

}
