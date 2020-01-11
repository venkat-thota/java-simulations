package com.aimxcel.abclearn.theramp.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import com.aimxcel.abclearn.common.aimxcelcommon.model.ModelElement;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.ShadowHTMLNode;
import com.aimxcel.abclearn.theramp.RampModule;
import com.aimxcel.abclearn.theramp.TheRampStrings;
import com.aimxcel.abclearn.theramp.model.RampPhysicalModel;
import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwing;


public class OverheatButton extends PNode {
    private RampPhysicalModel rampPhysicalModel;
    private RampPanel rampPanel;
    private RampModule module;

    public OverheatButton( final RampPanel rampPanel, final RampPhysicalModel rampPhysicalModel, RampModule module ) {
        this.module = module;
        this.rampPhysicalModel = rampPhysicalModel;
        this.rampPanel = rampPanel;
        ShadowHTMLNode shadowHTMLNode = new ShadowHTMLNode( TheRampStrings.getString( "message.overheated" ) );
        shadowHTMLNode.setColor( Color.red );
        shadowHTMLNode.setFont( new Font( AimxcelFont.getDefaultFontName(), Font.BOLD, 14 ) );
        addChild( shadowHTMLNode );
        JButton overheat = new JButton( TheRampStrings.getString( "controls.cool-ramp" ) );
        overheat.setFont( RampFontSet.getFontSet().getNormalButtonFont() );
        overheat.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                rampPanel.getRampModule().clearHeat();
            }
        } );
        PSwing buttonGraphic = new PSwing( overheat );
        buttonGraphic.setOffset( 0, shadowHTMLNode.getHeight() );
        addChild( buttonGraphic );
        module.getModel().addModelElement( new ModelElement() {
            public void stepInTime( double dt ) {
                update();
            }
        } );
        update();
        buttonGraphic.setOffset( 0, shadowHTMLNode.getFullBounds().getHeight() + 5 );
    }

    private void update() {

        double max = rampPanel.getOverheatEnergy();

        if ( rampPhysicalModel.getThermalEnergy() >= max && !getVisible() && module.numMaximizedBarGraphs() > 0 ) {
            setVisible( true );
            setPickable( true );
            setChildrenPickable( true );

            Rectangle2D r = rampPanel.getBarGraphSuite().getGlobalFullBounds();
            globalToLocal( r );

            localToParent( r );
            setOffset( r.getX() + 20, rampPanel.getHeight() / 2 );
        }
        else if ( rampPhysicalModel.getThermalEnergy() < max ) {
            setVisible( false );
            setPickable( false );
            setChildrenPickable( false );
        }
    }
}
