
package com.aimxcel.abclearn.glaciers.persistence;

import com.aimxcel.abclearn.common.aimxcelcommon.util.IProguardKeepClass;

public class GlaciersConfig implements IProguardKeepClass {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//----------------------------------------------------------------------------
    // Instance data
    //----------------------------------------------------------------------------
    
    // Global config
    private String _versionString;
    private String _versionMajor;
    private String _versionMinor;
    private String _versionDev;
    private String _versionRevision;
    
    // Modules
    private IntroConfig _basicConfig;
    private AdvancedConfig _advancedConfig;
    
    //----------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------
    
    /**
     * Zero-argument constructor for Java Bean compliance, required by XMLEncoder.
     */
    public GlaciersConfig() {
        _basicConfig = new IntroConfig();
        _advancedConfig = new AdvancedConfig();
    }

    //----------------------------------------------------------------------------
    // Accessors for global information
    //----------------------------------------------------------------------------
    
    public String getVersionString() {
        return _versionString;
    }
    
    public void setVersionString( String versionString ) {
        _versionString = versionString;
    }
    
    public String getVersionMajor() {
        return _versionMajor;
    }
    
    public void setVersionMajor( String versionMajor ) {
        _versionMajor = versionMajor;
    }

    public String getVersionMinor() {
        return _versionMinor;
    }
    
    public void setVersionMinor( String versionMinor ) {
        _versionMinor = versionMinor;
    }
    
    public String getVersionDev() {
        return _versionDev;
    }

    public void setVersionDev( String versionDev ) {
        _versionDev = versionDev;
    }
    
    public String getVersionRevision() {
        return _versionRevision;
    }
    
    public void setVersionRevision( String versionRevision ) {
        _versionRevision = versionRevision;
    }
    
    //----------------------------------------------------------------------------
    // Accessors for module configurations
    //----------------------------------------------------------------------------
    
    public void setBasicConfig( IntroConfig basicConfig ) {
        _basicConfig = basicConfig;
    }
    
    public IntroConfig getBasicConfig() {
        return _basicConfig;
    }
    
    public void setAdvancedConfig( AdvancedConfig advancedConfig ) {
        _advancedConfig = advancedConfig;
    }
    
    public AdvancedConfig getAdvancedConfig() {
        return _advancedConfig;
    }
}
