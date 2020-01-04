

/*
 * CVS Info -
 * Filename : $Source$
 * Branch : $Name$
 * Modified by : $Author$
 * Revision : $Revision$
 * Date modified : $Date$
 */
package com.aimxcel.abclearn.common.aimxcelcommon.model;

/**
 * A Command for invoking in the BaseModel update operation.
 *
 * @author ?
 * @version $Revision$
 */
public interface Command {

    /**
     * Invokes this Command.
     */
    void doIt();
}
