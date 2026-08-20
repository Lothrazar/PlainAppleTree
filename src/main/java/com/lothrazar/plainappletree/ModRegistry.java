package com.lothrazar.plainappletree;

import com.lothrazar.plainappletree.block.AppleLeavesBlock;
import com.lothrazar.plainappletree.world.AppleTreeGrower;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MODID);
  public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModMain.MODID);
  private static final ResourceKey<CreativeModeTab> TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ModMain.MODID, "tab"));

  @SubscribeEvent
  public static void onCreativeModeTabRegister(RegisterEvent event) {
    event.register(Registries.CREATIVE_MODE_TAB, helper -> {
      helper.register(TAB, CreativeModeTab.builder().icon(() -> new ItemStack(I_APPLE_SAPLING.get()))
          .title(Component.translatable("itemGroup." + ModMain.MODID))
          .displayItems((enabledFlags, populator) -> {
            for (RegistryObject<Item> entry : ITEMS.getEntries()) {
              populator.accept(entry.get());
            }
          }).build());
    });
  }

  public static final RegistryObject<Block> APPLE_SAPLING = BLOCKS.register("apple_sapling", () -> new SaplingBlock(new AppleTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING)));
  public static final RegistryObject<Block> APPLE_LEAVES = BLOCKS.register("apple_leaves", () -> new AppleLeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));
  public static final RegistryObject<Item> I_APPLE_SAPLING = ITEMS.register("apple_sapling", () -> new BlockItem(APPLE_SAPLING.get(), new Item.Properties()));
  public static final RegistryObject<Item> I_APPLE_LEAVES = ITEMS.register("apple_leaves", () -> new BlockItem(APPLE_LEAVES.get(), new Item.Properties()));
}
