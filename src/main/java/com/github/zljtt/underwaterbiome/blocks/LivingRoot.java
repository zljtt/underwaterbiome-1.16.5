package com.github.zljtt.underwaterbiome.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class LivingRoot extends BlockBase {

	public LivingRoot() {
		super(Properties.of(Material.WOOD).strength(1F, 1F).sound(SoundType.WOOD).harvestTool(ToolType.AXE));
	}

}
