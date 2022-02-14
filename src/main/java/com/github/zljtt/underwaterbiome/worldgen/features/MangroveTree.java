package com.github.zljtt.underwaterbiome.worldgen.features;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.github.zljtt.underwaterbiome.blocks.MangroveLeaf;
import com.github.zljtt.underwaterbiome.registries.BlockRegistry;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;

public class MangroveTree extends Feature<NoFeatureConfig> {

	public MangroveTree() {
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public boolean place(ISeedReader reader, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {

		int foliageHeight = 2;
		int foliageRadius = 3;

		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set1 = Sets.newHashSet();
//		Set<BlockPos> set2 = Sets.newHashSet();
		MutableBoundingBox mutableboundingbox = MutableBoundingBox.getUnknownBox();
		int oceanFloor = reader.getHeightmapPos(Heightmap.Type.OCEAN_FLOOR, pos).getY();
		BlockPos blockPos = new BlockPos(pos.getX(), oceanFloor, pos.getZ());
		if (reader.getBlockState(blockPos.below()).getBlock() != Blocks.SAND) {
			return false;
		}
		List<FoliagePlacer.Foliage> list = this.placeTrunk(reader, random, 4 + random.nextInt(4), blockPos, set,
				mutableboundingbox);
		list.forEach((foliage) -> {
//			System.out.println("foliage:" + foliage.foliagePos().toString());
			this.createFoliage(reader, random, foliage, foliageHeight, foliageRadius, set1, FeatureSpread.of(2, 2).sample(random),
					mutableboundingbox);
		});

//		VoxelShapePart voxelshapepart = this.updateLeaves(reader, mutableboundingbox, set, set2);
//		Template.updateShapeAtEdge(reader, 3, voxelshapepart, mutableboundingbox.x0, mutableboundingbox.y0,
//				mutableboundingbox.z0);

		return true;
	}

	public List<FoliagePlacer.Foliage> placeTrunk(IWorldGenerationReader p_230382_1_, Random p_230382_2_, int p_230382_3_,
			BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_) {
		int trunkOffset = 1 + p_230382_2_.nextInt(4);
		BlockPos realStart = p_230382_4_.above(trunkOffset);
		int j = p_230382_3_ + 2;
		int k = MathHelper.floor((double) j * 0.618D);
		int l = Math.min(1, MathHelper.floor(1.382D + Math.pow(1.0D * (double) j / 13.0D, 2.0D)));
		int i1 = realStart.getY() + k;
		int j1 = j - 5;
		List<TrunkFoliage> list = Lists.newArrayList();
		list.add(new TrunkFoliage(realStart.above(j1), i1));
		for (; j1 >= 0; --j1) {
			float f = this.treeShape(j, j1);
			if (!(f < 0.0F)) {
				for (int k1 = 0; k1 < l; ++k1) {
					double d2 = 1d * (double) f * ((double) p_230382_2_.nextFloat() + 0.328D);
					double d3 = (double) (p_230382_2_.nextFloat() * 2.0F) * Math.PI;
					double d4 = d2 * Math.sin(d3) + 0.5D;
					double d5 = d2 * Math.cos(d3) + 0.5D;
					BlockPos blockpos = realStart.offset(d4, (double) (j1 - 1), d5);
					BlockPos blockpos1 = blockpos.above(5);
					if (this.makeLimb(p_230382_1_, p_230382_2_, blockpos, blockpos1, false, p_230382_5_, p_230382_6_)) {
						int l1 = realStart.getX() - blockpos.getX();
						int i2 = realStart.getZ() - blockpos.getZ();
						double d6 = (double) blockpos.getY() - Math.sqrt((double) (l1 * l1 + i2 * i2)) * 0.381D;
						int j2 = d6 > (double) i1 ? i1 : (int) d6;
						BlockPos blockpos2 = new BlockPos(realStart.getX(), j2, realStart.getZ());
						if (this.makeLimb(p_230382_1_, p_230382_2_, blockpos2, blockpos, false, p_230382_5_, p_230382_6_)) {
							list.add(new TrunkFoliage(blockpos, blockpos2.getY()));
						}
					}
				}
			}
		}
		this.makeBranches(p_230382_1_, p_230382_2_, j, realStart, list, p_230382_5_, p_230382_6_);
		this.makeRoots(p_230382_1_, p_230382_2_, realStart, p_230382_5_, p_230382_6_, p_230382_2_);
		List<FoliagePlacer.Foliage> list1 = Lists.newArrayList();
		for (TrunkFoliage fancytrunkplacer$foliage : list) {
//			if (this.trimBranches(j, fancytrunkplacer$foliage.getBranchBase() - realStart.getY())) {
			list1.add(fancytrunkplacer$foliage.attachment);
//			}
		}
		this.makeLimb(p_230382_1_, p_230382_2_, realStart, realStart.above(k), true, p_230382_5_, p_230382_6_);
		return list1;
	}

	private void makeRoots(IWorldGenerationReader reader, Random random, BlockPos pos, Set<BlockPos> set, MutableBoundingBox box,
			Random ran) {
		int ran0 = 3 + ran.nextInt(3);
		int i = 0;
		for (int k = 0; i < ran0 && k < 10; k++) {
			int x_offset = pos.getX() - 5 + ran.nextInt(10);
			int z_offset = pos.getZ() - 5 + ran.nextInt(10);
			BlockPos pos0 = new BlockPos(x_offset, pos.getY(), z_offset);
			BlockPos height = reader.getHeightmapPos(Heightmap.Type.OCEAN_FLOOR, pos0);
			if ((pos.getY() - height.getY()) < 10 && (pos.getY() - height.getY()) > 0) {
				this.makeLimb(reader, random, pos, height, true, set, box);
				i++;
			}
		}
	}

	@SuppressWarnings("deprecation")
	protected void createFoliage(IWorldGenerationReader reader, Random random, FoliagePlacer.Foliage foliage, int foilageHeight,
			int treeHeight, Set<BlockPos> set, int offset, MutableBoundingBox box) {
		for (int i = offset; i >= offset - foilageHeight; --i) {
			int j = treeHeight + (i != offset && i != offset - offset ? 1 : 0);
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
			for (int j1 = -j; j1 <= j + 1; ++j1) {
				for (int k1 = -j; k1 <= j + 1; ++k1) {
					if (!this.shouldSkipLocationSigned(random, j1, i, k1, j, false)) {
						blockpos$mutable.setWithOffset(foliage.foliagePos(), j1, i, k1);
						if (reader.isStateAtPosition(blockpos$mutable, (state) -> state.getFluidState().is(FluidTags.WATER))) {
							reader.setBlock(blockpos$mutable, BlockRegistry.MANGROVE_LEAVES.get().defaultBlockState()
									.setValue(MangroveLeaf.WATERLOGGED, true), 19);
						} else if (reader.isStateAtPosition(blockpos$mutable, (state) -> state.isAir())) {
							reader.setBlock(blockpos$mutable, BlockRegistry.MANGROVE_LEAVES.get().defaultBlockState()
									.setValue(MangroveLeaf.WATERLOGGED, false), 19);
						}

						box.expand(new MutableBoundingBox(blockpos$mutable, blockpos$mutable));
						set.add(blockpos$mutable.immutable());
					}
				}
			}
		}

	}

	protected boolean shouldSkipLocationSigned(Random p_230375_1_, int p_230375_2_, int p_230375_3_, int p_230375_4_,
			int p_230375_5_, boolean p_230375_6_) {
		int i;
		int j;
		if (p_230375_6_) {
			i = Math.min(Math.abs(p_230375_2_), Math.abs(p_230375_2_ - 1));
			j = Math.min(Math.abs(p_230375_4_), Math.abs(p_230375_4_ - 1));
		} else {
			i = Math.abs(p_230375_2_);
			j = Math.abs(p_230375_4_);
		}

		return MathHelper.square((float) i + 0.5F) + MathHelper.square((float) j + 0.5F) > (float) (p_230375_5_ * p_230375_5_);
	}

	private boolean makeLimb(IWorldGenerationReader reader, Random random, BlockPos from, BlockPos to, boolean p_236887_5_,
			Set<BlockPos> set, MutableBoundingBox box) {
		if (!p_236887_5_ && Objects.equals(from, to)) {
			return true;
		} else {
			BlockPos blockpos = to.offset(-from.getX(), -from.getY(), -from.getZ());
			int i = this.getSteps(blockpos);
			float f = (float) blockpos.getX() / (float) i;
			float f1 = (float) blockpos.getY() / (float) i;
			float f2 = (float) blockpos.getZ() / (float) i;

			for (int j = 0; j <= i; ++j) {
				BlockPos blockpos1 = from.offset((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1),
						(double) (0.5F + (float) j * f2));
				if (p_236887_5_) {
					setBlock(reader, blockpos1, BlockRegistry.MANGROVE_LOG.get().defaultBlockState()
							.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(from, blockpos1)), box);
					set.add(blockpos1.immutable());
				}
//				else if (!TreeFeature.isFree(reader, blockpos1)) {
//					return false;
//				}
			}

			return true;
		}
	}

	private void makeBranches(IWorldGenerationReader reader, Random random, int p_236886_3_, BlockPos pos,
			List<TrunkFoliage> foliges, Set<BlockPos> set, MutableBoundingBox box) {
		for (TrunkFoliage fancytrunkplacer$foliage : foliges) {
			int i = fancytrunkplacer$foliage.getBranchBase();
			BlockPos blockpos = new BlockPos(pos.getX(), i, pos.getZ());
			if (!blockpos.equals(fancytrunkplacer$foliage.attachment.foliagePos())
					&& this.trimBranches(p_236886_3_, i - pos.getY())) {
				this.makeLimb(reader, random, blockpos, fancytrunkplacer$foliage.attachment.foliagePos(), true, set, box);
			}
		}

	}

	private boolean trimBranches(int p_236885_1_, int p_236885_2_) {
		return (double) p_236885_2_ >= (double) p_236885_1_ * 0.2D;
	}

	private int getSteps(BlockPos pos) {
		int i = MathHelper.abs(pos.getX());
		int j = MathHelper.abs(pos.getY());
		int k = MathHelper.abs(pos.getZ());
		return Math.max(i, Math.max(j, k));
	}

	private Direction.Axis getLogAxis(BlockPos from, BlockPos to) {
		Direction.Axis direction$axis = Direction.Axis.Y;
		int i = Math.abs(to.getX() - from.getX());
		int j = Math.abs(to.getZ() - from.getZ());
		int k = Math.max(i, j);
		if (k > 0) {
			if (i == k) {
				direction$axis = Direction.Axis.X;
			} else {
				direction$axis = Direction.Axis.Z;
			}
		}

		return direction$axis;
	}

	private float treeShape(int p_236890_1_, int p_236890_2_) {
		if ((float) p_236890_2_ < (float) p_236890_1_ * 0.3F) {
			return -1.0F;
		} else {
			float f = (float) p_236890_1_ / 2.0F;
			float f1 = f - (float) p_236890_2_;
			float f2 = MathHelper.sqrt(f * f - f1 * f1);
			if (f1 == 0.0F) {
				f2 = f;
			} else if (Math.abs(f1) >= f) {
				return 0.0F;
			}

			return f2 * 0.5F;
		}
	}

//	private VoxelShapePart updateLeaves(IWorld p_236403_1_, MutableBoundingBox p_236403_2_, Set<BlockPos> p_236403_3_,
//			Set<BlockPos> p_236403_4_) {
//		List<Set<BlockPos>> list = Lists.newArrayList();
//		VoxelShapePart voxelshapepart = new BitSetVoxelShapePart(p_236403_2_.getXSpan(), p_236403_2_.getYSpan(),
//				p_236403_2_.getZSpan());
//		int i = 6;
//
//		for (int j = 0; j < 6; ++j) {
//			list.add(Sets.newHashSet());
//		}
//
//		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
//
//		for (BlockPos blockpos : Lists.newArrayList(p_236403_4_)) {
//			if (p_236403_2_.isInside(blockpos)) {
//				voxelshapepart.setFull(blockpos.getX() - p_236403_2_.x0, blockpos.getY() - p_236403_2_.y0,
//						blockpos.getZ() - p_236403_2_.z0, true, true);
//			}
//		}
//
//		for (BlockPos blockpos1 : Lists.newArrayList(p_236403_3_)) {
//			if (p_236403_2_.isInside(blockpos1)) {
//				voxelshapepart.setFull(blockpos1.getX() - p_236403_2_.x0, blockpos1.getY() - p_236403_2_.y0,
//						blockpos1.getZ() - p_236403_2_.z0, true, true);
//			}
//
//			for (Direction direction : Direction.values()) {
//				blockpos$mutable.setWithOffset(blockpos1, direction);
//				if (!p_236403_3_.contains(blockpos$mutable)) {
//					BlockState blockstate = p_236403_1_.getBlockState(blockpos$mutable);
//					if (blockstate.hasProperty(BlockStateProperties.DISTANCE)) {
//						list.get(0).add(blockpos$mutable.immutable());
//						setBlockKnownShape(p_236403_1_, blockpos$mutable,
//								blockstate.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
//						if (p_236403_2_.isInside(blockpos$mutable)) {
//							voxelshapepart.setFull(blockpos$mutable.getX() - p_236403_2_.x0,
//									blockpos$mutable.getY() - p_236403_2_.y0, blockpos$mutable.getZ() - p_236403_2_.z0,
//									true, true);
//						}
//					}
//				}
//			}
//		}
//
//		for (int l = 1; l < 6; ++l) {
//			Set<BlockPos> set = list.get(l - 1);
//			Set<BlockPos> set1 = list.get(l);
//
//			for (BlockPos blockpos2 : set) {
//				if (p_236403_2_.isInside(blockpos2)) {
//					voxelshapepart.setFull(blockpos2.getX() - p_236403_2_.x0, blockpos2.getY() - p_236403_2_.y0,
//							blockpos2.getZ() - p_236403_2_.z0, true, true);
//				}
//
//				for (Direction direction1 : Direction.values()) {
//					blockpos$mutable.setWithOffset(blockpos2, direction1);
//					if (!set.contains(blockpos$mutable) && !set1.contains(blockpos$mutable)) {
//						BlockState blockstate1 = p_236403_1_.getBlockState(blockpos$mutable);
//						if (blockstate1.hasProperty(BlockStateProperties.DISTANCE)) {
//							int k = blockstate1.getValue(BlockStateProperties.DISTANCE);
//							if (k > l + 1) {
//								BlockState blockstate2 = blockstate1.setValue(BlockStateProperties.DISTANCE,
//										Integer.valueOf(l + 1));
//								setBlockKnownShape(p_236403_1_, blockpos$mutable, blockstate2);
//								if (p_236403_2_.isInside(blockpos$mutable)) {
//									voxelshapepart.setFull(blockpos$mutable.getX() - p_236403_2_.x0,
//											blockpos$mutable.getY() - p_236403_2_.y0,
//											blockpos$mutable.getZ() - p_236403_2_.z0, true, true);
//								}
//
//								set1.add(blockpos$mutable.immutable());
//							}
//						}
//					}
//				}
//			}
//		}
//
//		return voxelshapepart;
//	}

	protected void setBlock(IWorldWriter p_230367_1_, BlockPos p_230367_2_, BlockState p_230367_3_) {
		setBlockKnownShape(p_230367_1_, p_230367_2_, p_230367_3_);
	}

	public static void setBlockKnownShape(IWorldWriter p_236408_0_, BlockPos p_236408_1_, BlockState p_236408_2_) {
		p_236408_0_.setBlock(p_236408_1_, p_236408_2_, 19);
	}

	protected static void setBlock(IWorldWriter p_236913_0_, BlockPos p_236913_1_, BlockState p_236913_2_,
			MutableBoundingBox p_236913_3_) {
		TreeFeature.setBlockKnownShape(p_236913_0_, p_236913_1_, p_236913_2_);
		p_236913_3_.expand(new MutableBoundingBox(p_236913_1_, p_236913_1_));
	}

	static class TrunkFoliage {
		private final FoliagePlacer.Foliage attachment;
		private final int branchBase;

		public TrunkFoliage(BlockPos pos, int branchBase) {
			this.attachment = new FoliagePlacer.Foliage(pos, 0, false);
			this.branchBase = branchBase;
		}

		public int getBranchBase() {
			return this.branchBase;
		}
	}
}
