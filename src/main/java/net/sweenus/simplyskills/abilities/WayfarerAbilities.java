package net.sweenus.simplyskills.abilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sweenus.simplyskills.SimplySkills;
import net.sweenus.simplyskills.registry.EffectRegistry;
import net.sweenus.simplyskills.registry.SoundRegistry;
import net.sweenus.simplyskills.util.HelperMethods;
import net.sweenus.simplyskills.util.SkillReferencePosition;

public class WayfarerAbilities {

    public static void passiveWayfarerBreakStealth(
            Entity target,
            PlayerEntity player,
            Boolean brokenByDamage,
            Boolean backstabBonus) {

        if (player.hasStatusEffect(EffectRegistry.STEALTH)) {

            if (brokenByDamage) {

                int speedDuration = SimplySkills.rogueConfig.passiveRogueFleetfootedSpeedDuration;
                int speedStacks = SimplySkills.rogueConfig.passiveRogueFleetfootedSpeedStacks;
                int speedMaxStacks = SimplySkills.rogueConfig.passiveRogueFleetfootedSpeedMaxStacks;
                int evasionDuration = SimplySkills.wayfarerConfig.passiveWayfarerReflexiveEvasionDuration;
                int evasionChance = SimplySkills.wayfarerConfig.passiveWayfarerReflexiveChance;

                if (HelperMethods.isUnlocked("simplyskills:rogue",
                        SkillReferencePosition.rogueFleetfooted, player))
                    HelperMethods.incrementStatusEffect(player, StatusEffects.SPEED,
                            speedDuration, speedStacks, speedMaxStacks);
                if (HelperMethods.isUnlocked("simplyskills:tree",
                        SkillReferencePosition.wayfarerReflexive, player)
                        && player.getRandom().nextInt(100) < evasionChance)
                    HelperMethods.incrementStatusEffect(player, EffectRegistry.EVASION,
                            evasionDuration, 1, 1);

            }

            if ( !brokenByDamage ) {

                if (HelperMethods.isUnlocked("simplyskills:tree",
                        SkillReferencePosition.wayfarerReflexive, player)) {
                    HelperMethods.incrementStatusEffect(player, EffectRegistry.MIGHT, 40, 1, 20);
                    HelperMethods.incrementStatusEffect(player, EffectRegistry.MARKSMANSHIP, 40, 1, 20);
                }

                if (target != null) {
                    if (target instanceof LivingEntity livingTarget) {
                        int deathmarkDuration = SimplySkills.rogueConfig.passiveRogueExploitationDeathMarkDuration;
                        int deathmarkStacks = SimplySkills.rogueConfig.passiveRogueExploitationDeathMarkStacks;

                        if (backstabBonus && HelperMethods.isBehindTarget(player, livingTarget)) {
                            if (HelperMethods.isUnlocked("simplyskills:rogue",
                                    SkillReferencePosition.rogueExploitation, player))
                                HelperMethods.incrementStatusEffect(
                                        livingTarget,
                                        EffectRegistry.DEATHMARK,
                                        deathmarkDuration,
                                        deathmarkStacks,
                                        3);
                            if (HelperMethods.isUnlocked("simplyskills:rogue",
                                    SkillReferencePosition.rogueOpportunisticMastery, player))
                                RogueAbilities.passiveRogueOpportunisticMastery(livingTarget, player);
                        }
                    }
                }
            }
            player.removeStatusEffect(EffectRegistry.STEALTH);
            player.getWorld().playSoundFromEntity(
                    null, player, SoundRegistry.SOUNDEFFECT36,
                    SoundCategory.PLAYERS, 0.7f, 1.4f);
            if (player.hasStatusEffect(StatusEffects.INVISIBILITY))
                player.removeStatusEffect(StatusEffects.INVISIBILITY);
            player.addStatusEffect(new StatusEffectInstance(EffectRegistry.REVEALED, 180, 5, false, false, true));
        }
    }

