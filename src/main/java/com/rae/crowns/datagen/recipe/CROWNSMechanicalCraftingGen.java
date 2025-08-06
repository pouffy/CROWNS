package com.rae.crowns.datagen.recipe;

import com.rae.crowns.CROWNS;
import com.rae.crowns.init.misc.BlockInit;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.recipe.MechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public final class CROWNSMechanicalCraftingGen extends MechanicalCraftingRecipeGen {

    GeneratedRecipe

    TURBINE_STAGE = create(BlockInit.TURBINE_STAGE::get)
            .recipe(b -> b
                    .key('P', Ingredient.of(AllTags.commonItemTag("plates/iron")))
                    .key('C', Ingredient.of(AllBlocks.BRASS_CASING))
                    .key('I', Ingredient.of(AllTags.commonItemTag("ingots/iron")))
                    .patternLine("P P P")
                    .patternLine(" III ")
                    .patternLine("PICIP")
                    .patternLine(" III ")
                    .patternLine("P P P")
                    .disallowMirrored());

    public CROWNSMechanicalCraftingGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CROWNS.MODID);
    }
}
