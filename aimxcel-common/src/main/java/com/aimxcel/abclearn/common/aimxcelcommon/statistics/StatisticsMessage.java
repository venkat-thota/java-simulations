
package com.aimxcel.abclearn.common.aimxcelcommon.statistics;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class StatisticsMessage {

    private final ArrayList<StatisticsMessageField> fields = new ArrayList<StatisticsMessageField>();

    public StatisticsMessage( String messageType, String messageVersion ) {
        addField( new StatisticsMessageField( "Common.statistics.message_type", "message_type", messageType ) );
        addField( new StatisticsMessageField( "Common.statistics.message_version", "message_version", messageVersion ) );
        addField( new StatisticsMessageField( "Common.statistics.sim_type", "sim_type", "java" ) );
    }

    public void addFields( StatisticsMessageField[] list ) {
        fields.addAll( Arrays.asList( list ) );
    }

    public void addField( StatisticsMessageField field ) {
        fields.add( field );
    }

    public StatisticsMessageField getField( int i ) {
        return (StatisticsMessageField) fields.get( i );
    }

    public int getFieldCount() {
        return fields.size();
    }

    public String toHumanReadable() {
        String text = "";
        for ( int i = 0; i < getFieldCount(); i++ ) {
            if ( i > 0 ) {
                text += "\n";
            }
            text += getField( i ).getHumanReadableName() + " " + getField( i ).getValue();
        }
        return text;
    }
}
