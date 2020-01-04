

package com.aimxcel.abclearn.common.aimxcelcommon.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelCommonResources;
import com.aimxcel.abclearn.common.aimxcelcommon.util.AimxcelLocales;
import com.aimxcel.abclearn.common.aimxcelcommon.util.LocaleUtils;



public class AimxcelLocales implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PROPERTIES_RESOURCE_NAME = "localization/aimxcelcommon-locales.properties";
    private static final String LOCALES_SEPARATOR = ",";

    private static AimxcelLocales _singleton;

    private HashMap<Locale, String> _localeToNameMap;
    private HashMap<String, Locale> _nameToLocaleMap;

    /* singleton */
    public static AimxcelLocales getInstance() {
        if ( _singleton == null ) {
            _singleton = new AimxcelLocales();
        }
        return _singleton;
    }

    /* singleton */
    private AimxcelLocales() {
        _nameToLocaleMap = new HashMap<String, Locale>();
        _localeToNameMap = new HashMap<Locale, String>();
        loadCodes( AimxcelCommonResources.getInstance().getProperties( PROPERTIES_RESOURCE_NAME ) );
    }

    private void loadCodes( Properties p ) {
        // get the set of locale codes
        String[] isoCodes = p.getProperty( "locales" ).split( LOCALES_SEPARATOR );
        // build mappings between locales and display names
        for ( int i = 0; i < isoCodes.length; i++ ) {
            Locale locale = LocaleUtils.stringToLocale( isoCodes[i] );
            if ( getName( locale ) != null ) {
                System.err.println( getClass().getName() + ": ignoring duplicate locale=" + locale );
            }
            else {
                String name = p.getProperty( "name." + locale ); // eg, name.zh_CN
                if ( name == null ) {
                    System.err.println( getClass().getName() + ": missing display name for locale=" + locale );
                }
                else {
                    addEntry( locale, name );
                }
            }
        }
    }

    private void addEntry( Locale locale, String name ) {
        _localeToNameMap.put( locale, name );
        _nameToLocaleMap.put( name, locale );
    }

    public String getName( Locale locale ) {
        String name = null;
        Object o = _localeToNameMap.get( locale );
        if ( o != null ) {
            name = (String) o;
        }
        return name;
    }

    public Locale getLocale( String name ) {
        Locale locale = null;
        Object o = _nameToLocaleMap.get( name );
        if ( o != null ) {
            locale = (Locale) o;
        }
        return locale;
    }

    public String[] getSortedNames() {
        List<String> list = new ArrayList<String>( _nameToLocaleMap.keySet() );
        Collections.sort( list );
        return (String[]) list.toArray( new String[list.size()] );
    }

    public List<Locale> getLocaleList() {
        return new ArrayList<Locale>( _localeToNameMap.keySet() );
    }
}