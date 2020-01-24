
package com.aimxcel.abclearn.signalcircuit.paint;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class LayeredPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TreeMap<Integer, Vector<Painter>> painters = new TreeMap<Integer, Vector<Painter>>();

    public LayeredPanel() {
    }

    public void remove( Painter p, int layer ) {
        ( (Vector<?>)painters.get( new Integer( layer ) ) ).remove( p );
    }

    public void addPainter( Painter p ) {
        addPainter( p, 0 );
    }

    public void addPainter( Painter p, int level ) {

        Vector<Painter> v = (Vector<Painter>)painters.get( new Integer( level ) );
        if( v == null ) {
            v = new Vector<Painter>();
            painters.put( new Integer( level ), v );
        }
        v.add( p );
    }

    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D)g;
        Set<Integer> e = painters.keySet();
        Iterator<Integer> it = e.iterator();
        while( it.hasNext() ) {
            Object key = it.next();
            Vector<?> next = (Vector<?>)painters.get( key );
            for( int i = 0; i < next.size(); i++ ) {
                Painter p = (Painter)next.get( i );
                p.paint( g2 );
            }
        }
    }
}
