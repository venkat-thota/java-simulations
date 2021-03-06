package com.aimxcel.abclearn.platetectonics.model.regions;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.aimxcel.abclearn.platetectonics.model.Sample;
import com.aimxcel.abclearn.platetectonics.util.Side;

import com.aimxcel.abclearn.common.aimxcelcommon.model.event.ValueNotifier;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.util.SimpleObserver;

public class CrossSectionStrip {
    public final List<Sample> topPoints = new LinkedList<Sample>();
    public final List<Sample> bottomPoints = new LinkedList<Sample>();

    public final ValueNotifier<CrossSectionStrip> changed = new ValueNotifier<CrossSectionStrip>( this );

    // fires when this strip should be moved to the front
    public final ValueNotifier<CrossSectionStrip> moveToFrontNotifier = new ValueNotifier<CrossSectionStrip>( this );

    // fired when the strip is permanently removed from the model, so we can detach the necessary listeners
    public final ValueNotifier<CrossSectionStrip> disposed = new ValueNotifier<CrossSectionStrip>( this );

    public final Property<Float> alpha = new Property<Float>( 1f );

    public CrossSectionStrip() {
        alpha.addObserver( new SimpleObserver() {
            public void update() {
                changed.updateListeners();
            }
        } );
    }

    public CrossSectionStrip( List<Sample> topPoints, List<Sample> bottomPoints ) {
        ListIterator<Sample> topIter = topPoints.listIterator();
        ListIterator<Sample> bottomIter = bottomPoints.listIterator();

        while ( topIter.hasNext() ) {
            addPatch( Side.RIGHT, topIter.next(), bottomIter.next() );
        }
    }

    public int getLength() {
        return topPoints.size();
    }

    public int getNumberOfVertices() {
        return topPoints.size() * 2;
    }

    public void addPatch( Side side, Sample top, Sample bottom ) {
        side.addToList( topPoints, top );
        side.addToList( bottomPoints, bottom );
    }

    public void removePatch( Side side ) {
        side.removeFromList( topPoints );
        side.removeFromList( bottomPoints );
    }

    public void update() {
        changed.updateListeners();
    }
}
