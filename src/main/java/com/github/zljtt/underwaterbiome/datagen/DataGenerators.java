package com.github.zljtt.underwaterbiome.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		System.out.println("Data Generation Start --");
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		if (event.includeClient()) {
			generator.addProvider(new BlockModels(generator, existingFileHelper));
			generator.addProvider(new ItemModels(generator, existingFileHelper));
			generator.addProvider(new BlockStates(generator, existingFileHelper));
		}
		if (event.includeServer()) {
			generator.addProvider(new AllLootTables(generator));
			generator.addProvider(new Biomes(generator));
			BlockTags blockTagsProvider = new BlockTags(generator, existingFileHelper);
			generator.addProvider(blockTagsProvider);
			generator.addProvider(new ItemTags(generator, blockTagsProvider, existingFileHelper));

		}
	}
}