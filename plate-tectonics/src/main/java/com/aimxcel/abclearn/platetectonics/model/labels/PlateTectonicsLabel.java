package com.aimxcel.abclearn.platetectonics.model.labels;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.ValueNotifier;

public class PlateTectonicsLabel {
    // fired when this label is permanently removed from the model, so we can detach the necessary listeners
    public final ValueNotifier<PlateTectonicsLabel> disposed = new ValueNotifier<PlateTectonicsLabel>( this );
}
