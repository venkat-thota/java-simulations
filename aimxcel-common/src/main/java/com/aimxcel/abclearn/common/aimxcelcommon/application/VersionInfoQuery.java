
package com.aimxcel.abclearn.common.aimxcelcommon.application;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.aimxcel.abclearn.common.aimxcelcommon.AimxcelCommonConstants;
import com.aimxcel.abclearn.common.aimxcelcommon.application.ISimInfo;
import com.aimxcel.abclearn.common.aimxcelcommon.math.MathUtil;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelInstallerVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.resources.AimxcelVersion;
import com.aimxcel.abclearn.common.aimxcelcommon.statistics.StatisticsMessageSender;
import com.aimxcel.abclearn.common.aimxcelcommon.util.logging.LoggingUtils;
import com.aimxcel.abclearn.common.aimxcelcommon.view.util.XMLUtils;

public class VersionInfoQuery {

    private static final String REQUEST_VERSION_TAG = "request_version";
    private static final String ERROR_TAG = "error";
    private static final String WARNING_TAG = "warning";

    private final String project;
    private final String sim;
    private final AimxcelVersion currentSimVersion;
    private final AimxcelInstallerVersion currentInstallerVersion;
    private final boolean automaticRequest;
    private final ArrayList listeners;

    private final boolean hasSimQuery, hasInstallerQuery;

    private static final Logger LOGGER = LoggingUtils.getLogger( VersionInfoQuery.class.getCanonicalName() );

    /**
     * Use this constructor to get both sim and installer version info.
     */
    public VersionInfoQuery( String project, String sim, AimxcelVersion currentSimVersion, AimxcelInstallerVersion currentInstallerVersion, boolean automaticRequest ) {

        this.project = project;
        this.sim = sim;

        this.currentSimVersion = currentSimVersion;
        this.currentInstallerVersion = currentInstallerVersion;
        this.automaticRequest = automaticRequest;

        listeners = new ArrayList();

        hasSimQuery = ( project != null && sim != null );
        hasInstallerQuery = ( currentInstallerVersion != null );
    }

    /**
     * Convenience constructor, for getting both sim and installer version info.
     */
    public VersionInfoQuery( ISimInfo simInfo, AimxcelInstallerVersion currentInstallerVersion, boolean automaticRequest ) {
        this( simInfo.getProjectName(), simInfo.getFlavor(), simInfo.getVersion(), currentInstallerVersion, automaticRequest );
    }

    /**
     * Use this constructor to get sim version information.
     */
    public VersionInfoQuery( String project, String sim, AimxcelVersion currentSimVersion, boolean automaticRequest ) {
        this( project, sim, currentSimVersion, null, automaticRequest );
    }

    /**
     * Convenience constructor, for getting sim version information.
     */
    public VersionInfoQuery( ISimInfo simInfo, boolean automaticRequest ) {
        this( simInfo, null, automaticRequest );
    }

    /**
     * Use this constructor to get installer version info.
     */
    public VersionInfoQuery( AimxcelInstallerVersion currentInstallerVersion, boolean automaticRequest ) {
        this( null, null, null, currentInstallerVersion, automaticRequest );
    }

    public String getProject() {
        return project;
    }

    public String getSim() {
        return sim;
    }

    public AimxcelVersion getCurrentSimVersion() {
        return currentSimVersion;
    }

    public AimxcelInstallerVersion getCurrentInstallerVersion() {
        return currentInstallerVersion;
    }

