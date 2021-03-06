package com.aimxcel.abclearn.buildamolecule.model;

import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.aimxcel.abclearn.buildamolecule.BuildAMoleculeStrings;

import com.aimxcel.abclearn.chemistry.model.Element;
import com.aimxcel.abclearn.common.aimxcelcommon.math.vector.Vector2D;
import com.aimxcel.abclearn.common.aimxcelcommon.model.SphereBucket;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.IClock;
import com.aimxcel.abclearn.aimxcel2dcore.util.PDimension;

/**
 * A bucket for atoms
 */
public class Bucket extends SphereBucket<Atom2D> {
    private final Element element;

    public Bucket( IClock clock, Element element, int quantity ) {
        // automatically compute the desired width with a height of 200;
        this( new PDimension( calculateIdealBucketWidth( element.getRadius(), quantity ), 200 ), clock, element, quantity );
    }

    
    public Bucket( Dimension2D size, IClock clock, Element element, int quantity ) {
        super( new Point2D.Double(), size, element.getColor(), BuildAMoleculeStrings.getAtomName( element ), element.getRadius() );
        this.element = element;

        for ( int i = 0; i < quantity; i++ ) {
            super.addParticleFirstOpen( new Atom2D( element, clock ), true );
        }
    }

    
    public static int calculateIdealBucketWidth( double radius, int quantity ) {
        // calculate atoms to go on the bottom row
        int numOnBottomRow = ( quantity <= 2 ) ? quantity : ( quantity / 2 + 1 );

        // figure out our width, accounting for radius-padding on each side
        double width = 2 * radius * ( numOnBottomRow + 1 );

        // add a bit, and make sure we don't go under 350
        return (int) Math.max( 350, width + 1 );
    }

    @Override public void setPosition( Point2D point ) {
        // when we move the bucket, we must also move our contained atoms
        Vector2D delta = new Vector2D( point ).minus( new Vector2D( getPosition() ) );
        for ( Atom2D atom : getAtoms() ) {
            atom.setPositionAndDestination( atom.getPosition().plus( delta ) );
        }
        super.setPosition( point );
    }

    public List<Atom2D> getAtoms() {
        return getParticleList();
    }

    /**
     * Instantly place the atom in the correct position, whether or not it is in the bucket
     *
     * @param atom The atom
     */
    public void placeAtom( final Atom2D atom ) {
        if ( containsParticle( atom ) ) {
            removeParticle( atom );
        }
        super.addParticleFirstOpen( atom, true );
    }

    public double getWidth() {
        return getContainerShape().getBounds().getWidth();
    }

    public Element getElement() {
        return element;
    }

}