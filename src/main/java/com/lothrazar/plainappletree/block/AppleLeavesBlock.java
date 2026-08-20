package com.lothrazar.plainappletree.block;

import com.lothrazar.plainappletree.ConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class AppleLeavesBlock extends LeavesBlock {

  public static final BooleanProperty FRUIT = BooleanProperty.create("fruit");

  public AppleLeavesBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.defaultBlockState().setValue(FRUIT, Boolean.FALSE));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(FRUIT);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return true;
  }

  @Override
  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
    super.randomTick(state, level, pos, rand);
    BlockState current = level.getBlockState(pos);
    if (current.is(this) && !current.getValue(FRUIT)) {
      int chance = current.getValue(WATERLOGGED) ? ConfigManager.FRUIT_REGROW_CHANCE_WATERLOGGED.get() : ConfigManager.FRUIT_REGROW_CHANCE.get();
      int distance = current.getValue(DISTANCE);
      if (distance >= 4) {
        // leaves far from any log (near the edge of the canopy, or an isolated placed leaf) regrow slower,
        // with distance 7 (max/uncomputed) getting an extra penalty beyond the linear falloff
        chance *= distance == 7 ? 8 : distance - 2;
      }
      if (rand.nextInt(chance) == 0) {
        level.setBlock(pos, current.setValue(FRUIT, Boolean.TRUE), 2);
      }
    }
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (!state.getValue(FRUIT)) {
      return super.use(state, level, pos, player, hand, hit);
    }
    if (!level.isClientSide) {
      level.setBlock(pos, state.setValue(FRUIT, Boolean.FALSE), 2);

      final int bonusChance = ConfigManager.BONUS_APPLE_CHANCE.get();
      int count = 1;
      if (bonusChance > 0 && level.random.nextInt(bonusChance) == 0) {
        count = 2;
      }
      // the leaf block itself stays solid (only fruit flips off), so nudge the drop down half a block -
      // otherwise a leaf boxed in on all sides and above has nowhere for the item to land but inside itself
      Containers.dropItemStack(level, pos.getX(), pos.getY() - 0.5, pos.getZ(), new ItemStack(Items.APPLE, count));
      level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
