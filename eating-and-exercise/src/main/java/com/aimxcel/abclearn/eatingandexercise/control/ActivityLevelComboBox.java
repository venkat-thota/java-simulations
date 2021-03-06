
package com.aimxcel.abclearn.eatingandexercise.control;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.swing.AimxcelJComboBox;
import com.aimxcel.abclearn.eatingandexercise.model.Human;
import com.aimxcel.abclearn.eatingandexercise.module.eatingandexercise.EatingAndExerciseCanvas;

public class ActivityLevelComboBox extends AimxcelJComboBox {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EatingAndExerciseCanvas canvas;
    private Human human;

    public ActivityLevelComboBox( EatingAndExerciseCanvas canvas, final Human human ) {
        this.human = human;
        this.canvas = canvas;
        for ( int i = 0; i < Activity.DEFAULT_ACTIVITY_LEVELS.length; i++ ) {
            addItem( Activity.DEFAULT_ACTIVITY_LEVELS[i] );
        }
        updateSelectedItem();

        setFont( new AimxcelFont( 13, true ) );
        addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent e ) {
                human.setActivityLevel( ( (Activity) e.getItem() ) );
            }
        } );
        //todo: remove this workaround, which is necessary since piccolo pswing doesn't support jcombobox or pcombobox embedded in container within pswing
        setUI( new MyComboBoxUI() );
//        setSelectedItem(  );
        human.addListener( new Human.Adapter() {
            public void activityLevelChanged() {
                updateSelectedItem();
            }
        } );
    }

    private void updateSelectedItem() {
        for ( int i = 0; i < Activity.DEFAULT_ACTIVITY_LEVELS.length; i++ ) {
            if ( Activity.DEFAULT_ACTIVITY_LEVELS[i].getValue() == human.getActivityLevel() ) {
                setSelectedItem( Activity.DEFAULT_ACTIVITY_LEVELS[i] );
            }
        }
    }

    protected class MyComboBoxUI extends BasicComboBoxUI {
        protected ComboPopup createPopup() {
            return new MyComboPopup( comboBox );
        }
    }

    protected class MyComboPopup extends BasicComboPopup {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyComboPopup( JComboBox combo ) {
            super( combo );
        }

        protected Rectangle computePopupBounds( int px, int py, int pw, int ph ) {
            Rectangle sup = super.computePopupBounds( px, py, pw, ph );
            double y = canvas.getControlPanelY();
            Rectangle2D r = new Rectangle2D.Double( 0, y + sup.y, 0, 0 );
            return new Rectangle( (int) r.getX(), (int) r.getMaxY(), (int) sup.getWidth(), (int) sup.getHeight() );
        }
    }

}