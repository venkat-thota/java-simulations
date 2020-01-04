
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplication;
import com.aimxcel.abclearn.common.aimxcelcommon.application.AimxcelApplicationConfig;

public interface ApplicationConstructor {
    AimxcelApplication getApplication( AimxcelApplicationConfig config );
}
