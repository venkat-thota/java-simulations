

package com.aimxcel.abclearn.core.aimxcelcore;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aimxcel.abclearn.common.aimxcelcommon.application.Module;
import com.aimxcel.abclearn.common.aimxcelcommon.model.clock.ConstantDtClock;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.ChangeObserver;
import com.aimxcel.abclearn.common.aimxcelcommon.model.property.Property;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.servicemanager.AimxcelServiceManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.SimSharingManager;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterSet;
import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserComponentTypes;
import com.aimxcel.abclearn.common.aimxcelcommon.view.LogoPanel;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.AimxcelFont;
import com.aimxcel.abclearn.core.aimxcelcore.event.CursorHandler;
import com.aimxcel.abclearn.core.aimxcelcore.event.ToolTipHandler;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.AimxcelPPath;
import com.aimxcel.abclearn.core.aimxcelcore.nodes.HTMLNode;

import com.aimxcel.abclearn.aimxcel2dcore.PNode;
import com.aimxcel.abclearn.aimxcel2dcore.event.PBasicInputEventHandler;
import com.aimxcel.abclearn.aimxcel2dcore.event.PInputEvent;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PImage;
import com.aimxcel.abclearn.aimxcel2dcore.nodes.PPath;
import com.aimxcel.abclearn.aimxcel2dextra.pswing.PSwingCanvas;

import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.ParameterKeys.text;
import static com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.UserActions.pressed;



