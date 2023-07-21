package net.sweenus.simplyskills.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "berserker")
public class BerserkerConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public boolean enableBerserkerSpecialisation = true;

    public int passiveBerserkerSwordMasteryFrequency = 20;
    public int passiveBerserkerSwordMasteryBaseSpeedAmplifier = 0;
    public int passiveBerserkerSwordMasterySpeedAmplifierPerTier = 1;
    public int passiveBerserkerAxeMasteryFrequency = 20;
    public int passiveBerserkerAxeMasteryBaseStrengthAmplifier = 0;
    public int passiveBerserkerAxeMasteryStrengthAmplifierPerTier = 1;
    public int passiveBerserkerIgnorePainFrequency = 20;
    public double passiveBerserkerIgnorePainHealthThreshold = 0.4;
    public int passiveBerserkerIgnorePainBaseResistanceAmplifier = 0;
    public int passiveBerserkerIgnorePainResistanceAmplifierPerTier = 1;
    public int passiveBerserkerRecklessnessFrequency = 20;
    public double passiveBerserkerRecklessnessHealthThreshold = 0.7;
    public int passiveBerserkerRecklessnessWeaknessAmplifier = 0;
    public int passiveBerserkerChallengeFrequency = 20;
    public int passiveBerserkerChallengeRadius = 2;
    public int passiveBerserkerChallengeMaxAmplifier = 5;

    public int signatureBerserkerRampageDuration = 300;
    public int signatureBerserkerRampageSubEffectDuration = 200;
    public int signatureBerserkerRampageSubEffectMaxAmplifier = 3;
    public int signatureBerserkerBullrushDuration = 20;
    public int signatureBerserkerBullrushVelocity = 2;
    public int signatureBerserkerBullrushRadius = 3;
    public double signatureBerserkerBullrushDamageModifier = 1.8;
    public int signatureBerserkerBullrushHitFrequency = 5;
    public int signatureBerserkerBullrushImmobilizeDuration = 80;

    public int signatureBerserkerBloodthirstyDuration = 400;
    public float signatureBerserkerBloodthirstyHealPercent = 0.25f;

    public float signatureBerserkerBerserkingSacrificeAmount = 0.30f;
    public int signatureBerserkerBerserkingSecondsPerSacrifice = 1;
    public int signatureBerserkerBerserkingSubEffectDuration = 200;
    public int signatureBerserkerBerserkingSubEffectMaxAmplifier = 3;
    public int signatureBerserkerLeapSlamDuration = 62;
    public int signatureBerserkerLeapSlamRadius = 3;
    public double signatureBerserkerLeapSlamVelocity = 1.5;
    public double signatureBerserkerLeapSlamHeight = 0.9;
    public double signatureBerserkerLeapSlamDescentVelocity = 1.0;
    public double signatureBerserkerLeapSlamDamageModifier = 2.8;
    public int signatureBerserkerLeapSlamImmobilizeDuration = 80;




}
