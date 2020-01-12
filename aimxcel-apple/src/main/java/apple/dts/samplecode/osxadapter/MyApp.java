
package apple.dts.samplecode.osxadapter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class MyApp extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JDialog aboutBox, prefs;

    protected JLabel imageLabel;
    protected JComboBox<?> colorComboBox;

    protected JMenu fileMenu, helpMenu;
    protected JMenuItem openMI, optionsMI, quitMI;
    protected JMenuItem docsMI, supportMI, aboutMI;

    // Check that we are on Mac OS X.  This is crucial to loading and using the OSXAdapter class.
    public static boolean MAC_OS_X = ( System.getProperty( "os.name" ).toLowerCase().startsWith( "mac os x" ) );

    protected BufferedImage currentImage;

    Color[] colors = { Color.WHITE, Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.ORANGE };
    String[] colorNames = { "White", "Black", "Red", "Blue", "Yellow", "Orange" };

    // Ask AWT which menu modifier we should be using.
    final static int MENU_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    public MyApp() {
        super( "OSXAdapter" );

        addMenus();

        // Main content area; set up a JLabel to display images selected by the user
        getContentPane().setLayout( new BorderLayout() );
        getContentPane().add( imageLabel = new JLabel( "Open an image to view it" ) );
        imageLabel.setHorizontalAlignment( SwingConstants.CENTER );
        imageLabel.setVerticalAlignment( SwingConstants.CENTER );
        imageLabel.setOpaque( true );

        // set up a simple about box
        aboutBox = new JDialog( this, "About OSXAdapter" );
        aboutBox.getContentPane().setLayout( new BorderLayout() );
        aboutBox.getContentPane().add( new JLabel( "OSXAdapter", JLabel.CENTER ) );
        aboutBox.getContentPane().add( new JLabel( "\u00A92003-2007 Apple, Inc.", JLabel.CENTER ), BorderLayout.SOUTH );
        aboutBox.setSize( 160, 120 );
        aboutBox.setResizable( false );

        // Preferences dialog lets you select the background color when displaying an image
        prefs = new JDialog( this, "OSXAdapter Preferences" );
        colorComboBox = new JComboBox<Object>( colorNames );
        colorComboBox.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent ev ) {
                if ( currentImage != null ) {
                    imageLabel.setBackground( (Color) colors[colorComboBox.getSelectedIndex()] );
                }
            }
        } );

        JPanel masterPanel = new JPanel();
        masterPanel.setBorder( new TitledBorder( "Window background color:" ) );
        masterPanel.add( colorComboBox );
        prefs.getContentPane().add( masterPanel );
        prefs.setSize( 240, 100 );
        prefs.setResizable( false );

        // Set up our application to respond to the Mac OS X application menu
        registerForMacOSXEvents();

        setSize( 320, 240 );
    }

    // Generic registration with the Mac OS X application menu
    // Checks the platform, then attempts to register with the Apple EAWT
    // See OSXAdapter.java to see how this is done without directly referencing any Apple APIs
    public void registerForMacOSXEvents() {
        if ( MAC_OS_X ) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods
                OSXAdapter.setQuitHandler( this, getClass().getDeclaredMethod( "quit", (Class[]) null ) );
                OSXAdapter.setAboutHandler( this, getClass().getDeclaredMethod( "about", (Class[]) null ) );
                OSXAdapter.setPreferencesHandler( this, getClass().getDeclaredMethod( "preferences", (Class[]) null ) );
                OSXAdapter.setFileHandler( this, getClass().getDeclaredMethod( "loadImageFile", new Class[] { String.class } ) );
            }
            catch ( Exception e ) {
                System.err.println( "Error while loading the OSXAdapter:" );
                e.printStackTrace();
            }
        }
    }

    // General info dialog; fed to the OSXAdapter as the method to call when 
    // "About OSXAdapter" is selected from the application menu
    public void about() {
        aboutBox.setLocation( (int) this.getLocation().getX() + 22, (int) this.getLocation().getY() + 22 );
        aboutBox.setVisible( true );
    }

    // General preferences dialog; fed to the OSXAdapter as the method to call when
    // "Preferences..." is selected from the application menu
    public void preferences() {
        prefs.setLocation( (int) this.getLocation().getX() + 22, (int) this.getLocation().getY() + 22 );
        prefs.setVisible( true );
    }

    // General quit handler; fed to the OSXAdapter as the method to call when a system quit event occurs
    // A quit event is triggered by Cmd-Q, selecting Quit from the application or Dock menu, or logging out
    public boolean quit() {
        int option = JOptionPane.showConfirmDialog( this, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION );
        return ( option == JOptionPane.YES_OPTION );
    }

    public void addMenus() {
        JMenu fileMenu = new JMenu( "File" );
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.add( fileMenu = new JMenu( "File" ) );
        fileMenu.add( openMI = new JMenuItem( "Open..." ) );
        openMI.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, MENU_MASK ) );
        openMI.addActionListener( this );

        // Quit/prefs menu items are provided on Mac OS X; only add your own on other platforms
        if ( !MAC_OS_X ) {
            fileMenu.addSeparator();
            fileMenu.add( optionsMI = new JMenuItem( "Options" ) );
            optionsMI.addActionListener( this );

            fileMenu.addSeparator();
            fileMenu.add( quitMI = new JMenuItem( "Quit" ) );
            quitMI.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, MENU_MASK ) );
            quitMI.addActionListener( this );
        }

        mainMenuBar.add( helpMenu = new JMenu( "Help" ) );
        helpMenu.add( docsMI = new JMenuItem( "Online Documentation" ) );
        helpMenu.addSeparator();
        helpMenu.add( supportMI = new JMenuItem( "Technical Support" ) );
        // About menu item is provided on Mac OS X; only add your own on other platforms
        if ( !MAC_OS_X ) {
            helpMenu.addSeparator();
            helpMenu.add( aboutMI = new JMenuItem( "About OSXAdapter" ) );
            aboutMI.addActionListener( this );
        }

        setJMenuBar( mainMenuBar );
    }

    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();
        if ( source == quitMI ) {
            quit();
        }
        else if ( source == optionsMI ) {
            preferences();
        }
        else if ( source == aboutMI ) {
            about();
        }
        else if ( source == openMI ) {
            // File:Open action shows a FileDialog for loading displayable images
            FileDialog openDialog = new FileDialog( this );
            openDialog.setMode( FileDialog.LOAD );
            openDialog.setFilenameFilter( new FilenameFilter() {
                public boolean accept( File dir, String name ) {
                    String[] supportedFiles = ImageIO.getReaderFormatNames();
                    for ( int i = 0; i < supportedFiles.length; i++ ) {
                        if ( name.endsWith( supportedFiles[i] ) ) {
                            return true;
                        }
                    }
                    return false;
                }
            } );
            openDialog.setVisible( true );
            String filePath = openDialog.getDirectory() + openDialog.getFile();
            if ( filePath != null ) {
                loadImageFile( filePath );
            }
        }
    }

    public void loadImageFile( String path ) {
        try {
            currentImage = ImageIO.read( new File( path ) );
            imageLabel.setIcon( new ImageIcon( currentImage ) );
            imageLabel.setBackground( (Color) colors[colorComboBox.getSelectedIndex()] );
            imageLabel.setText( "" );
        }
        catch ( IOException ioe ) {
            System.out.println( "Could not load image " + path );
        }
        repaint();
    }

    public static void main( String args[] ) {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                new MyApp().setVisible( true );
            }
        } );
    }
}