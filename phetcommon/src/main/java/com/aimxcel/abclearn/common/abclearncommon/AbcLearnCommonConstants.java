package com.aimxcel.abclearn.common.abclearncommon;


public class AbcLearnCommonConstants {

    public static final String ABC_LEARN_NAME = "ABC_LEARN";  // user-visible name, does not require translation

    public static final String ABC_LEARN_HOME_SERVER = "ABC_LEARN.colorado.edu";
    public static final String ABC_LEARN_HOME_URL = "http://" + ABC_LEARN_HOME_SERVER;
    public static final String ABC_LEARN_EMAIL = "ABC_LEARNhelp@colorado.edu";

    // web pages
    public static final String ABC_LEARN_INSTALLER_URL = ABC_LEARN_HOME_URL + "/get_ABC_LEARN/full_install.php";
    public static final String ABC_LEARN_LICENSE_URL = ABC_LEARN_HOME_URL + "/about/licensing.php";

    // services
    public static final String SIM_WEBSITE_REDIRECT_URL = ABC_LEARN_HOME_URL + "/services/sim-website-redirect";
    public static final String SIM_JAR_REDIRECT_URL = ABC_LEARN_HOME_URL + "/services/sim-jar-redirect";
    public static final String ABC_LEARN_INFO_URL = ABC_LEARN_HOME_URL + "/services/ABC_LEARN-info";
    public static final String STATISTICS_SERVICE_URL = ABC_LEARN_HOME_URL + "/statistics/submit_message";

    // service request version numbers
    public static final int SIM_WEBSITE_REDIRECT_VERSION = 1; // associated with SIM_WEBSITE_REDIRECT_URL
    public static final int SIM_JAR_REDIRECT_VERSION = 1; // associated with SIM_JAR_REDIRECT_URL
    public static final int SIM_VERSION_VERSION = 1; // <sim_version> request to ABC_LEARN_INFO_URL
    public static final int ABC_LEARN_INSTALLER_UPDATE_VERSION = 1; // <ABC_LEARN_installer_update> request to ABC_LEARN_INFO_URL

    // Properties used to set the locale.
    // Since these need to be usable via JNLP, they begin with "javaws".
    // For an untrusted application, system properties set in the JNLP file will 
    // only be set by Java Web Start if property name begins with "jnlp." or "javaws.".

    //Do not change these values lightly; the translation-deploy process and many other processes will need to accommodate any changes
    public static final String PROPERTY_ABC_LEARN_LANGUAGE = "javaws.user.language";
    public static final String PROPERTY_ABC_LEARN_COUNTRY = "javaws.user.country";
}
