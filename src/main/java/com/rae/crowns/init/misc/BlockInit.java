package com.rae.crowns.init.misc;

import com.rae.crowns.content.nuclear.AssemblyBlock;
import com.rae.crowns.content.nuclear.AssemblyBlockGenerator;
import com.rae.crowns.content.nuclear.UraniumOreBlock;
import com.rae.crowns.content.thermodynamics.conduction.HeatExchangerBlock;
import com.rae.crowns.content.thermodynamics.compressor.CompressorBlock;
import com.rae.crowns.content.thermodynamics.turbine.SteamCollectorBlock;
import com.rae.crowns.content.thermodynamics.turbine.SteamInputBlock;
import com.rae.crowns.content.thermodynamics.turbine.TurbineStageBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.Tags;

import java.util.function.ToIntFunction;

import static com.rae.crowns.CROWNS.REGISTRATE;
import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.foundation.data.TagGen.*;

@SuppressWarnings("ALL")
public class BlockInit {


    static {
        //REGISTRATE.setCreativeTab(CreativeModeTabsInit.NUCLEAR_TAB);
    }

    //to do list -> uranium ore (enrichment ?) + plutonium (created from 235) + depletion of fuel
    // control bar
    // thermal exchanger pipe ( entry, exit and middle : fluid tanks on both side entry and exit -> flow rate ? pressure loss ?)

    // turbine contraption ? -> turbine blade model + entry and exit ports
    // compressor ?

    public static final BlockEntry<HeatExchangerBlock> HEAT_EXCHANGER = REGISTRATE
            .block("heat_exchanger", HeatExchangerBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(axeOrPickaxe())
            .transform(displaySource(DisplaySourceInit.TEMPERATURE))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .model(AssetLookup::customItemModel)
            .build()
            .register();

    public static final BlockEntry<SteamInputBlock> STEAM_INPUT = REGISTRATE
            .block("steam_input", SteamInputBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .model(AssetLookup::customItemModel)
            .build()
            .register();

    public static final BlockEntry<SteamCollectorBlock> STEAM_COLLECTOR = REGISTRATE
            .block("steam_collector", SteamCollectorBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .model(AssetLookup::customItemModel)
            .build()
            .register();

    public static final BlockEntry<TurbineStageBlock> TURBINE_STAGE = REGISTRATE
            .block("turbine_stage",TurbineStageBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(axeOrPickaxe())
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .item()
            .model(AssetLookup::customItemModel)
            .build()
            .register();

    public static final BlockEntry<CompressorBlock> COMPRESSOR = REGISTRATE
            .block("compressor", CompressorBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(axeOrPickaxe())
            .properties(BlockBehaviour.Properties::noOcclusion)
            .blockstate(BlockStateGen.directionalBlockProvider(true))
            .item()
            .model(AssetLookup::customItemModel)
            .build()
            .register();

    public static final BlockEntry<AssemblyBlock> FUEL_ASSEMBLY = REGISTRATE
            .block("fuel_assembly", AssemblyBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(axeOrPickaxe()).tag(Tags.Blocks.NEEDS_WOOD_TOOL)
            .properties(p-> p.lightLevel(AssemblyBlock.getLightEmission()))
            .transform(displaySource(DisplaySourceInit.ACTIVITY))
            .transform(displaySource(DisplaySourceInit.TEMPERATURE))
            .blockstate(new AssemblyBlockGenerator()::generate)
            .item()
            .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/fuel_assembly/none")))
            .build()
            .register();

    public static final BlockEntry<UraniumOreBlock> DEEP_URANIUM_ORE = REGISTRATE
            .block("deepslate_uranium_ore", UraniumOreBlock::new)
            .initialProperties(()-> Blocks.DEEPSLATE)
            .properties(p->p.lightLevel(litBlockEmission(9)).strength(5.5F, 4.0F))
            .transform(pickaxeOnly()).tag(BlockTags.NEEDS_IRON_TOOL)
            .loot((lt, b) ->  {
                HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup = lt.getRegistries().lookupOrThrow(Registries.ENCHANTMENT);

                lt.add(b,
                        lt.createSilkTouchDispatchTable(b,
                                lt.applyExplosionDecay(b, LootItem.lootTableItem(ItemInit.RAW_URANIUM.get())
                                        .apply(ApplyBonusCount.addOreBonusCount(enchantmentRegistryLookup.getOrThrow(Enchantments.FORTUNE))))));
            })
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/uranium", "ores_in_ground/deepslate"))
            .tag(Tags.Items.ORES)
            .build()
            .register();

    public static final BlockEntry<UraniumOreBlock> URANIUM_ORE = REGISTRATE
            .block("uranium_ore", UraniumOreBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p->p.lightLevel(litBlockEmission(9)).strength(4,4))
            .transform(pickaxeOnly()).tag(BlockTags.NEEDS_IRON_TOOL)
            .loot((lt, b) ->  {
                HolderLookup.RegistryLookup<Enchantment> enchantmentRegistryLookup = lt.getRegistries().lookupOrThrow(Registries.ENCHANTMENT);

                lt.add(b,
                        lt.createSilkTouchDispatchTable(b,
                                lt.applyExplosionDecay(b, LootItem.lootTableItem(ItemInit.RAW_URANIUM.get())
                                        .apply(ApplyBonusCount.addOreBonusCount(enchantmentRegistryLookup.getOrThrow(Enchantments.FORTUNE))))));
            })
            .tag(Tags.Blocks.ORES)
            .transform(tagBlockAndItem("ores/uranium", "ores_in_ground/stone"))
            .tag(Tags.Items.ORES)
            .build()
            .register();


    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return (blockState) -> blockState.getValue(BlockStateProperties.LIT) ? lightLevel : 0;
    }
    public static void register() {}

}
