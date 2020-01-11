
package com.aimxcel.abclearn.eatingandexercise.control;

import com.aimxcel.abclearn.eatingandexercise.model.CalorieSet;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;

public class PlateTopSummaryNode extends PNode {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PNode layer = new PNode();
    private CalorieSet calorieSet;
    private PNode plate;
    private boolean showItems = false;

    public boolean isShowItems() {
        return showItems;
    }

    public PlateTopSummaryNode( CalorieSet calorieSet, PNode plate ) {
        this.calorieSet = calorieSet;
        this.plate = plate;
        addChild( layer );
        for ( int i = 0; i < calorieSet.size(); i++ ) {
            addItem( calorieSet.getItem( i ) );
        }
        calorieSet.addListener( new CalorieSet.Adapter() {
            public void itemAdded( CaloricItem item ) {
                addItem( item );
            }

            public void itemRemoved( CaloricItem item ) {
                for ( int i = 0; i < layer.getChildrenCount(); i++ ) {
                    SummaryItemNode child = (SummaryItemNode) layer.getChild( i );
                    if ( child.getItem() == item ) {
                        layer.removeChild( child );
                        break;//remove first match
                    }
                }
                relayout();
            }
        } );
        relayout();
    }

    public void addItem( final CaloricItem item ) {
        if ( showItems ) {
            SummaryItemNode summaryItemNode = new SummaryItemNode( item, 1 );
            summaryItemNode.addInputEventListener( new PBasicInputEventHandler() {
                public void mousePressed( PInputEvent event ) {
                    calorieSet.removeItem( item );
                }
            } );
            layer.addChild( summaryItemNode );
        }
        relayout();
    }

    public void relayout() {
        if ( layer.getChildrenCount() > 0 ) {
            layer.getChild( 0 ).setOffset( plate.getFullBounds().getX(), plate.getFullBounds().getY() - layer.getChild( 0 ).getFullBounds().getHeight() );
            for ( int i = 1; i < layer.getChildrenCount(); i++ ) {
                PNode prevNode = layer.getChild( i - 1 );
                layer.getChild( i ).setOffset( prevNode.getOffset().getX(), prevNode.getFullBounds().getMinY() - layer.getChild( i ).getFullBounds().getHeight() );
            }
        }

    }

}