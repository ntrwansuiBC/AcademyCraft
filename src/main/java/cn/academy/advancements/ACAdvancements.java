package cn.academy.advancements;

import cn.academy.Resources;
import cn.academy.ability.vanilla.VanillaCategories;
import cn.academy.ability.vanilla.electromaster.CatElectromaster;
import cn.academy.ability.vanilla.meltdowner.CatMeltdowner;
import cn.academy.ability.vanilla.teleporter.CatTeleporter;
import cn.academy.ability.vanilla.vecmanip.CatVecManip;
import cn.academy.ability.vanilla.vecmanip.skill.*;
import cn.academy.AcademyCraft;
import cn.academy.advancements.triggers.ACLevelChangeTrigger;
import cn.academy.advancements.triggers.ACTrigger;
import cn.lambdalib2.registry.StateEventCallback;
import net.minecraft.advancements.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import cn.academy.block.block.*;
import cn.academy.item.*;

/**
 * Automatically generated by LambdaLib2.xconf in 2018-11-10 03:25:14.
 */
public class ACAdvancements {

    public static final cn.academy.advancements.triggers.ACTrigger ac_developer = new cn.academy.advancements.triggers.ACTrigger("ac_developer");

    public static final cn.academy.advancements.triggers.ACTrigger ac_exp_full = new cn.academy.advancements.triggers.ACTrigger("ac_exp_full");

    public static final cn.academy.advancements.triggers.ACTrigger ac_learning_skill = new cn.academy.advancements.triggers.ACTrigger("ac_learning_skill");

    public static final cn.academy.advancements.triggers.ACTrigger ac_level_3 = new cn.academy.advancements.triggers.ACTrigger("ac_level_3");

    public static final cn.academy.advancements.triggers.ACTrigger ac_level_5 = new cn.academy.advancements.triggers.ACTrigger("ac_level_5");

    public static final cn.academy.advancements.triggers.ACTrigger ac_matrix = new cn.academy.advancements.triggers.ACTrigger("ac_matrix");

    public static final cn.academy.advancements.triggers.ACTrigger ac_milestone = new cn.academy.advancements.triggers.ACTrigger("ac_milestone");

    public static final cn.academy.advancements.triggers.ACTrigger ac_node = new cn.academy.advancements.triggers.ACTrigger("ac_node");

    public static final cn.academy.advancements.triggers.ACTrigger ac_overload = new cn.academy.advancements.triggers.ACTrigger("ac_overload");

    public static final cn.academy.advancements.triggers.ACTrigger convert_category = new cn.academy.advancements.triggers.ACTrigger("convert_category");

    public static final cn.academy.advancements.triggers.ACTrigger dev_category = new cn.academy.advancements.triggers.ACTrigger("dev_category");

    public static final cn.academy.advancements.triggers.ACTrigger getting_factor = new cn.academy.advancements.triggers.ACTrigger("getting_factor");

    public static final cn.academy.advancements.triggers.ACTrigger getting_phase = new cn.academy.advancements.triggers.ACTrigger("getting_phase");

    public static final cn.academy.advancements.triggers.ACTrigger open_misaka_cloud = new cn.academy.advancements.triggers.ACTrigger("open_misaka_cloud");

    public static final cn.academy.advancements.triggers.ACTrigger phase_generator = new cn.academy.advancements.triggers.ACTrigger("phase_generator");


    public static final cn.academy.advancements.triggers.ACTrigger terminal_installed = new cn.academy.advancements.triggers.ACTrigger("terminal_installed");


    @StateEventCallback
    public static void init(FMLInitializationEvent event) {
        DispatcherAch.init();

        CriteriaTriggers.register(ac_developer);

        CriteriaTriggers.register(ac_exp_full);

        CriteriaTriggers.register(ac_learning_skill);

        CriteriaTriggers.register(ac_level_3);

        CriteriaTriggers.register(ac_level_5);

        CriteriaTriggers.register(ac_matrix);

        CriteriaTriggers.register(ac_milestone);

        CriteriaTriggers.register(ac_node);

        CriteriaTriggers.register(ac_overload);

        CriteriaTriggers.register(convert_category);

        CriteriaTriggers.register(dev_category);

        CriteriaTriggers.register(getting_factor);

        CriteriaTriggers.register(getting_phase);

        CriteriaTriggers.register(open_misaka_cloud);

        CriteriaTriggers.register(phase_generator);


        CriteriaTriggers.register(terminal_installed);
    }

    /**
     * Trigger an achievement
     * @param player The player
     * @param achid The id of the achievement
     * @return true if succeeded
     * This method is server-only. --Paindar
     */
    public static boolean trigger(EntityPlayer player, ResourceLocation achid) {
            if (!(player instanceof EntityPlayerMP))
                return false;

            ICriterionTrigger ach = CriteriaTriggers.get(achid);
            if (ach == null || (!(ach instanceof ACTrigger))) {
                AcademyCraft.log.warn("AC Achievement '" + achid + "' does not exist");
                return false;
            }
            ((ACTrigger)ach).trigger((EntityPlayerMP) player);
            return true;
    }

    public static boolean trigger(EntityPlayer player, String achid) {
            return trigger(player, Resources.res(achid));
    }
}