    public static void passiveWayfarerGuarding(PlayerEntity player) {
        int barrierFrequency = SimplySkills.wayfarerConfig.passiveWayfarerGuardingBarrierFrequency;
        int barrierDuration = SimplySkills.wayfarerConfig.passiveWayfarerGuardingBarrierDuration;
        int barrierStacks = SimplySkills.wayfarerConfig.passiveWayfarerGuardingBarrierStacks;
        int barrierMaxStacks = SimplySkills.wayfarerConfig.passiveWayfarerGuardingBarrierMaxStacks;
        if (player.getOffHandStack().getItem() instanceof CrossbowItem
                && player.age % barrierFrequency == 0) {
            HelperMethods.incrementStatusEffect(player, EffectRegistry.BARRIER, barrierDuration, barrierStacks, barrierMaxStacks);
        }
    }

    public static void passiveWayfarerSlender(PlayerEntity player) {

        int slenderArmorThreshold = SimplySkills.wayfarerConfig.passiveWayfarerSlenderArmorThreshold;
        int frailArmorThreshold = SimplySkills.initiateConfig.passiveInitiateFrailArmorThreshold;

        if (player.age % 20 == 0) {

            int armorValue = player.getArmor();

            if (armorValue < slenderArmorThreshold) {
                if (HelperMethods.isUnlocked("simplyskills:tree", SkillReferencePosition.roguePath, player)
                        || HelperMethods.isUnlocked("simplyskills:tree", SkillReferencePosition.rangerPath, player)) {

                    int buffAmplifier = (slenderArmorThreshold - armorValue) / 5;
                    player.addStatusEffect(new StatusEffectInstance(EffectRegistry.AGILE,
                            25, buffAmplifier, false, false, false));
                }
            }
            if (armorValue < frailArmorThreshold) {
                if (HelperMethods.isUnlocked("simplyskills:tree", SkillReferencePosition.wizardPath, player)) {
                    int buffAmplifier = (frailArmorThreshold - armorValue) / 5;
                    player.addStatusEffect(new StatusEffectInstance(EffectRegistry.AGILE,
                            25, buffAmplifier, false, false, false));
                }
            }
        }
    }

    public static boolean passiveWayfarerStealth(PlayerEntity player) {
        return HelperMethods.isUnlocked("simplyskills:tree",
                SkillReferencePosition.wayfarerStealth, player)
                && player.isSneaking()
                && !player.hasStatusEffect(EffectRegistry.REVEALED) && !isPlayerTargeted(player, 20);
    }

    public static boolean isPlayerTargeted(PlayerEntity player, int radius) {
        World world = player.getWorld();
        Box box = new Box(player.getX() - radius, player.getY() - radius, player.getZ() - radius,
                player.getX() + radius, player.getY() + radius, player.getZ() + radius);

        // Check if any MobEntity has the player targeted
        for (Entity entity : world.getOtherEntities(player, box, entity -> entity instanceof MobEntity)) {
            if (entity instanceof MobEntity mobEntity) {
                LivingEntity target = mobEntity.getTarget();
                if (target == player) {
                    return true;
                }
            }
        }

        // Check if any PlayerEntity has the player in their viewing angle
        for (Entity entity : world.getOtherEntities(player, box, entity -> entity instanceof PlayerEntity)) {
            if (entity instanceof PlayerEntity otherPlayer && otherPlayer != player) {
                if (isInViewingAngle(otherPlayer, player)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isInViewingAngle(PlayerEntity viewer, PlayerEntity target) {
        Vec3d viewerPos = viewer.getPos();
        Vec3d targetPos = target.getPos();
        Vec3d directionToTarget = targetPos.subtract(viewerPos).normalize();
        Vec3d viewerLookVec = viewer.getRotationVec(1.0F).normalize();

        double dotProduct = viewerLookVec.dotProduct(directionToTarget);
        double threshold = Math.cos(Math.toRadians(90)); // 90 degrees viewing angle

        return dotProduct > threshold;
    }



}
