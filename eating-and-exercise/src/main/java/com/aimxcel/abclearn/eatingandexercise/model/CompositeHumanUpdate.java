
package com.aimxcel.abclearn.eatingandexercise.model;

import java.util.ArrayList;

public class CompositeHumanUpdate implements HumanUpdate {
    private ArrayList list = new ArrayList();

    public void add( HumanUpdate humanUpdate ) {
        list.add( humanUpdate );
    }

    public void update( Human human, double dt ) {
        for ( int i = 0; i < list.size(); i++ ) {
            ( (HumanUpdate) list.get( i ) ).update( human, dt );
        }
    }
}
