package com.github.zljtt.underwaterbiome.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class FloatingCore extends BlockBase {

	public FloatingCore() {
		super(Properties.of(Material.STONE).strength(2F, 6F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)
				.harvestLevel(2));
	}

}
