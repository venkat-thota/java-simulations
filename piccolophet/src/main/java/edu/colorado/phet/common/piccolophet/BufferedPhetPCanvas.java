
package edu.colorado.phet.common.piccolophet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;

import com.aimxcel.abclearn.common.abclearncommon.util.AbcLearnUtilities;

/**
 * This class explicitly buffers the graphics to resolve some issues
 * related to Piccolo (PSwing) scene graph rendering.
 *
 * @author Sam Reid
 * @deprecated see Unfuddle #1621: unbuffered JFreeChartNode doesn't work with buffered PCanvas on Mac
 */
@Deprecated
public class BufferedAbcLearnPCanvas extends AbcLearnPCanvas {

    private BufferedImage bufferedImage;

    public BufferedAbcLearnPCanvas() {
    }

    public BufferedAbcLearnPCanvas( Dimension2D pDimension ) {
        super( pDimension );
    }

    @Override
    public void paintComponent( Graphics g ) {
        if ( AbcLearnUtilities.isMacintosh() ) {
            // This workaround is not needed on mac, and introduces issues
            // (mostly slider knobs in weird places). See Unfuddle tickets
            // #1619 and #2848.
            super.paintComponent( g );
        }
        else {
            // Apply the workaround on windows and linux since they have
            // similar behaviors.
            if ( bufferedImage == null || bufferedImage.getWidth() != getWidth() || bufferedImage.getHeight() != getHeight() ) {
                bufferedImage = new BufferedImage( getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB );
            }
            Graphics2D bufferedGraphics = bufferedImage.createGraphics();
            bufferedGraphics.setClip( g.getClipBounds() );//TODO is this correct?
            super.paintComponent( bufferedGraphics );
            ( (Graphics2D) g ).drawRenderedImage( bufferedImage, new AffineTransform() );
            bufferedGraphics.dispose();
        }
    }
}
