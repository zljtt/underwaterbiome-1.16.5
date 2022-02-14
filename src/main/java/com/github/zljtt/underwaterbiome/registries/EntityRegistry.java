package com.github.zljtt.underwaterbiome.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.zljtt.underwaterbiome.UnderwaterBiome;
import com.github.zljtt.underwaterbiome.datagen.EntityLootTables;
import com.github.zljtt.underwaterbiome.entities.Conch;
import com.github.zljtt.underwaterbiome.entities.ConchSeaGrass;
import com.github.zljtt.underwaterbiome.entities.CreeperFish;
import com.github.zljtt.underwaterbiome.entities.LavaFish;
import com.github.zljtt.underwaterbiome.entities.Lurker;
import com.github.zljtt.underwaterbiome.entities.Ray;
import com.github.zljtt.underwaterbiome.entities.Shark;
import com.github.zljtt.underwaterbiome.entities.Sturgeon;
import com.github.zljtt.underwaterbiome.entities.renders.ConchRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.ConchSeaGrassRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.CreeperFishRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.LavaFishRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.LurkerRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.RayRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.SharkRenderer;
import com.github.zljtt.underwaterbiome.entities.renders.SturgeonRenderer;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegistry {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			UnderwaterBiome.MODID);

	public static final List<Runnable> RENDERERS = new ArrayList<Runnable>();
	public static final List<Runnable> ATTRIBUTES = new ArrayList<Runnable>();

	public static final RegistryObject<EntityType<Shark>> SHARK = registerAll("shark",
			EntityType.Builder.<Shark>of(Shark::new, EntityClassification.WATER_CREATURE).sized(2.0F, 1.3F),
			new SharkRenderer.RenderFactory(), () -> Shark.createMobAttributes(), () -> Shark.getLootTableBuilder());
	public static final RegistryObject<EntityType<Conch>> CONCH = registerAll("conch",
			EntityType.Builder.<Conch>of(Conch::new, EntityClassification.WATER_CREATURE).sized(0.6F, 0.4F),
			new ConchRenderer.RenderFactory(), () -> Conch.createMobAttributes(), () -> Conch.getLootTableBuilder());
	public static final RegistryObject<EntityType<ConchSeaGrass>> CONCH_SEA_GRASS = registerAll("conch_sea_grass",
			EntityType.Builder.<ConchSeaGrass>of(ConchSeaGrass::new, EntityClassification.WATER_CREATURE).sized(0.6F, 0.4F),
			new ConchSeaGrassRenderer.RenderFactory(), () -> ConchSeaGrass.createMobAttributes(),
			() -> ConchSeaGrass.getLootTableBuilder());
	public static final RegistryObject<EntityType<CreeperFish>> CREEPER_FISH = registerAll("creeper_fish",
			EntityType.Builder.<CreeperFish>of(CreeperFish::new, EntityClassification.WATER_CREATURE).sized(0.4F, 0.4F),
			new CreeperFishRenderer.RenderFactory(), () -> CreeperFish.createMobAttributes(),
			() -> CreeperFish.getLootTableBuilder());
	public static final RegistryObject<EntityType<LavaFish>> LAVA_FISH = registerAll("lava_fish",
			EntityType.Builder.<LavaFish>of(LavaFish::new, EntityClassification.WATER_CREATURE).sized(0.9F, 0.7F),
			new LavaFishRenderer.RenderFactory(), () -> LavaFish.createMobAttributes(), () -> LavaFish.getLootTableBuilder());
	public static final RegistryObject<EntityType<Lurker>> LURKER = registerAll("lurker",
			EntityType.Builder.<Lurker>of(Lurker::new, EntityClassification.WATER_CREATURE).sized(0.6F, 0.4F),
			new LurkerRenderer.RenderFactory(), () -> Lurker.createMobAttributes(), () -> Lurker.getLootTableBuilder());
	public static final RegistryObject<EntityType<Ray>> RAY = registerAll("ray",
			EntityType.Builder.<Ray>of(Ray::new, EntityClassification.WATER_CREATURE).sized(2.5F, 0.5F),
			new RayRenderer.RenderFactory(), () -> Ray.createMobAttributes(), () -> Ray.getSmallLootTableBuilder());
	public static final RegistryObject<EntityType<Ray>> RAY_MIDDLE = registerAll("ray_middle",
			EntityType.Builder.<Ray>of(Ray::new, EntityClassification.WATER_CREATURE).sized(4F, 0.8F),
			new RayRenderer.RenderFactory(), () -> Ray.createMobAttributes(), () -> Ray.getMiddleLootTableBuilder());
	public static final RegistryObject<EntityType<Ray>> RAY_LARGE = registerAll("ray_large",
			EntityType.Builder.<Ray>of(Ray::new, EntityClassification.WATER_CREATURE).sized(6.5F, 1.3F),
			new RayRenderer.RenderFactory(), () -> Ray.createMobAttributes(), () -> Ray.getLargeLootTableBuilder());
	public static final RegistryObject<EntityType<Sturgeon>> STURGEON = registerAll("sturgeon",
			EntityType.Builder.<Sturgeon>of(Sturgeon::new, EntityClassification.WATER_CREATURE).sized(1.0F, 0.8F),
			new SturgeonRenderer.RenderFactory(), () -> Sturgeon.createMobAttributes(), () -> Sturgeon.getLootTableBuilder());

