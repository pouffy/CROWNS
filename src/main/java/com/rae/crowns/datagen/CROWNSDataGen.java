package com.rae.crowns.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rae.crowns.CROWNS;
import com.rae.crowns.content.ponder.CROWNSPonderPlugin;
import com.rae.crowns.datagen.recipe.CROWNSMechanicalCraftingGen;
import com.rae.crowns.datagen.recipe.CROWNSStandardRecipeGen;
import com.simibubi.create.foundation.data.recipe.CreateMechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.data.recipe.CreateStandardRecipeGen;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static com.rae.crowns.CROWNS.REGISTRATE;

public class CROWNSDataGen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(CROWNS.MODID))
            addExtraRegistrateData();
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new CROWNSStandardRecipeGen(output, lookupProvider));
        generator.addProvider(event.includeServer(), new CROWNSMechanicalCraftingGen(output, lookupProvider));

    }

    private static void addExtraRegistrateData() {
        CROWNSRegistrateTags.addGenerators();

        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            provideDefaultLang("interface", langConsumer);
            provideDefaultLang("tooltips", langConsumer);

            providePonderLang(langConsumer);
        });
    }

    private static void providePonderLang(BiConsumer<String, String> consumer) {
        PonderIndex.addPlugin(new CROWNSPonderPlugin());

        PonderIndex.getLangAccess().provideLang(CROWNS.MODID, consumer);
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/crowns/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }
}
