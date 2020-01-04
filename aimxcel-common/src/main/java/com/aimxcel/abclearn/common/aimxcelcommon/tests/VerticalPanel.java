package com.aimxcel.abclearn.common.aimxcelcommon.tests;


class Component {
    void addChild( Component b ) {
    }
}

class Panel extends Component {
    public Panel( Component pNode ) {
    }

    Panel() {
    }
}

public class VerticalPanel extends Panel {
    public VerticalPanel() {
        super( new Component() {{
            addChild( new Panel() );
        }} );
    }

    public static void main( String[] args ) {
        System.out.println( "new SelectedItemNode() = " + new VerticalPanel() );
    }
}