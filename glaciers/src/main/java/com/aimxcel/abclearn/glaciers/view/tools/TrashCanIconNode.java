
package com.aimxcel.abclearn.glaciers.view.tools;

import com.aimxcel.abclearn.glaciers.GlaciersImages;
import com.aimxcel.abclearn.glaciers.model.IToolProducer;


public class TrashCanIconNode extends AbstractToolIconNode {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TrashCanDelegate _trashCanDelegate;
    
    /**
     * Constructor.
     * 
     * @param toolProducer
     */
    public TrashCanIconNode( final IToolProducer toolProducer ) {
        super( GlaciersImages.TRASH_CAN );
        _trashCanDelegate = new TrashCanDelegate( this, toolProducer );
    }
    
    /**
     * Gets the delegate that handles the specifics of trashing tools.
     * 
     * @return TrashCanDelegate
     */
    public TrashCanDelegate getTrashCanDelegate() {
        return _trashCanDelegate;
    }
}