public class AimxcelTabbedPane extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String IMAGE_AIMXCEL_LOGO = LogoPanel.IMAGE_AIMXCEL_LOGO;

    /* Default property values */
    public static final Font DEFAULT_TAB_FONT = new AimxcelFont( 16, true /* bold */ );
    public static final Color DEFAULT_BACKGROUND_COLOR = new Color( 240, 240, 240 );
    public static final Color DEFAULT_SELECTED_TAB_COLOR = new Color( 180, 205, 255 );
    public static final Color DEFAULT_UNSELECTED_TAB_COLOR = new Color( 100, 125, 255 );
    public static final Color DEFAULT_SELECTED_TEXT_COLOR = Color.BLACK;
    public static final Color DEFAULT_UNSELECTED_TEXT_COLOR = new Color( 40, 40, 40 );

    private TabPane tabPane;
    private JComponent component;
    private Font tabFont;
    private Color selectedTabColor, unselectedTabColor;
    private Color selectedTextColor, unselectedTextColor;
    private ArrayList changeListeners = new ArrayList();

    /**
     * Constructs a AimxcelTabbedPane using the default color scheme.
     */
    public AimxcelTabbedPane() {
        super( new BorderLayout() );
        setBackground( DEFAULT_BACKGROUND_COLOR );

        this.tabFont = DEFAULT_TAB_FONT;
        this.selectedTabColor = DEFAULT_SELECTED_TAB_COLOR;
        this.unselectedTabColor = DEFAULT_UNSELECTED_TAB_COLOR;
        this.selectedTextColor = DEFAULT_SELECTED_TEXT_COLOR;
        this.unselectedTextColor = DEFAULT_UNSELECTED_TEXT_COLOR;

        tabPane = new TabPane( selectedTabColor, unselectedTabColor );
        add( tabPane, BorderLayout.NORTH );
        ComponentListener relayoutHandler = new ComponentListener() {
            public void componentHidden( ComponentEvent e ) {
            }

            public void componentMoved( ComponentEvent e ) {
            }

            public void componentResized( ComponentEvent e ) {
                relayoutComponents();
            }

            public void componentShown( ComponentEvent e ) {
                relayoutComponents();
            }
        };
        addComponentListener( relayoutHandler );

        relayoutComponents();
    }

    /**
     * See #2015, ensure that scaling and layout are updated when bounds change.
     * This must happen synchronously; if you schedule it in a ComponentEvent,
     * you will see the scaling and layout.
     */
    @Override
    public void setBounds( int x, int y, int w, int h ) {
        super.setBounds( x, y, w, h );
        relayoutComponents();
    }

    public void setLogoVisible( boolean logoVisible ) {
        tabPane.setLogoVisible( logoVisible );
    }

    /**
     * Returns the number of tabs.
     *
     * @return the number of tabs.
     */
    public int getTabCount() {
        return tabPane.getTabCount();
    }

    /**
     * Sets the bounds of each tab JComponent to mimic behavior in JTabbedPane
     */
    private void relayoutComponents() {
//todo I originally believed this to be necessary in QWI to get tabs content JComponent to appear at the right size the first time they appear, but this code causes an error in 1.5: no content JComponent shows initially.
//todo This fixed version works correctly for the TestAimxcelTabbedPane test under 1.4 and 1.5, so maybe the fault was originally with QWI.  This warrants further investigation.
        if ( component != null ) {
            Rectangle bounds = component.getBounds();
            for ( int i = 0; i < getTabCount(); i++ ) {
                tabPane.getTabs()[i].getComponent().setSize( bounds.width, bounds.height );//to mimic behavior in JTabbedPane
                updateLayout( tabPane.getTabs()[i].getComponent() );
            }
        }
    }

    /**
     * Todo: Identify why this workaround is necessary and delete it.
     * <p/>
     * This is a utility function for ensuring that all layouts in this component hierarchy
     * have a chance to reflect their new parent's sizes.
     * <p/>
     * We added this to solve the problem visible in bound states on 6-21-2006 5:24pm that
     * when changing tabs, there would be a visible resize after selection.
     *
     * @param component
     */
    private void updateLayout( Component component ) {
        component.doLayout();
        if ( component instanceof Container ) {
            Container c = (Container) component;
            for ( int i = 0; i < c.getComponentCount(); i++ ) {
                updateLayout( c.getComponent( i ) );
            }
        }
    }

    /**
     * Removes the tab at the specified index.  If the removed tab was selected and there are still remaining tabs,
     * a new tab becomes selected.  If one exists, the next tab will become selected, otherwise the previous tab becomes selected.
     *
     * @param i
     */
    public void removeTabAt( int i ) {
        AbstractTabNode tabToRemove = getTab( i );
        AbstractTabNode selectedTab = getSelectedTab();

        tabPane.removeTabAt( i );
        if ( selectedTab == tabToRemove && getTabCount() > 0 ) {//need to set a new selected tab.
            if ( i < getTabCount() ) {
                setSelectedIndex( i );
            }
            else {
                setSelectedIndex( i - 1 );
            }
        }
    }

    /**
     * Adds a ChangeListener to observe changes in tab selection.
     *
     * @param changeListener
     */
    public void addChangeListener( ChangeListener changeListener ) {
        changeListeners.add( changeListener );
    }

    /**
     * Returns the index of the selected tab.
     *
     * @return the index of the selected tab.
     */
    public int getSelectedIndex() {
        return tabPane.getSelectedIndex();
    }

    /**
     * Sets the base foreground color for selected tabs.
     *
     * @param color
     */
    public void setSelectedTabColor( Color color ) {
        this.selectedTabColor = color;
        tabPane.setSelectedTabColor( color );
    }

    /**
     * Gets the base foreground color for selected tabs.
     *
     * @return Color
     */
    public Color getSelectedTabColor() {
        return selectedTabColor;
    }

    public void setUnselectedTabColor( Color color ) {
        this.unselectedTabColor = color;
        tabPane.setUnselectedTabColor( color );
    }

    public Color getUnselectedTabColor() {
        return unselectedTabColor;
    }

    public void setSelectedTextColor( Color color ) {
        this.selectedTextColor = color;
        tabPane.setSelectedTextColor( color );
    }

    public Color getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setUnselectedTextColor( Color color ) {
        this.unselectedTextColor = color;
        tabPane.setUnselectedTextColor( color );
    }

    public Color getUnselectedTextColor() {
        return unselectedTextColor;
    }

    /**
     * Adds the text or HTML label as a tab for the specified component.  If this is the first tab, it becomes selected.
     *
     * @param title   text or HTML
     * @param content
     */
    public void addTab( IUserComponent userComponent, String title, JComponent content ) {
        final AbstractTabNode tab = createTab( userComponent, title, content );
        tab.addInputEventListener( new TabInputListener( tab ) );
        if ( tabPane.getTabs().length == 0 ) {
            setSelectedTab( tab );
            tabPane.setSelectedTab( tab );
        }
        else {
            tab.setSelected( false );
        }
        tabPane.addTab( tab );
        relayoutComponents();
    }

    /**
     * Creates an AbstractTabNode from the specified title and component.
     * This implementation creates an HTMLTabNode.
     * Override if you want some other type of tab.
     *
     * @param title
     * @param content
     * @return the AbstractTabNode
     */
    protected AbstractTabNode createTab( IUserComponent userComponent, String title, JComponent content ) {
        return new HTMLTabNode( userComponent, title, content, selectedTabColor, unselectedTabColor, selectedTextColor, unselectedTextColor, tabFont );
    }

    /**
     * Sets the selected tab to be the one with the specified index.
     *
     * @param index
     */
    public void setSelectedIndex( int index ) {
        if ( index < 0 || index >= getTabCount() ) {
            throw new RuntimeException( "Illegal tab index: " + index + ", tab count=" + getTabCount() );
        }
        setSelectedTab( tabPane.getTabs()[index] );
    }

    /**
     * Sets the selected tab.  Should not be used by normal clients.
     *
     * @param tab
     */
    protected void setSelectedTab( AbstractTabNode tab ) {
        setComponent( tab.getComponent() );
        tab.setSelected( true );
        for ( int i = 0; i < tabPane.getTabs().length; i++ ) {
            AbstractTabNode t = tabPane.getTabs()[i];
            if ( t != tab ) {
                t.setSelected( false );
            }
        }
        tabPane.setSelectedTab( tab );
        notifySelectionChanged();
    }

    /**
     * Sends a message to change listeners that the selected tab changed.  This method is called internally, and should
     * not normally be called directly.
     */
    private void notifySelectionChanged() {
        ChangeEvent changeEvent = new ChangeEvent( this );
        for ( int i = 0; i < changeListeners.size(); i++ ) {
            ChangeListener changeListener = (ChangeListener) changeListeners.get( i );
            changeListener.stateChanged( changeEvent );
        }
    }

    /**
     * Returns the selected tab object, may be null.
     *
     * @return selected tab object, may be null.
     */
    private AbstractTabNode getSelectedTab() {
        return tabPane.getSelectedTab();
    }

    /**
     * Sets the main body component of the AimxcelTabbedPane.
     *
     * @param component
     */
    private void setComponent( final JComponent component ) {
        if ( component != this.component ) {
            if ( this.component != null ) {
                remove( this.component );
            }
            this.component = component;
            add( component, BorderLayout.CENTER );
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the tab at the specified index.
     *
     * @param i
     * @return the tab at the specified index.
     */
    public AbstractTabNode getTab( int i ) {
        return tabPane.getTab( i );
    }

    /**
     * Sets the font for all tabs.
     *
     * @param font
     */
    public void setTabFont( Font font ) {
        this.tabFont = font;
        for ( int i = 0; i < getTabCount(); i++ ) {
            getTab( i ).setFont( font );
        }
        tabPane.relayout();
        revalidate();
    }

    /**
     * Returns the text of the specified tab.
     *
     * @param i the tab index
     * @return the text of the specified tab.
     */
    public String getTitleAt( int i ) {
        return getTab( i ).getText();
    }

    public boolean getLogoVisible() {
        return tabPane.getLogoVisible();
    }

    /**
     * This AbstractTabNode renders HTML.
     */
    private static class HTMLTabNode extends AbstractTabNode {
        private HTMLNode htmlNode;

        public HTMLTabNode( IUserComponent userComponent, String html, JComponent component, Color selectedTabColor, Color unselectedTabColor, Color selectedTextColor, Color unselectedTextColor, Font tabFont ) {
            super( userComponent, html, component, selectedTabColor, unselectedTabColor, selectedTextColor, unselectedTextColor, tabFont );
        }

        protected PNode createTextNode( String html ) {
            this.htmlNode = new HTMLNode( html, getSelectedTextColor(), getTabFont() );
            return this.htmlNode;
        }

        protected void updateTextNode() {
            this.htmlNode.setFont( getTabFont() );
            this.htmlNode.setHTMLColor( getTextColor() );
        }

        public void updateFont( Font font ) {
            htmlNode.setFont( font );
            updateShape();
        }
    }

    /**
     * The AbstractTabNode is the graphic PNode for one tab.
     */
    private static abstract class AbstractTabNode extends PNode {

        private final IUserComponent userComponent;
        private String text;/*The text for the tab*/
        private JComponent component;/*The swing component associated with this tab.*/
        private PNode textNode;/*The PNode that draws the text*/
        private AimxcelPPath background; /*Draws the gradient background, use AimxcelPPath for Gradient workaround on Mac OS 10.4 */
        private boolean selected;/*True if this tab is selected.*/
        private float tiltWidth = 11; /*Amount the tab sticks out over adjacent tabs*/
        private Color selectedTabColor; /*Color when selected*/
        private Color unselectedTabColor;
        private Color selectedTextColor, unselectedTextColor;
        private PPath outlineNode; /*Draws the tab outline.*/
        private Font tabFont;
        private boolean textIsCentered = true;

        private static final Insets tabInsets = new Insets( 2, 15, 0, 15 );/* Insets for the text in the tab.*/

        public AbstractTabNode( IUserComponent userComponent, String text, JComponent component, Color selectedTabColor, Color unselectedTabColor, Color selectedTextColor, Color unselectedTextColor, Font tabFont ) {
            this.userComponent = userComponent;

            this.text = text;
            this.component = component;
            this.selectedTabColor = selectedTabColor;
            this.unselectedTabColor = unselectedTabColor;
            this.selectedTextColor = selectedTextColor;
            this.unselectedTextColor = unselectedTextColor;
            this.tabFont = tabFont;

            textNode = createTextNode( text );
            outlineNode = new PPath( createTabTopBorder( textNode.getFullBounds().getWidth(), textNode.getFullBounds().getHeight() ) );
            background = new AimxcelPPath( createTabShape( textNode.getFullBounds().getWidth(), textNode.getFullBounds().getHeight() ) );
            background.setStroke( null );
            addChild( background );
            addChild( textNode );
            addChild( outlineNode );
        }

        protected Color getSelectedTextColor() {
            return selectedTextColor;
        }

        public void updateShape() {
            outlineNode.setPathTo( createTabTopBorder( textNode.getFullBounds().getWidth(), textNode.getFullBounds().getHeight() ) );
            background.setPathTo( createTabShape( textNode.getFullBounds().getWidth(), textNode.getFullBounds().getHeight() ) );
        }

        protected abstract PNode createTextNode( String text );

        protected abstract void updateTextNode();

        public void setTabTextHeight( double tabHeight ) {
            background.setPathTo( createTabShape( textNode.getFullBounds().getWidth(), tabHeight ) );
            outlineNode.setPathTo( createTabTopBorder( textNode.getFullBounds().getWidth(), tabHeight ) );
            if ( textIsCentered ) {
                textNode.setOffset( 0, tabHeight / 2 - textNode.getHeight() / 2 );
            }
        }

        /**
         * Creates an outline over just the sides and top of the tab.
         *
         * @param textWidth
         * @param textHeight
         * @return the path.
         */
        private GeneralPath createTabTopBorder( double textWidth, double textHeight ) {
            GeneralPath outline = new GeneralPath();
            outline.moveTo( -tabInsets.left, (float) ( textHeight + tabInsets.bottom ) );
            outline.lineTo( -tabInsets.left, -tabInsets.top );
            outline.lineTo( (float) ( textWidth + tabInsets.right ), -tabInsets.top );
            outline.lineTo( (float) textWidth + tabInsets.right + tiltWidth, (float) ( textHeight + tabInsets.bottom ) );
            return outline;
        }

        /**
         * Creates a path that goes entirely around the tab graphic.
         *
         * @param textWidth
         * @param textHeight
         * @return the path.
         */
        private GeneralPath createTabShape( double textWidth, double textHeight ) {
            GeneralPath path = new GeneralPath();
            path.moveTo( -tabInsets.left, -tabInsets.top );
            path.lineTo( (float) ( textWidth + tabInsets.right ), -tabInsets.top );
            path.lineTo( (float) textWidth + tabInsets.right + tiltWidth, (float) ( textHeight + tabInsets.bottom ) );
            path.lineTo( -tabInsets.left, (float) ( textHeight + tabInsets.bottom ) );
            path.closePath();
            return path;
        }

        /**
         * Returns the swing component associated with this tab.
         *
         * @return the swing component associated with this tab.
         */
        public JComponent getComponent() {
            return component;
        }

        /**
         * Sets whether this tab and its associated component are selected.
         *
         * @param selected
         */
        public void setSelected( boolean selected ) {
            this.selected = selected;
            background.setStroke( getBorderStroke() );
            outlineNode.setVisible( selected );
            outlineNode.setStroke( selected ? new BasicStroke( 1.2f ) : new BasicStroke( 1 ) );
            updatePaint();
        }

        /**
         * Returns whether this tab is selected.
         *
         * @return true if this tab is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        private void updatePaint() {
            background.setPaint( getBackgroundPaint() );
            background.setStrokePaint( getBorderStrokePaint() );
            updateTextNode();
        }

        private Paint getBorderStrokePaint() {
            return Color.gray;
        }

        private Stroke getBorderStroke() {
            return ( !selected ) ? new BasicStroke( 1.0f ) : null;
        }

        private Paint getBackgroundPaint() {
            if ( selected ) {
                return new GradientPaint( 0, (float) background.getFullBounds().getY() - 2, selectedTabColor.brighter(), 0, (float) ( background.getFullBounds().getY() + 6 ), selectedTabColor );
            }
            else {
                return new GradientPaint( 0, (float) background.getFullBounds().getY() - 2, unselectedTabColor.brighter(), 0, (float) ( background.getFullBounds().getY() + 6 ), unselectedTabColor );
            }
        }

        protected Color getTextColor() {
            return selected ? selectedTextColor : unselectedTextColor;
        }

        public Font getTabFont() {
//            return selected?tabFont : new Font( tabFont.getName(),Font.PLAIN, tabFont.getSize() );
            return tabFont;
        }

        public String getText() {
            return text;
        }

        public void setSelectedTabColor( Color color ) {
            this.selectedTabColor = color;
            updatePaint();
        }

        public void setUnselectedTabColor( Color color ) {
            this.unselectedTabColor = color;
            updatePaint();
        }

        public void setSelectedTextColor( Color color ) {
            this.selectedTextColor = color;
            updatePaint();
        }

        public void setUnselectedTextColor( Color color ) {
            this.unselectedTextColor = color;
            updatePaint();
        }

        public double getTextHeight() {
            return textNode.getFullBounds().getHeight();
        }

        public void setFont( Font font ) {
            this.tabFont = font;
            updateFont( tabFont );
        }

        protected abstract void updateFont( Font tabFont );

    }

    /**
     * The TabBase is a PNode graphic for the horizontal bar under the tabs.
     */
    private static class TabBase extends PNode {
        private final AimxcelPPath path; // use AimxcelPPath for Gradient workaround on Mac OS 10.4
        private int tabBaseHeight = 9;
        private Color color;

        public TabBase( Color color ) {
            this.color = color;
            path = new AimxcelPPath( new Rectangle( 0, 0, 200, tabBaseHeight ) );
            path.setPaint( color );
            path.setPaint( new GradientPaint( 0, 0, color, 0, tabBaseHeight + 4, color.darker() ) );
            path.setStroke( null );
            addChild( path );
            updatePaint();
        }

        public void setTabBaseWidth( int width ) {
            path.setPathTo( new Rectangle( 0, 0, width, tabBaseHeight ) );
        }

        public void updatePaint() {
            path.setPaint( new GradientPaint( 0, 0, color, 0, tabBaseHeight, darker( color, 75 ) ) );
        }

        public void setTabColor( Color color ) {
            this.color = color;
            updatePaint();
        }
    }

    private static int darker( int value, int d ) {
        return Math.max( 0, value - d );
    }

    /**
     * Returns a new Color that is darker by the specified amount in each R G and B.
     *
     * @param a
     * @param d
     * @return the new Color.
     */
    private static Color darker( Color a, int d ) {
        return new Color( darker( a.getRed(), d ), darker( a.getGreen(), d ), darker( a.getBlue(), d ) );
    }

    /**
     * The TabPane is the Core PCanvas container for AbstractTabNode PNodes.
     */
    private static class TabPane extends PSwingCanvas {
        private ArrayList tabs = new ArrayList();
        private double distBetweenTabs = -6;
        private TabBase tabBase;
        private int tabTopInset = 3;
        private PImage logo;
        private AbstractTabNode selectedTab;
        private static final int LEFT_TAB_INSET = 10;
        private boolean logoObscured = false;
        private boolean logoVisible = true;

        public TabPane( Color selectedTabColor, Color unselectedTabColor ) {
            Image image = AimxcelCommonResources.getInstance().getImage( IMAGE_AIMXCEL_LOGO );
            logo = new PImage( image );
            logo.addInputEventListener( new CursorHandler() );
            logo.addInputEventListener( new ToolTipHandler( AimxcelCommonResources.getInstance().getLocalizedString( "Common.About.WebLink" ), this ) );
            logo.addInputEventListener( new PBasicInputEventHandler() {
                public void mousePressed( PInputEvent event ) {
                    AimxcelServiceManager.showAimxcelPage();
                }
            } );
            tabBase = new TabBase( selectedTabColor );
            setPanEventHandler( null );
            setZoomEventHandler( null );
            setOpaque( false );

            getLayer().addChild( logo );
            getLayer().addChild( tabBase );
            relayout();
        }

        @Override
        public void setBounds( int x, int y, int w, int h ) {
            super.setBounds( x, y, w, h );
            relayout();
        }

        public AbstractTabNode getSelectedTab() {
            return selectedTab;
        }

        /**
         * Adds the specified tab- if this is the first tab, it becomes selected.
         *
         * @param tab
         */
        public void addTab( AbstractTabNode tab ) {
            tabs.add( tab );
            getLayer().addChild( 0, tab );
            relayout();
            setSelectedTab( getSelectedTab() == null ? tab : getSelectedTab() );//updates
        }

        private void relayout() {
            tabBase.setTabBaseWidth( getWidth() );
            int x = AbstractTabNode.tabInsets.left + LEFT_TAB_INSET;
            double maxTabTextHeight = getMaxTabTextHeight();
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                tabNode.setOffset( x, tabTopInset );
                tabNode.setTabTextHeight( maxTabTextHeight );
                x += tabNode.getFullBounds().getWidth() + distBetweenTabs;
            }
            double tabBaseY = getHeight() - tabBase.getFullBounds().getHeight();
            tabBase.setOffset( 0, tabBaseY );
            relayoutLogo( tabBaseY );
            for ( int i = 0; i < tabs.size(); i++ ) {
                getTab( i ).updatePaint();
            }
            tabBase.updatePaint();
        }

        private void relayoutLogo( double tabBaseY ) {
            if ( logo.getImage().getHeight( null ) > getHeight() ) {
                double scale = ( getHeight() - 5.0 ) / ( (double) logo.getImage().getHeight( null ) );
                logo.setScale( Math.max( scale, 0.5 ) );
            }
            else {
                logo.setScale( 1 );
            }
            logo.setOffset( getWidth() - logo.getFullBounds().getWidth(), tabBaseY / 2 - logo.getFullBounds().getHeight() / 2 );

            if ( tabs.size() > 0 ) {
                AbstractTabNode lastTab = (AbstractTabNode) tabs.get( tabs.size() - 1 );
                if ( logo.getXOffset() < lastTab.getFullBounds().getMaxX() ) {
                    logoObscured = true;
                    updateLogoVisible();
                }
                else {
                    logoObscured = false;
                    updateLogoVisible();
                }
            }
        }

        public Dimension getPreferredSize() {
            relayout();
            int h = getMaxTabHeight();
            int width = (int) getLayer().getFullBounds().getWidth();
            width = Math.max( width, super.getPreferredSize().width );
            return new Dimension( width, (int) ( h + tabBase.getFullBounds().getHeight() ) );
        }

        private double getMaxTabTextHeight() {
            double h = 0;
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                h = Math.max( h, tabNode.getTextHeight() ) + getTabInsetFudgeFactor();
            }
            return h;
        }

        private double getTabInsetFudgeFactor() {
            if ( System.getProperty( "os.name" ).toLowerCase().indexOf( "mac os x" ) >= 0 ) {
                return 2;
            }
            else {
                return 0;
            }
        }

        private int getMaxTabHeight() {
            int h = 0;
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                h = (int) Math.max( h, tabNode.getFullBounds().getHeight() );
            }
            return h;
        }

        /**
         * Returns the tabs in this TabPane.
         *
         * @return the tabs in this TabPane.
         */
        public AbstractTabNode[] getTabs() {
            return (AbstractTabNode[]) tabs.toArray( new AbstractTabNode[0] );
        }

        /**
         * Sets the selected tab.
         *
         * @param tab
         */
        public void setSelectedTab( AbstractTabNode tab ) {
            this.selectedTab = tab;
            /*Ensure the tabs appear in the z-order in which they are listed.*/
            for ( int i = 0; i < getTabs().length; i++ ) {
                if ( getLayer().getChildrenReference().contains( getTabs()[i] ) ) {
                    getLayer().removeChild( getTabs()[i] );
                }
            }
            for ( int i = getTabs().length - 1; i >= 0; i-- ) {
                if ( getTabs()[i] != tab ) {
                    getLayer().addChild( getTabs()[i] );
                }
            }
            /*Then show the TabBase*/
            getLayer().removeChild( tabBase );
            getLayer().addChild( tabBase );
            /**Last, show the selected tab on top.*/
            getLayer().addChild( tab );
        }

        public int getSelectedIndex() {
            return tabs.indexOf( selectedTab );
        }

        public int getTabCount() {
            return tabs.size();
        }

        public String getTitleAt( int i ) {
            return getTab( i ).getText();
        }

        protected AbstractTabNode getTab( int i ) {
            return (AbstractTabNode) tabs.get( i );
        }

        public void removeTabAt( int i ) {
            getLayer().removeChild( (PNode) tabs.get( i ) );
            tabs.remove( i );
            relayout();
        }

        /**
         * Sets what the color should be when this tab is selected.
         *
         * @param color
         */
        public void setSelectedTabColor( Color color ) {
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                tabNode.setSelectedTabColor( color );
            }
            tabBase.setTabColor( color );
        }

        public void setUnselectedTabColor( Color color ) {
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                tabNode.setUnselectedTabColor( color );
            }
        }

        public void setSelectedTextColor( Color color ) {
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                tabNode.setSelectedTextColor( color );
            }
        }

        public void setUnselectedTextColor( Color color ) {
            for ( int i = 0; i < tabs.size(); i++ ) {
                AbstractTabNode tabNode = (AbstractTabNode) tabs.get( i );
                tabNode.setUnselectedTextColor( color );
            }
        }

        public void setLogoVisible( boolean logoVisible ) {
            this.logoVisible = logoVisible;
            updateLogoVisible();
        }

        public boolean getLogoVisible() {
            return logoVisible;
        }

        private void updateLogoVisible() {
            logo.setVisible( logoVisible && !logoObscured );
        }

    }

    /**
     * Handles input events to the tab, including selection events and cursor changes.
     */
    protected class TabInputListener extends PBasicInputEventHandler {
        private AbstractTabNode tab;

        public TabInputListener( AbstractTabNode tab ) {
            this.tab = tab;
        }

        public void mouseReleased( PInputEvent e ) {
            if ( tab.getFullBounds().contains( e.getCanvasPosition() ) ) {
                SimSharingManager.sendUserMessage( tab.userComponent, UserComponentTypes.tab, pressed );
                setSelectedTab( tab );
            }
        }

        public void mouseEntered( PInputEvent event ) {
            AimxcelTabbedPane.this.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        }

        public void mouseExited( PInputEvent event ) {
            AimxcelTabbedPane.this.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
        }
    }

    /**
     * A module with a separate similarly-styled Aimxcel tab bar across the top when more than 1 tab is
     * available, and with a single content component that takes up all remaining space.
     * <p/>
     * The content component can be changed whenever the passed-in tabs are made active or inactive
     * to support customized behavior.
     * <p/>
     * This is currently necessary for our 3D based sims (both LWJGL and JME3)
     */
    public static class TabbedModule extends Module {

        // what tab is currently active
        public final Property<Tab> selectedTab = new Property<Tab>( null );

        // main content
        private Component content;

        // tab pane (visible with more than one tab) at the top
        private TabPane tabPane;

        private List<Tab> tabs = new ArrayList<Tab>();

        // our "simulation" panel for the module
        private JPanel simulationPanel;

        // associate tabs with their respective AimxcelTabbedPane.HTMLTabNode instance and vice versa
        private Map<Tab, HTMLTabNode> tabNodeMap = new HashMap<Tab, HTMLTabNode>();
        private Map<HTMLTabNode, Tab> tabNodeReverseMap = new HashMap<HTMLTabNode, Tab>();

        /**
         * @param content Component that holds all of the main content
         */
        public TabbedModule( final Component content ) {
            super( "", new ConstantDtClock() ); // TODO: get rid of the clock
            this.content = content;

            tabPane = new TabPane( AimxcelTabbedPane.DEFAULT_SELECTED_TAB_COLOR, AimxcelTabbedPane.DEFAULT_UNSELECTED_TAB_COLOR );

            // set up our module so that only the content is showing. tab bar will be added later if necessary
            simulationPanel = new JPanel( new BorderLayout() ) {{
                add( content, BorderLayout.CENTER );
            }};
            setSimulationPanel( simulationPanel );
            setClockControlPanel( null );
            setControlPanel( null );
            setLogoPanelVisible( false );

            // when our tab changes, update our visual state and notify the changed tabs of their state
            selectedTab.addObserver( new ChangeObserver<Tab>() {
                public void update( Tab newValue, Tab oldValue ) {
                    // our state handling
                    if ( oldValue != null ) {
                        oldValue.setActive( false );
                    }
                    newValue.setActive( true );

                    // all of this is needed to interface with the TabPane to assure correct tab highlights
                    for ( Tab tab : tabs ) {
                        if ( tab != newValue ) {
                            tabNodeMap.get( tab ).setSelected( false );
                        }
                    }
                    if ( tabPane.getSelectedTab() != newValue ) {
                        HTMLTabNode tabNode = tabNodeMap.get( newValue );
                        tabNode.setSelected( true );
                        tabPane.setSelectedTab( tabNode );
                    }
                }
            } );
        }

        public Component getContent() {
            return content;
        }

        public void addTab( final Tab tab ) {
            HTMLTabNode tabNode = new HTMLTabNode( tab.getUserComponent(), tab.getTitle(), null,
                                                   AimxcelTabbedPane.DEFAULT_SELECTED_TAB_COLOR, AimxcelTabbedPane.DEFAULT_UNSELECTED_TAB_COLOR,
                                                   AimxcelTabbedPane.DEFAULT_SELECTED_TEXT_COLOR, AimxcelTabbedPane.DEFAULT_UNSELECTED_TEXT_COLOR,
                                                   AimxcelTabbedPane.DEFAULT_TAB_FONT ) {{
                final HTMLTabNode htmlTabNode = this; // self-reference

                addInputEventListener( new PBasicInputEventHandler() {
                    @Override public void mouseReleased( PInputEvent event ) {
                        if ( getFullBounds().contains( event.getCanvasPosition() ) ) {

                            SimSharingManager.sendUserMessage( tab.getUserComponent(), UserComponentTypes.tab, pressed, ParameterSet.parameterSet( text, getText() ) );

                            selectedTab.set( tabNodeReverseMap.get( htmlTabNode ) );
                        }
                    }

                    @Override public void mouseEntered( PInputEvent event ) {
                        tabPane.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
                    }

                    @Override public void mouseExited( PInputEvent event ) {
                        tabPane.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );
                    }
                } );
            }};

            tabNodeMap.put( tab, tabNode );
            tabNodeReverseMap.put( tabNode, tab );

            tabPane.addTab( tabNode );
            tabs.add( tab );
            if ( tabs.size() == 1 ) {
                selectedTab.set( tab );
                tab.setActive( true );
            }
            else {
                tabNode.setSelected( false );
                tab.setActive( false );
            }

            // if necessary, add tab pane.
            if ( tabs.size() == 2 ) {
                simulationPanel.add( tabPane, BorderLayout.NORTH );
            }
        }

        public void removeTab( Tab tab ) {
            boolean wasSelected = selectedTab.get() == tab;
            tabPane.removeTabAt( tabs.indexOf( tab ) );
            tabs.remove( tab );
            if ( wasSelected ) {
                tab.setActive( false );

                // removed the active tab, so just select the 1st tab to make active
                selectedTab.set( tabs.get( 0 ) );
                tabs.get( 0 ).setActive( true );
            }
        }

        public List<Tab> getTabs() {
            return new ArrayList<Tab>( tabs ); // defensive copy
        }

        public static interface Tab {
            public String getTitle();

            public void setActive( boolean active );

            IUserComponent getUserComponent();
        }
    }
}