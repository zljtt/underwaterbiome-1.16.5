package com.github.zljtt.underwaterbiome.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemBase extends Item implements IItemDataProvider {

	private boolean commented;

	public ItemBase(Properties properties, boolean commented) {
		super(properties);
		this.commented = commented;
	}

	public ItemBase(Properties properties) {
		super(properties);
		this.commented = false;
	}

	@Override
	public void appendHoverText(ItemStack stack, World level, List<ITextComponent> com, ITooltipFlag flag) {
		if (commented) {
			com.add(new TranslationTextComponent("tooltip." + this.getRegistryName().getPath()));
		}
		super.appendHoverText(stack, level, com, flag);
	}
}