    /**
     * Sends the query to the server.
     * Notifies listeners when the response is received.
     */
    public void send() {
        final String url = AimxcelCommonConstants.AIMXCEL_INFO_URL;
        try {
            // query
            Document queryDocument = buildQueryDocument();
            LOGGER.fine( "posting to url=" + url );
            LOGGER.fine( "query=" + XMLUtils.toString( queryDocument ) );
            HttpURLConnection connection = XMLUtils.post( url, queryDocument );

            // response
            Document responseDocument = XMLUtils.readDocument( connection );
            LOGGER.fine( "response=" + XMLUtils.toString( responseDocument ) );
            Response response = parseResponse( responseDocument, this );

            // notification
            if ( response != null ) {
                notifyDone( response );
            }
        }
        catch ( ParserConfigurationException e ) {
            notifyException( e );
        }
        catch ( TransformerException e ) {
            notifyException( e );
        }
        catch ( UnknownHostException e ) {
            notifyException( e );
        }
        catch ( IOException e ) {
            notifyException( e );
        }
        catch ( SAXException e ) {
            notifyException( e );
        }
    }

    /*
     * Creates an XML document that represents the query.
     */
    private Document buildQueryDocument() throws ParserConfigurationException {

        String requestedBy = ( automaticRequest ? "automatic" : "manual" );

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.newDocument();

        Element rootElement = document.createElement( "aimxcel_info" );
        document.appendChild( rootElement );

        if ( hasSimQuery ) {
            Element simVersionElement = document.createElement( "sim_version" );
            simVersionElement.setAttribute( REQUEST_VERSION_TAG, String.valueOf( AimxcelCommonConstants.SIM_VERSION_VERSION ) );
            simVersionElement.setAttribute( "project", project );
            simVersionElement.setAttribute( "sim", sim );
            simVersionElement.setAttribute( "requested_by", requestedBy );
            rootElement.appendChild( simVersionElement );
        }

        if ( hasInstallerQuery ) {
            Element installerUpdateElement = document.createElement( "aimxcel_installer_update" );
            installerUpdateElement.setAttribute( REQUEST_VERSION_TAG, String.valueOf( AimxcelCommonConstants.AIMXCEL_INSTALLER_UPDATE_VERSION ) );
            installerUpdateElement.setAttribute( "timestamp_seconds", String.valueOf( currentInstallerVersion.getTimestamp() ) );
            installerUpdateElement.setAttribute( "requested_by", requestedBy );
            rootElement.appendChild( installerUpdateElement );
        }

        return document;
    }

    /*
    * Parses an XML document that represents the query response.
    * If no errors are encountered, returns an object that contains the query results.
    * When the first error is encountered, parsing stops, listeners are notified of an exception, and null is returned.
    */
    private Response parseResponse( Document document, VersionInfoQuery query ) throws TransformerException {

        // bail on the first error
        NodeList errors = document.getElementsByTagName( ERROR_TAG );
        if ( errors.getLength() > 0 ) {
            Element element = (Element) errors.item( 0 );
            NodeList children = element.getChildNodes();
            Text text = (Text) children.item( 0 );
            System.err.println( "ERROR: " + StatisticsMessageSender.class.getName() + ": " + text.getData() );
            notifyException( new VersionInfoQueryException( text.getData() ) );
            return null;
        }
        // if there were no <error> elements, then we assume that we have results

        // print warnings to the console
        NodeList warnings = document.getElementsByTagName( WARNING_TAG );
        for ( int i = 0; i < warnings.getLength(); i++ ) {
            Element element = (Element) warnings.item( i );
            NodeList children = element.getChildNodes();
            for ( int j = 0; j < children.getLength(); j++ ) {
                if ( children.item( j ) instanceof Text ) {
                    Text text = (Text) children.item( j );
                    System.out.println( "WARNING: " + StatisticsMessageSender.class.getName() + ": " + text.getData() );
                }
            }
        }

        // parse content
        SimResponse simResponse = parseSimResponse( document );
        InstallerResponse installerResponse = parseInstallerResponse( document );
        return new Response( query, simResponse, installerResponse );
    }

