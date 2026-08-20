package com.lothrazar.plainappletree;

import com.lothrazar.library.config.ConfigTemplate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigManager extends ConfigTemplate {

  private static ForgeConfigSpec CONFIG;
  public static BooleanValue TESTING;
  public static IntValue FRUIT_REGROW_CHANCE;
  public static IntValue FRUIT_REGROW_CHANCE_WATERLOGGED;
  static {
    final ForgeConfigSpec.Builder BUILDER = builder();
    BUILDER.comment("Mod settings").push(ModMain.MODID);
    TESTING = BUILDER.comment("Testing boolean config").define("doesNothing", true);
    FRUIT_REGROW_CHANCE = BUILDER.comment("1-in-N chance per random tick for a bare apple_leaves block to regrow its fruit. Lower = faster. Governed by the randomTickSpeed gamerule.")
        .defineInRange("fruitRegrowChance", 20, 1, 1000);
    FRUIT_REGROW_CHANCE_WATERLOGGED = BUILDER.comment("Same as fruitRegrowChance, but used instead when the leaf block is waterlogged. Lower = faster.")
        .defineInRange("fruitRegrowChanceWaterlogged", 10, 1, 1000);
    BUILDER.pop(); // one pop for every push
    CONFIG = BUILDER.build();
  }

  public ConfigManager() {
    CONFIG.setConfig(setup(ModMain.MODID));
  }
}
