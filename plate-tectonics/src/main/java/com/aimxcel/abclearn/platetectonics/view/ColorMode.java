package com.aimxcel.abclearn.platetectonics.view;

import com.aimxcel.abclearn.platetectonics.PlateTectonicsResources.Strings;
import com.aimxcel.abclearn.platetectonics.view.materials.CombinedMaterial;
import com.aimxcel.abclearn.platetectonics.view.materials.DensityMaterial;
import com.aimxcel.abclearn.platetectonics.view.materials.EarthMaterial;
import com.aimxcel.abclearn.platetectonics.view.materials.TemperatureMaterial;

public enum ColorMode {
    // darker == more dense
    DENSITY( new DensityMaterial(), Strings.LESS_DENSE, Strings.MORE_DENSE ),

    // redder == warmer
    TEMPERATURE( new TemperatureMaterial(), Strings.COOL, Strings.WARM ),

    // heuristic view that combines both
    COMBINED( new CombinedMaterial(), null, null );

    private final EarthMaterial material;
    private final String minString;
    private final String maxString;

    /**
     * @param material  The material to be used in the graphics engine
     * @param minString The label for the "low" temperature / density, used for the legend
     * @param maxString The label for the "high" temperature / density, used for the legend
     */
    private ColorMode( EarthMaterial material, String minString, String maxString ) {
        this.material = material;
        this.minString = minString;
        this.maxString = maxString;
    }

    public EarthMaterial getMaterial() {
        return material;
    }

    public String getMinString() {
        return minString;
    }

    public String getMaxString() {
        return maxString;
    }
}
