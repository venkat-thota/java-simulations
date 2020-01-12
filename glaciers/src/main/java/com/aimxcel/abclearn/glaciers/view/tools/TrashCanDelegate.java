package com.aimxcel.abclearn.glaciers.view.tools;

import java.awt.geom.Point2D;

import com.aimxcel.abclearn.glaciers.model.IToolProducer;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.activities.PActivity;


public class TrashCanDelegate {
    
    private PNode _trashCanNode;
    private IToolProducer _toolProducer;

    /**
     * Constructor
     * 
     * @param trashCanNode node that represents the trash can
     * @param toolProducer responsible for deleting tools
     */
    public TrashCanDelegate( PNode trashCanNode, IToolProducer toolProducer ) {
        _trashCanNode = trashCanNode;
        _toolProducer = toolProducer;
    }

    /**
     * Is a point in the trash?
     * 
     * @param p a position transformed through the view transform of the bottom camera
     * @return
     */
    public boolean isInTrash( Point2D p ) {
        return _trashCanNode.getGlobalFullBounds().contains( p ); 
    }
    
    /**
     * Deletes a specified tool.
     * <p>
     * Shows an animation of a tool shrinking towards a point.
     * Then deletes the tool.
     * 
     * @param toolNode the tool to delete
     * @param p a position transformed through the view transform of the bottom camera
     */
    public void delete( final AbstractToolNode toolNode, Point2D p ) {
        
        assert ( isInTrash( p ) );
        
        // shrink the tool to the specified point
        final double scale = 0.1;
        final long duration = 300; // ms
        PActivity a1 = toolNode.animateToPositionScaleRotation( p.getX(), p.getY(), scale, toolNode.getRotation(), duration );
        
        // then delete the tool
        PActivity a2 = new PActivity( -1 ) {
            protected void activityStep( long elapsedTime ) {
                _toolProducer.removeTool( toolNode.getTool() );
                terminate(); // ends this activity
            }
        };
        _trashCanNode.addActivity( a2 );
        a2.startAfter( a1 );
    }
}
