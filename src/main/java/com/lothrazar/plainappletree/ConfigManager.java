package com.lothrazar.plainappletree;

import com.lothrazar.library.config.ConfigTemplate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigManager extends ConfigTemplate {

  private static ForgeConfigSpec CONFIG;
  public static IntValue FRUIT_REGROW_CHANCE;
  public static IntValue FRUIT_REGROW_CHANCE_WATERLOGGED;
  public static IntValue BONUS_APPLE_CHANCE;
  static {
    final ForgeConfigSpec.Builder BUILDER = builder();
    BUILDER.comment("Mod settings").push(ModMain.MODID);
    FRUIT_REGROW_CHANCE = BUILDER.comment("1-in-N chance per random tick for a bare apple_leaves block to regrow its fruit. Lower = faster. Governed by the randomTickSpeed gamerule.")
        .defineInRange("appleRegrowChance", 20, 1, 1000);
    FRUIT_REGROW_CHANCE_WATERLOGGED = BUILDER.comment("Same as fruitRegrowChance, but when the leaf block is waterlogged. Lower = faster.")
        .defineInRange("appleRegrowChanceWaterlogged", 5, 1, 1000);
    BONUS_APPLE_CHANCE = BUILDER.comment("1-in-N chance of a bonus second apple when harvesting a fruited leaf block. Lower = more likely (e.g. 4 means a 25% chance). Set to 0 to always drop exactly 1 apple.")
        .defineInRange("bonusAppleChance", 4, 0, 1000);
    BUILDER.pop(); // one pop for every push
    CONFIG = BUILDER.build();
  }

  public ConfigManager() {
    CONFIG.setConfig(setup(ModMain.MODID));
  }
}
