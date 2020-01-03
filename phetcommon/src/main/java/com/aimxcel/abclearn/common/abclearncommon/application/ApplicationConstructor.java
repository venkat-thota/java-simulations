
package com.aimxcel.abclearn.common.abclearncommon.application;

import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplication;
import com.aimxcel.abclearn.common.abclearncommon.application.AbcLearnApplicationConfig;

public interface ApplicationConstructor {
    AbcLearnApplication getApplication( AbcLearnApplicationConfig config );
}