//	public static final RegistryObject<EntityType<Shark>> SHARK = ENTITIES.register("shark", () -> EntityType.Builder
//			.<Shark>of(Shark::new, EntityClassification.WATER_CREATURE).sized(2.0F, 1.3F).build("shark"));
//
//	public static final RegistryObject<EntityType<Conch>> CONCH = ENTITIES.register("conch", () -> EntityType.Builder
//			.<Conch>of(Conch::new, EntityClassification.WATER_CREATURE).sized(0.6F, 0.4F).build("conch"));
//	public static final RegistryObject<EntityType<ConchSeaGrass>> CONCH_SEA_GRASS = ENTITIES.register("conch_sea_grass",
//			() -> EntityType.Builder.<ConchSeaGrass>of(ConchSeaGrass::new, EntityClassification.WATER_CREATURE)
//					.sized(0.6F, 0.4F).build("conch_sea_grass"));
//	public static final RegistryObject<EntityType<CreeperFish>> CREEPER_FISH = ENTITIES.register("creeper_fish",
//			() -> EntityType.Builder.<CreeperFish>of(CreeperFish::new, EntityClassification.WATER_CREATURE)
//					.sized(0.4F, 0.4F).build("creeper_fish"));
//	public static final RegistryObject<EntityType<LavaFish>> LAVA_FISH = ENTITIES.register("lava_fish",
//			() -> EntityType.Builder.<LavaFish>of(LavaFish::new, EntityClassification.WATER_CREATURE).sized(0.9F, 0.7F)
//					.build("lava_fish"));
//	public static final RegistryObject<EntityType<Lurker>> LURKER = ENTITIES.register("lurker", () -> EntityType.Builder
//			.<Lurker>of(Lurker::new, EntityClassification.WATER_CREATURE).sized(0.6F, 0.4F).build("lurker"));
//	public static final RegistryObject<EntityType<Ray>> RAY = ENTITIES.register("ray", () -> EntityType.Builder
//			.<Ray>of(Ray::new, EntityClassification.WATER_CREATURE).sized(2.0F, 0.7F).build("ray"));
//	public static final RegistryObject<EntityType<Sturgeon>> STURGEON = ENTITIES.register("sturgeon",
//			() -> EntityType.Builder.<Sturgeon>of(Sturgeon::new, EntityClassification.WATER_CREATURE).sized(1.0F, 0.8F)
//					.build("sturgeon"));

	public static EntityType<?> get(ResourceLocation rl) {
		return RegistryObject.of(rl, ForgeRegistries.ENTITIES).get();
	}

	public static void registerModel() {
		for (Runnable entry : RENDERERS) {
			entry.run();
		}
//		RenderingRegistry.registerEntityRenderingHandler(SHARK.get(), new SharkRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(CONCH.get(), new ConchRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(CONCH_SEA_GRASS.get(),
//				new ConchSeaGrassRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(CREEPER_FISH.get(), new CreeperFishRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(LAVA_FISH.get(), new LavaFishRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(LURKER.get(), new LurkerRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(RAY.get(), new RayRenderer.RenderFactory());
//		RenderingRegistry.registerEntityRenderingHandler(STURGEON.get(), new SturgeonRenderer.RenderFactory());

	}

	public static void registerAttributes() {
		for (Runnable entry : ATTRIBUTES) {
			entry.run();
		}
//		GlobalEntityTypeAttributes.put(SHARK.get(), Shark.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(CONCH.get(), Conch.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(CONCH_SEA_GRASS.get(), ConchSeaGrass.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(CREEPER_FISH.get(), CreeperFish.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(LAVA_FISH.get(), LavaFish.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(LURKER.get(), Lurker.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(RAY.get(), Ray.createMobAttributes().build());
//		GlobalEntityTypeAttributes.put(STURGEON.get(), Sturgeon.createMobAttributes().build());

	}

	@SuppressWarnings("deprecation")
	private static <T extends LivingEntity> RegistryObject<EntityType<T>> registerAll(String name, EntityType.Builder<T> builder,
			IRenderFactory<? super T> renderFactory, Supplier<MutableAttribute> attributes,
			Supplier<LootTable.Builder> loottable) {
		RegistryObject<EntityType<T>> entityType = ENTITIES.register(name, () -> builder.build(name));
		RENDERERS.add(() -> RenderingRegistry.registerEntityRenderingHandler(entityType.get(), renderFactory));
		ATTRIBUTES.add(() -> GlobalEntityTypeAttributes.put(entityType.get(), attributes.get().build()));
		EntityLootTables.ENTITY_LOOTS.put(new ResourceLocation(UnderwaterBiome.MODID, name), loottable);

		return entityType;
	}
}
