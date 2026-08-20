package com.lothrazar.plainappletree.world;

import com.lothrazar.plainappletree.ModMain;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class AppleTreeGrower extends AbstractTreeGrower {

  public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE = FeatureUtils.createKey(ModMain.MODID + ":apple_tree");

  @Override
  protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource rand, boolean hasFlowers) {
    return APPLE_TREE;
  }
}
