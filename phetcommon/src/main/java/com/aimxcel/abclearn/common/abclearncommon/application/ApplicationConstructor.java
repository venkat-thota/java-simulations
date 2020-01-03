
package com.aimxcel.abclearn.common.abclearncommon.application;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;

/**
 * We need one of these to start the simulation.
 */
public interface ApplicationConstructor {
    AbcLearnApplication getApplication( AbcLearnApplicationConfig config );
}
