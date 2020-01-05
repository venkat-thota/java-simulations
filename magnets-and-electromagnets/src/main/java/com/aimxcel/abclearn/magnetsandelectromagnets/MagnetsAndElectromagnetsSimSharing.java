
package com.aimxcel.abclearn.magnetsandelectromagnets;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ISystemComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;


public class MagnetsAndElectromagnetsSimSharing {

    public static enum Components implements IUserComponent, ISystemComponent {

        // Tabs
        barMagnetTab,
        pickupCoilTab,
        electromagnetTab,
        transformerTab,
        generatorTab,

        // Panels
        barMagnetControlPanel,
        pickupCoilControlPanel,
        electromagnetControlPanel,

        // Controls
        strengthControl,
        flipPolarityButton,
        seeInsideMagnetCheckBox,
        showFieldCheckBox,
        showCompassCheckBox,
        showFieldMeterCheckBox,
        showEarthCheckBox,
        showElectrons,
        dcRadioButton,
        acRadioButton,
        loopsSpinner,
        lightbuldRadioButton,
        voltmeterRadioButton,
        loopAreaControl,
        okButton,
        cancelButton,
        needleSpacingSlider,
        needleSizeSlider,
        maxAmplitudeSlider,
        frequencySlider,
        faucetSlider,
        batterySlider,

        // Menu items
        backgroundColorMenuItem,
        gridControlsMenuItem,

        // Secondary windows
        backgroundColorDialog,
        fieldControlsDialog,

        // Sprites
        barMagnet,
        pickupCoil,
        fieldMeter,
        compass,
        electromagnet,
        waterWheel,
        faucet,
        acPowerSupply,
        bField
    }
}