    /*
    * Parses the portion of the response that is related to sim version info.
    * Returns null if there is no such response.
    */
    private SimResponse parseSimResponse( Document document ) {

        SimResponse simResponse = null;

        if ( hasSimQuery ) {

            AimxcelVersion version = null;
            long askMeLaterDuration = 0;

            String elementName = "sim_version_response";

            String versionMajor = getAttribute( document, elementName, "version_major" );
            String versionMinor = getAttribute( document, elementName, "version_minor" );
            String versionDev = getAttribute( document, elementName, "version_dev" );
            String versionRevision = getAttribute( document, elementName, "version_revision" );
            String versionTimestamp = getAttribute( document, elementName, "version_timestamp" );
            if ( versionMajor == null || versionMinor == null || versionDev == null || versionRevision == null || versionTimestamp == null ) {
                notifyException( new VersionInfoQueryException( "missing one or more attributes related to sim version" ) );
                return null;
            }
            version = new AimxcelVersion( versionMajor, versionMinor, versionDev, versionRevision, versionTimestamp );

            String attributeName = "ask_me_later_duration_days";
            String attributeValue = getAttribute( document, elementName, attributeName );
            if ( attributeValue == null ) {
                notifyException( new VersionInfoQueryException( elementName + " is missing attribute " + attributeName ) );
                return null;
            }
            try {
                askMeLaterDuration = MathUtil.daysToMilliseconds( Long.parseLong( attributeValue ) ); // days to ms !
            }
            catch ( NumberFormatException e ) {
                notifyException( new VersionInfoQueryException( "expected a number, received " + attributeValue ) );
                return null;
            }

            simResponse = new SimResponse( this.getCurrentSimVersion(), version, askMeLaterDuration );
        }
        return simResponse;
    }

    /*
    * Parses the portion of the response that is related to installer version info.
    * Returns null if there is no such response.
    */
    private InstallerResponse parseInstallerResponse( Document document ) {

        InstallerResponse installerResponse = null;

        if ( hasInstallerQuery ) {

            boolean isUpdateRecommended = false;
            AimxcelInstallerVersion version = null;
            long askMeLaterDuration = 0;

            String elementName = "aimxcel_installer_update_response";

            String attributeName = "recommend_update";
            String attributeValue = getAttribute( document, elementName, attributeName );
            if ( attributeValue == null ) {
                notifyException( new VersionInfoQueryException( elementName + " is missing attribute " + attributeName ) );
            }
            isUpdateRecommended = Boolean.valueOf( attributeValue ).booleanValue();

            attributeName = "timestamp_seconds";
            attributeValue = getAttribute( document, elementName, attributeName );
            if ( attributeValue == null ) {
                notifyException( new VersionInfoQueryException( elementName + " is missing attribute " + attributeName ) );
                return null;
            }
            try {
                version = new AimxcelInstallerVersion( Long.parseLong( attributeValue ) );
            }
            catch ( NumberFormatException e ) {
                notifyException( new VersionInfoQueryException( "expected a number, received " + attributeValue ) );
                return null;
            }

            attributeName = "ask_me_later_duration_days";
            attributeValue = getAttribute( document, elementName, attributeName );
            if ( attributeValue == null ) {
                notifyException( new VersionInfoQueryException( elementName + " is missing attribute " + attributeName ) );
                return null;
            }
            try {
                askMeLaterDuration = MathUtil.daysToMilliseconds( Long.parseLong( attributeValue ) ); // days to ms !
            }
            catch ( NumberFormatException e ) {
                notifyException( new VersionInfoQueryException( "expected a number, received " + attributeValue ) );
                return null;
            }

            installerResponse = new InstallerResponse( isUpdateRecommended, version, askMeLaterDuration );
        }
        return installerResponse;
    }

    /*
    * Gets the first occurrence of an attribute in an XML document.
    */
    private static String getAttribute( Document document, String elementName, String attributeName ) {
        String value = null;
        NodeList nodelist = document.getElementsByTagName( elementName );
        if ( nodelist.getLength() > 0 ) {
            Element element = (Element) nodelist.item( 0 );
            value = element.getAttribute( attributeName );
            if ( value != null && value.length() == 0 ) {
                value = null;
            }
        }
        return value;
    }

    /**
     * Encapsulates the response to this query.
     */
    public static class Response {

        private final VersionInfoQuery query;
        private final SimResponse simResponse;
        private final InstallerResponse installerResponse;

