/*
 * Copyright (c) CovertJaguar, 2011-2017
 *
 * This work (Ironclad Gameplay Tweaks) is licensed under the "MIT" License,
 * see LICENSE in root folder for details.
 */

package mods.ironclad.config;

import mods.ironclad.EventHandlers.IIroncladEventHandler;
import mods.ironclad.Ironclad;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by CovertJaguar on 4/17/2017 for Railcraft.
 *
 * @author CovertJaguar <http://www.railcraft.info>
 */
public class IroncladConfig {
    public static float horseSpeedModifier;
    public static float muleSpeedModifier;
    public static float undeadHorseSpeedModifier;
    public static File configFolder;
    public static Configuration config;
    private static String MOB_INV_ARMOR_DROP = "mob_inv_armor_drop_chances";
    private static String MOB_INV_HAND_DROP = "mob_inv_hand_drop_chances";
    public static String CAT_PLAYER = "player";
    public static String CAT_BONEMEAL = "bonemeal";
    public static String CAT_HORSE = "horse";
    public static String CAT_FLUIDS = "fluids";
    public static String CAT_ITEMS = "items";

    public static void load(File modConfigFolder) {
        configFolder = new File(modConfigFolder, Ironclad.MOD_ID);
        config = new Configuration(new File(configFolder, "general.cfg"));
        config.load();

        readConfig();
    }

    public static void readConfig() {
        config.setCategoryComment(MOB_INV_ARMOR_DROP, "The chance that a entity will drop the armor its wearing. -1 to disable drops entirely. -2 to ignore entity. 2 to force a drop. Vanilla uses 0.085.");
        config.setCategoryComment(MOB_INV_HAND_DROP, "The chance that a entity will drop what is in its hands. -1 to disable drops entirely. -2 to ignore entity. 2 to force a drop. Vanilla uses 0.085.");
        config.setCategoryComment(CAT_PLAYER, "Tweaks pertaining to the player.");
        config.setCategoryComment(CAT_BONEMEAL, "Tweaks pertaining to bonemeal.");
        config.setCategoryComment(CAT_HORSE, "Tweaks pertaining to horses.");
        config.setCategoryComment(CAT_FLUIDS, "Tweaks pertaining to fluids.");
        config.setCategoryComment(CAT_ITEMS, "Tweaks pertaining to items.");

        for (IIroncladEventHandler eventHandler : Ironclad.eventHandlers) {
            eventHandler.readConfig(config);
        }

        horseSpeedModifier = config.getFloat("horseSpeedModifier", CAT_HORSE, 0F, -1F, 1F, "Adjusts the speed of Horses. Formula: speed = base + base * modifier");
        undeadHorseSpeedModifier = config.getFloat("undeadHorseSpeedModifier", CAT_HORSE, 0F, -1F, 1F, "Adjusts the speed of Undead Horses. Formula: speed = base + base * modifier");
        muleSpeedModifier = config.getFloat("muleSpeedModifier", CAT_HORSE, 0F, -1F, 1F, "Adjusts the speed of Mules and Donkeys. Formula: speed = base + base * modifier");

        if (config.hasChanged())
            config.save();
    }

    public static void setArmorDropChance(Entity entity) {
        if (entity instanceof EntityLiving) {
            float dropChance = config.getFloat(entity.getClass().getName(), MOB_INV_ARMOR_DROP, -2F, -2F, 2F, "");
            if (dropChance >= -1F) {
                EntityLiving el = (EntityLiving) entity;
                el.setDropChance(EntityEquipmentSlot.HEAD, dropChance);
                el.setDropChance(EntityEquipmentSlot.CHEST, dropChance);
                el.setDropChance(EntityEquipmentSlot.LEGS, dropChance);
                el.setDropChance(EntityEquipmentSlot.FEET, dropChance);
            }

            if (config.hasChanged())
                config.save();
        }
    }

    public static void setHandDropChance(Entity entity) {
        if (entity instanceof EntityLiving) {
            float dropChance = config.getFloat(entity.getClass().getName(), MOB_INV_HAND_DROP, -2F, -2F, 2F, "");
            if (dropChance >= -1F) {
                EntityLiving el = (EntityLiving) entity;
                el.setDropChance(EntityEquipmentSlot.MAINHAND, dropChance);
                el.setDropChance(EntityEquipmentSlot.OFFHAND, dropChance);
            }

            if (config.hasChanged())
                config.save();
        }
    }

}