        public Response( VersionInfoQuery query, SimResponse simResponse, InstallerResponse installerResponse ) {
            this.query = query;
            this.simResponse = simResponse;
            this.installerResponse = installerResponse;
        }

        public VersionInfoQuery getQuery() {
            return query;
        }

        public SimResponse getSimResponse() {
            return simResponse;
        }

        public InstallerResponse getInstallerResponse() {
            return installerResponse;
        }
    }

    /**
     * Portion of the response related to the sim.
     */
    public static class SimResponse {

        private final AimxcelVersion currentVersion;
        private final AimxcelVersion version;
        private final long askMeLaterDuration; // ms

        public SimResponse( AimxcelVersion currentVersion, AimxcelVersion version, long askMeLaterDuration ) {
            this.currentVersion = currentVersion;
            this.version = version;
            this.askMeLaterDuration = askMeLaterDuration;
        }

        public boolean isUpdateRecommended() {
            return getVersion().isGreaterThan( currentVersion );
        }

        public AimxcelVersion getVersion() {
            return version;
        }

        public long getAskMeLaterDuration() {
            return askMeLaterDuration;
        }
    }

    /**
     * Portion of the response related to the installer.
     */
    public static class InstallerResponse {

        private final boolean isUpdateRecommended;
        private final AimxcelInstallerVersion version;
        private final long askMeLaterDuration; // ms

        public InstallerResponse( boolean isUpdateRecommended, AimxcelInstallerVersion version, long askMeLaterDuration ) {
            this.isUpdateRecommended = isUpdateRecommended;
            this.version = version;
            this.askMeLaterDuration = askMeLaterDuration;
        }

        public boolean isUpdateRecommended() {
            return isUpdateRecommended;
        }

        public AimxcelInstallerVersion getVersion() {
            return version;
        }

        public long getAskMeLaterDuration() {
            return askMeLaterDuration;
        }
    }

    public interface VersionInfoQueryListener {

        /**
         * The query is done and a response is available.
         *
         * @param response
         */
        public void done( Response response );

        /**
         * An exception occurred, and don't expect a response.
         *
         * @param e
         */
        public void exception( Exception e );
    }

    public void addListener( VersionInfoQueryListener listener ) {
        listeners.add( listener );
    }

    public void removeListener( VersionInfoQueryListener listener ) {
        listeners.remove( listener );
    }

    private void notifyDone( Response response ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (VersionInfoQueryListener) listeners.get( i ) ).done( response );
        }
    }

    private void notifyException( Exception e ) {
        for ( int i = 0; i < listeners.size(); i++ ) {
            ( (VersionInfoQueryListener) listeners.get( i ) ).exception( e );
        }
    }

    public static class VersionInfoQueryException extends Exception {
        public VersionInfoQueryException( String message ) {
            super( message );
        }
    }

    public static void main( String[] args ) {

        AimxcelVersion simVersion = new AimxcelVersion( "1", "02", "03", "45678", "1122334455" );
        AimxcelInstallerVersion installerVersion = new AimxcelInstallerVersion( 1234567890 );
        VersionInfoQuery query = new VersionInfoQuery( "balloons", "balloons", simVersion, installerVersion, true /* automaticRequest */ );
        query.addListener( new VersionInfoQueryListener() {

            public void done( Response response ) {
                System.out.println( getClass().getName() + ".done" );
                System.out.println( "sim.version=" + response.getSimResponse().getVersion() );
                System.out.println( "sim.askMeLaterDuration=" + response.getSimResponse().getAskMeLaterDuration() );
                System.out.println( "installer.isUpdateRecommended=" + response.getInstallerResponse().isUpdateRecommended() );
                System.out.println( "installer.version=" + response.getInstallerResponse().getVersion().getTimestamp() );
                System.out.println( "installer.askMeLaterDuration=" + response.getInstallerResponse().getAskMeLaterDuration() );
            }

            public void exception( Exception e ) {
                e.printStackTrace();
            }
        } );
        query.send();
    }
}
