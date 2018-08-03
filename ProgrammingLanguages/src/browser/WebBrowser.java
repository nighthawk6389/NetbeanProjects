/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package browser;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.event.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author Mavis Beacon
 */
public class WebBrowser extends JFrame implements HyperlinkListener,ActionListener,ChangeListener,MouseListener,
        ComponentListener,WindowStateListener,KeyListener,PropertyChangeListener{

    private JPanel addressBarPanel,favoritesPanel,screenshotsPanel;
    private JTabbedPane tabs;
    private JMenuBar topMenu;
    private JMenu historyMenu,favoritesMenu;
    private JPopupMenu tabPopup,favoritesPopup,suggestionPopup;
    private JMenuItem newWindow,newTab,closeWindow,closeTab,print,exit,setHomepageItem,
            closeTabPopup,closeAllTabsPopup,closeAllExceptPopup,openInNewWindowPopup,openInNewTabPopup,
            deleteSettings,deleteHistory;
    private JCheckBoxMenuItem hideScreenshots;
    private JRadioButtonMenuItem windowsLookAndFeelItem,javaLookAndFeelItem;
    private JButton back,forward,go,searchButton,reload,homePage,setHomePage,addFavorite;
    private JTextField addressBar,searchBar;
    private JComboBox searchSelectCombo;
    private JScrollPane screenshotsScroll;
    //private JEditorPane page;
    private ArrayList<PageHistory> history=new ArrayList<PageHistory>();
    //private URL currentURL;
    private Settings settings;
    private RandomFacts facts=new RandomFacts();
    private String filename="ElkobiBrowserSettings";
    private String screenshotsFolder="screenshots";
    private String screenshotInfoFilename=screenshotsFolder+File.separator+"info";
    private int windowWidth,windowHeight,emptyTabTitleSpace=40;
    final static int OPEN_IN_NEW_WINDOW=1;
    final static int OPEN_IN_NEW_TAB=2;
    final static int OPEN_IN_SAME_TAB=0;
    final static int WINDOWS_LOOK=3,JAVA_LOOK=4;
    final static int MAX_NUMBER_SCREENSHOTS=8;
    private final static int NEW_SCREENSHOT_WIDTH=200;
    int addressBarLengthDivisor;
    boolean savedNewScreenShot=false;



   public static void main(String args []){
        WebBrowser frame  = new WebBrowser();
        frame.setVisible(true);
        frame.setSize(400, 600);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    } 

   public WebBrowser(){
       this(null);
   }

    public WebBrowser(String url){

        this.setBackground(new Color(207,219,236));

        ImageIcon logo=new ImageIcon("images/browserIcon.png");
        this.setIconImage(logo.getImage());

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.put("ComboBox.selectionBackground", Color.red);
        } catch (Exception e){}

        this.setTitle("Bowser the Browser");
        this.addWindowStateListener(this);

        Dimension screenSize = this.getToolkit().getScreenSize();
        windowWidth = screenSize.width;
        windowHeight = screenSize.height;
        setBounds(0, 0, windowWidth, windowHeight);

        settings=getSettings();

        JPanel allHold=new JPanel();
        allHold.setLayout(new BorderLayout());
        allHold.setBackground(new Color(207,219,236));

        JPanel north=new JPanel();
        north.setLayout(new BoxLayout(north,BoxLayout.PAGE_AXIS));
        north.setAlignmentX(LEFT_ALIGNMENT);

        topMenu=new JMenuBar();
        topMenu.setLayout(new FlowLayout(FlowLayout.LEADING));
        JMenu fileMenu=new JMenu("File");
        newWindow=new JMenuItem("New Window");
        newWindow.addActionListener(this);
        newTab=new JMenuItem("New Tab");
        newTab.addActionListener(this);
        closeWindow=new JMenuItem("Close Window");
        closeWindow.addActionListener(this);
        closeTab=new JMenuItem("Close Tab");
        closeTab.addActionListener(this);
        print=new JMenuItem("Print");
        print.addActionListener(this);
        exit=new JMenuItem("Exit");
        exit.addActionListener(this);
        fileMenu.add(newWindow);
        fileMenu.add(newTab);
        fileMenu.add(closeTab);
        fileMenu.add(closeWindow);
        fileMenu.addSeparator();
        fileMenu.add(exit);
        JMenu optionsMenu=new JMenu("Options");
        setHomepageItem=new JMenuItem("Set Homepage");
        setHomepageItem.addActionListener(this);
        deleteSettings=new JMenuItem("Delete Settings");
        deleteSettings.addActionListener(this);
        deleteHistory=new JMenuItem("Delete History");
        deleteHistory.addActionListener(this);
        hideScreenshots=new JCheckBoxMenuItem("Hide Screenshots");
        hideScreenshots.addActionListener(this);
        windowsLookAndFeelItem=new JRadioButtonMenuItem("Windows Look");
        windowsLookAndFeelItem.addActionListener(this);
        javaLookAndFeelItem=new JRadioButtonMenuItem("Java Look");
        javaLookAndFeelItem.addActionListener(this);
        ButtonGroup lookAndFeelGroup=new ButtonGroup();
        lookAndFeelGroup.add(windowsLookAndFeelItem);
        lookAndFeelGroup.add(javaLookAndFeelItem);
        windowsLookAndFeelItem.setSelected(true);
        optionsMenu.add(setHomepageItem);
        optionsMenu.addSeparator();
        optionsMenu.add(deleteSettings);
        optionsMenu.addSeparator();
        optionsMenu.add(deleteHistory);
        optionsMenu.addSeparator();
        optionsMenu.add(hideScreenshots);
        optionsMenu.addSeparator();
        optionsMenu.add(windowsLookAndFeelItem);
        optionsMenu.add(javaLookAndFeelItem);
        favoritesMenu=new JMenu("Favorites");
        populateFavoritesMenuFromSettings();
        historyMenu=new JMenu("History");
        topMenu.add(fileMenu);
        topMenu.add(favoritesMenu);
        topMenu.add(optionsMenu);
        topMenu.add(historyMenu);

        addressBarPanel=new JPanel();
        addressBarPanel.setBackground(new Color(207,219,236));
        addressBarPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        ImageIcon leftArrow=new ImageIcon("images/backArrow3.png");
        ImageIcon rightArrow=new ImageIcon("images/forwardArrow3.png");
        ImageIcon reloadIcon=new ImageIcon("images/reloadIcon2.png");
        ImageIcon homeIcon=new ImageIcon("images/homePageIcon2.png");
        ImageIcon magnify=new ImageIcon("images/magnify.png");
        back=new JButton(leftArrow);
        back.setToolTipText("Back");
        back.setName("address");
        coolButton(back,true,true,false);
        forward=new JButton(rightArrow);
        forward.setToolTipText("Forward");
        forward.setName("address");
        coolButton(forward,true,true,false);
        go=new JButton("Go");
        go.setName("address");
        coolButton(go,false,false,false);
        reload=new JButton(reloadIcon);
        reload.setToolTipText("Reload");
        reload.setName("address");
        coolButton(reload,true,true,false);
        homePage=new JButton(homeIcon);
        homePage.setToolTipText("HomePage");
        homePage.setName("address");
        coolButton(homePage,true,true,false);
        addressBar=new JTextField();
        addressBar.setPreferredSize(new Dimension(10,25));
        addressBarLengthDivisor=13;
        addressBar.setColumns(windowWidth/addressBarLengthDivisor);
        addressBar.setText("http://");
        String [] string={"Bing","Yahoo","Wolfram","Wikipedia"};
        searchSelectCombo=new JComboBox(string);
        searchSelectCombo.setBackground(Color.white);
        searchBar=new JTextField(20);
        searchButton=new JButton(magnify);
        searchButton.setName("address");
        searchButton.setToolTipText("Search");
        coolButton(searchButton,false,false,false);
        setHomePage=new JButton("Set as Homepage");
        setHomePage.setName("address");
        coolButton(setHomePage,false,false,false);
        back.addActionListener(this);
        forward.addActionListener(this);
        go.addActionListener(this);
        searchButton.addActionListener(this);
        reload.addActionListener(this);
        homePage.addActionListener(this);
        addressBar.addActionListener(this);
        setHomePage.addActionListener(this);
        back.addMouseListener(this);
        forward.addMouseListener(this);
        //go.addMouseListener(this);
        //searchButton.addMouseListener(this);
        reload.addMouseListener(this);
        homePage.addMouseListener(this);
        addressBar.addMouseListener(this);
        addressBar.addKeyListener(this);
        //setHomePage.addMouseListener(this);
        addressBarPanel.add(new JLabel("  "));
        addressBarPanel.add(back);
        addressBarPanel.add(forward);
        addressBarPanel.add(addressBar);
        addressBarPanel.add(go);
        addressBarPanel.add(reload);
        addressBarPanel.add(homePage);
        addressBarPanel.add(new JLabel("     "));
        addressBarPanel.add(searchSelectCombo);
        addressBarPanel.add(searchBar);
        addressBarPanel.add(searchButton);
        //addressBarPanel.add(cancel);
        addressBarPanel.add(new JLabel(" "));
        addressBarPanel.add(setHomePage);

        favoritesPanel=new JPanel();
        favoritesPanel.setBackground(new Color(207,219,236));
        favoritesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        addFavorite=new JButton("Add as favorite");
        addFavorite.addActionListener(this);
        coolButton(addFavorite,false,false,false);
        populateFavoritesPanelFromSettings();
        
        tabs=new JTabbedPane();
        tabs.add("New                                   ",makeTab());
        tabs.addTab("+ ",makeTab());
        tabs.setPreferredSize(new Dimension(windowWidth,windowHeight-175));
        tabs.addChangeListener(this);
        tabs.addMouseListener(this);
        tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        tabs.setFont(new Font("Arial",Font.BOLD,12));
        tabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        this.getEditorPaneFromTab().setText(getNewText());
        history.add(new PageHistory());

        north.add(topMenu);
        north.add(addressBarPanel);
        north.add(favoritesPanel);

        screenshotsPanel=new JPanel();
        screenshotsPanel.setBackground(new Color(207,219,236));
        populateScreenshotsPanelFromSaved();
        screenshotsScroll=new JScrollPane(screenshotsPanel);
        screenshotsScroll.setHorizontalScrollBar(null);
        
        allHold.add(north,"North");
        allHold.add(screenshotsScroll,"East");
        allHold.add(tabs,"Center");

        //popups
        tabPopup=new JPopupMenu();
        tabPopup.setInvoker(this);
        closeTabPopup=new JMenuItem("Close this tab");
        closeTabPopup.addActionListener(this);
        closeAllTabsPopup=new JMenuItem("Close all tabs");
        closeAllTabsPopup.addActionListener(this);
        closeAllExceptPopup=new JMenuItem("Close all other tabs");
        closeAllExceptPopup.addActionListener(this);
        tabPopup.add(closeTabPopup);
        tabPopup.add(closeAllTabsPopup);
        tabPopup.add(closeAllExceptPopup);
        favoritesPopup=new JPopupMenu();
        favoritesPopup.setInvoker(this);
        openInNewWindowPopup=new JMenuItem("Open In New Window");
        openInNewWindowPopup.addActionListener(this);
        openInNewTabPopup=new JMenuItem("Open In New Tab");
        openInNewTabPopup.addActionListener(this);
        favoritesPopup.add(openInNewWindowPopup);
        favoritesPopup.add(openInNewTabPopup);
        suggestionPopup=new JPopupMenu();
        suggestionPopup.setInvoker(this);
        suggestionPopup.setPreferredSize(new Dimension(windowWidth/addressBarLengthDivisor*8,200));
        populateSuggestionPopupMenu(' ');

        //website suggestion

        //setting all content
        this.setContentPane(allHold);

        //resizes tabs and panel to fit new dimensions on resize
        this.addComponentListener(this);
        resizeWindow();

        //after initialization actions
        updateHistoryMenu();
        if(url!=null)
            showPage(verifyURL(url),true);

    }//end constructor

    private void addFavorite(){
        String currentURL=this.getCurrentTabURL();
        settings.addFavorite(currentURL);
        saveSettings();
        this.populateFavoritesMenuFromSettings();
        this.clearFavoritesPanel();
        this.populateFavoritesPanelFromSettings();
        this.repaint();
    }

   private void back(){
        PageHistory ph=((PageHistory)history.get(tabs.getSelectedIndex()));
        if(!ph.isBackEnabled())
            return;
        String urlString=ph.back();
        URL goodURL=verifyURL(urlString);
        showPage(goodURL,false);
    }

   public void captureScreen() {
       JScrollPane sp=(JScrollPane)tabs.getSelectedComponent();
       JEditorPane panel=this.getEditorPaneFromTab();
       String screenshotFilename=this.getNameOfSite(this.getCurrentTabURL());
       if(screenshotFilename.indexOf('.')!=-1)
           return;
       try{
            BufferedImage bufImage = new BufferedImage(sp.getViewport().getWidth(), sp.getViewport().getHeight(),BufferedImage.TYPE_INT_RGB);
            panel.paint(bufImage.createGraphics());
            Image scaledImage=bufImage.getScaledInstance(WebBrowser.NEW_SCREENSHOT_WIDTH, -1, BufferedImage.SCALE_FAST);
            int newWidth=scaledImage.getWidth(null);
            int newHeight=scaledImage.getHeight(null);
            BufferedImage bufferedScaled=new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
            Graphics g=bufferedScaled.getGraphics();
            g.drawImage(scaledImage,0, 0, null);
            ImageIO.write(bufferedScaled, "png", new File("screenshots/"+screenshotFilename+".png"));

            updateScreenshotInfo(screenshotFilename,this.getCurrentTabURL());
       }
       catch(Exception e){
           System.out.println("Exception in Capture Screen: "+e.toString());
       }
}

    private void coolButton(JButton button,boolean a,boolean b,boolean c){
            button.setContentAreaFilled(true);
            button.setBorderPainted(true);
            button.setFocusPainted(true);
        if(a)
            button.setContentAreaFilled(false);
        if(b)
            button.setBorderPainted(false);
        if(c)
            button.setFocusPainted(false);
        button.setMargin(new Insets(2,2,2,2));
    }//end coolButton

    private void changeUI(int UI){
        if(UI==WebBrowser.WINDOWS_LOOK){
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                addressBarLengthDivisor=13;
                addressBar.setColumns(windowWidth/addressBarLengthDivisor);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception e){}
        }//end if
        else if(UI==WebBrowser.JAVA_LOOK){
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                addressBarLengthDivisor=18;
                addressBar.setColumns(windowWidth/addressBarLengthDivisor);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception e){System.out.println("Exception: "+e.toString());}
        }
    }//end changeUI

    private void checkToSaveScreenshot(){
        if(this.savedNewScreenShot)
            return;
        if(isMaximumNumberOfScreenshots()){
            deleteOldestScreenshot();
        }

        this.captureScreen();

        populateScreenshotsPanelFromSaved();

        this.resizeWindow();
        
    }

    private void createMiniPanes(){
        /*
        JPanel panel=new JPanel();
        Font f=this.getFont().deriveFont(18);
        Iterator it=settings.getFavorites().iterator();
        JEditorPane a=new JEditorPane();
        a.setFont(f);
        URL url=verifyURL((String)it.next());
        try {
            a.setPage(url);
        } catch (IOException ex) {
            System.out.println("Io exception in createMiniPane");
        }
        panel.add(a);
        //return panel;
        
        JButton b=new JButton("Hello!!!!!!!!");
        return b;
         * *
         */

    }

    private void createNewScreenshots(){
        showPage(verifyURL("www.sidebump.com"),false);
        captureScreen();
        //insertNewTab();
        //this.removeAllExcept();

    }

    private void clearFavoritesPanel(){
        favoritesPanel.removeAll();
        this.repaint();
    }//end clearFavoritesPanel

    private void closeWindow(){
        this.dispose();
    }
    private void deleteSettings(){
        settings=new Settings();
        saveSettings();
        this.clearFavoritesPanel();
        populateFavoritesPanelFromSettings();
        deleteScreenshots();
        populateScreenshotsPanelFromSaved();
    }
    private void deleteHistory(){
        settings.resetHistory();
        saveSettings();
        updateHistoryMenu();
    }

    private void deleteOldestScreenshot(){
        File [] arrayOfFiles=new File("screenshots").listFiles();
        File oldest=arrayOfFiles[0];
        for(File file:arrayOfFiles){
            if(oldest.lastModified()<file.lastModified()){
                oldest=file;
            }//end if
        }//end for
        boolean error=oldest.delete();
        if(!error)
            System.out.println("ERROR IN DELETE OF OLDEST SCREENSHOT");
    }//end deleteOldest

    private void deleteScreenshots(){
        File [] arrayOfFiles=new File("screenshots").listFiles();
        for(File file:arrayOfFiles){
            file.delete();
        }//end for
    }

    private void displayFavoritePopupMenu(MouseEvent e){
        System.out.println("In favoritePopupMenu");
        favoritesPopup.setLocation(e.getXOnScreen()+10, e.getYOnScreen()+5);
        favoritesPopup.setVisible(true);
        favoritesPopup.setLabel(((JButton)e.getSource()).getToolTipText());
    }

    private void displayTabPopupMenu(MouseEvent e) {
        tabPopup.setLocation(e.getXOnScreen()+10, e.getYOnScreen()+5);
        tabPopup.setVisible(true);
    }

    private void displaySuggestionPopupMenu(char toAdd){
        int xCoor=addressBar.getX();
        int yCoor=addressBar.getY();
        suggestionPopup.setLocation(xCoor, yCoor+80);
        suggestionPopup.setVisible(true);
        populateSuggestionPopupMenu(toAdd);
        addressBar.requestFocus();

    }

    private void forward(){
        PageHistory ph=((PageHistory)history.get(tabs.getSelectedIndex()));
        if(!ph.isForwardEnabled())
            return;
        String urlString=ph.forward();
        URL goodURL=verifyURL(urlString);
        showPage(goodURL,false);
    } 

   private JEditorPane getEditorPaneFromTab(){
        return (JEditorPane)(((JScrollPane)tabs.getSelectedComponent()).getViewport().getView());
    }
    
    ////////////////////////////////////////////////////
    //Gets the URL from the current tab selected and
    //returns null if its a new tab
    ///////////////////////////////////////////////////
    private String getCurrentTabURL(){
        String url;
        if(this.getEditorPaneFromTab().getPage()==null)
            url="http://";
        else
            url=this.getEditorPaneFromTab().getPage().toString();
        return url;
    }

    private String getNameOfSite(String s){
        s=s.trim();
        int beginIndex;
        String testString="http://www.";
        beginIndex=s.indexOf(testString);
        if(beginIndex==-1){
            testString="http://";
            beginIndex=s.indexOf(testString);
        }
        if(beginIndex==-1){
            testString="www.";
            beginIndex=s.indexOf(testString);
        }
        if(beginIndex==-1){
            testString="";
            beginIndex=0;
        }

        beginIndex=beginIndex+testString.length();

        int endIndex;
        endIndex=s.indexOf(".com", beginIndex);
        if(endIndex==-1)
            endIndex=s.indexOf(".edu",beginIndex);
        if(endIndex==-1)
            endIndex=s.indexOf(".net",beginIndex);
        if(endIndex==-1)
            endIndex=s.indexOf(".",beginIndex);
        if(endIndex==-1)
            endIndex=s.length();
        String name=s.substring(beginIndex, endIndex);
        char firstLetter=name.charAt(0);
        char newFirstLetter=Character.toUpperCase(firstLetter);
        name=name.replace(firstLetter,newFirstLetter);
        return name;
    }//end getNameOfSIte

    private String getNewText(){
        String s="This is a new tab.";
        return s;
    }//end getNewText

    private Settings getSettings(){
        Settings s;
        try{
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            s = (Settings)in.readObject();
            in.close();
        } catch (Exception e) {
                return new Settings();
        }
        return s;
    }

    private ScreenshotInfo getScreenshotInfo(){
        ScreenshotInfo s;
        try{
            FileInputStream fis = new FileInputStream(screenshotInfoFilename);
            ObjectInputStream in = new ObjectInputStream(fis);
            s = (ScreenshotInfo)in.readObject();
            in.close();
        } catch (Exception e) {
                return new ScreenshotInfo();
        }
        return s;
    }

    private void go(){

        URL goodURL=verifyURL(addressBar.getText().trim());
        if(goodURL==null){
            showError("GO: Invalid URL");
        }
        showPage(goodURL,true);
    }//end go

    private void goToFavorite(String url,int openCode){
        if(openCode==WebBrowser.OPEN_IN_NEW_WINDOW)
            newWindow(url);
        else if(openCode==WebBrowser.OPEN_IN_NEW_TAB)
            insertNewTab(url);
        else
            showPage(verifyURL(url),true);

        favoritesMenu.setSelected(false);
        favoritesMenu.setPopupMenuVisible(false);
    }

    private void goToHistory(String url){
        showPage(verifyURL(url),true);
        historyMenu.setSelected(false);
        historyMenu.setPopupMenuVisible(false);
    }

    private void goToSuggestion(String url){
        suggestionPopup.setVisible(false);
        showPage(verifyURL(url),true);
    }

    private void goToScreenshot(String url){
        showPage(verifyURL(url),true);
    }
 
    private void homePage(){
        URL goodURL=verifyURL(settings.getHomePage());
        if(goodURL==null){
            showError("GO: Invalid URL");
        }
        showPage(goodURL,true);
    }

    private void hideScreenshots(boolean hide){
        screenshotsScroll.setVisible(!hide);
        //screenshotsPanel.setVisible(!hide);
        this.resizeWindow();
    }

    private void insertNewTab(boolean showDefault){
        int tabCount=tabs.getTabCount();
        history.add(tabCount-1, new PageHistory());
        tabs.setSelectedIndex(tabCount-2);
        tabs.insertTab("New                                    ", null, makeTab(), "Your Tab!", tabCount-1);
        tabCount=tabs.getTabCount();
        tabs.setSelectedIndex(tabCount-2);

        if(showDefault){
            //showPage(verifyURL("http://www.randomfunfacts.com/"),false);
            this.getEditorPaneFromTab().setText("<html><body><h3 style=\"margin:10px\">"+facts.getRandomFact()+"</h3></body></html>");
        }

    }//end addNewTab;

    //Open URL in new tab
    private void insertNewTab(String urlToOpen){
       insertNewTab(false);
       showPage(verifyURL(urlToOpen),true);
    }

    private boolean isAddressBarButton(Object o){
        if(o instanceof JButton && ((JButton)o).getName().equals("address"))
            return true;
        return false;
    }

    private boolean isFavorite(Object o){
        if(o instanceof JButton && ((JButton)o).getName().equals("favorite"))
            return true;
        return false;
    }//end isFavorite

    private boolean isHistory(Object o){
        if(o instanceof JButton && ((JButton)o).getName().equals("history"))
            return true;
        return false;
    }//end isFavorite

    private boolean isMaximumNumberOfScreenshots(){
        int numberOfFiles=new File("screenshots").listFiles().length;
        if(numberOfFiles>=MAX_NUMBER_SCREENSHOTS)
            return true;
        return false;
    }

    private boolean isSuggestion(Object o){
        if(o instanceof JButton && ((JButton)o).getName().equals("suggestion"))
            return true;
        return false;
    }//end isFavorite

    private boolean isScreenshotButton(Object o){
        if(o instanceof JButton && ((JButton)o).getName().equals("screenshot"))
            return true;
        return false;
    }

    private JScrollPane makeTab(){
        JEditorPane panel=new JEditorPane();
        JScrollPane scroll=new JScrollPane(panel);
        HTMLEditorKit kit=new HTMLEditorKit();
        scroll.setAutoscrolls(false);
        panel.setBackground(Color.white); 
        panel.setEditable(false);
        panel.setAutoscrolls(false);
        panel.setContentType("text/html");
        panel.setEditorKit(kit);
        panel.addHyperlinkListener(this);
        panel.addPropertyChangeListener("page", this);
        //return scroll;
        return scroll;
    }//end makeTab

    private void newWindow(String urlToOpen){
        WebBrowser frame  = new WebBrowser(urlToOpen);
        frame.setVisible(true);
        frame.setSize(900,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void populateFavoritesMenuFromSettings(){
        favoritesMenu.removeAll();
        Iterator it=settings.getFavorites().iterator();
        JButton b;
        String name;
        String full;
        while(it.hasNext()){
            full=(String)it.next();
            name=getNameOfSite(full);
            b=new JButton(name+" - "+full);
            b.setName("favorite");
            b.setToolTipText(full);
            b.addActionListener(this);
            b.addMouseListener(this);
            favoritesMenu.add(b);
            coolButton(b,true,true,false);
            favoritesMenu.addSeparator();
        }//end while
    }//end populate

    private void populateScreenshotsPanelFromSaved(){
        File screenshotsFile=new File(this.screenshotsFolder);
        if(!screenshotsFile.exists())
            return;
        File [] screenshots=screenshotsFile.listFiles();
        ScreenshotInfo ssi=this.getScreenshotInfo();
        screenshotsPanel.removeAll();
        screenshotsPanel.setLayout(new GroupLayout(screenshotsPanel));
        GroupLayout layout=(GroupLayout)screenshotsPanel.getLayout();
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        SequentialGroup vg=layout.createSequentialGroup();
        SequentialGroup hg=layout.createSequentialGroup();
        Group vGroup=layout.createSequentialGroup();
        Group hGroup=layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        JButton b;
        JLabel label;
        for(File f:screenshots){
            if(f.getName().equals("info"))
                continue;
            int index=f.getName().indexOf(".png");
            String subString=f.getName().substring(0, index);
            label=new JLabel(subString);
            label.setFont(new Font("Times New Roman",0,18));
            label.setHorizontalAlignment(JLabel.CENTER);
            b=new JButton(new ImageIcon(f.getPath()));
            b.setName("screenshot");
            b.setToolTipText(ssi.getScreenshot(subString));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            b.addActionListener(this);
            b.setMargin(new Insets(5,5,5,5));
            vGroup.addComponent(label);
            vGroup.addComponent(b);
            vGroup.addGap(20);
            hGroup.addComponent(label);
            hGroup.addComponent(b);
            //screenshotsPanel.add(label);
            //screenshotsPanel.add(b);
        }//end for
        vg.addGroup(vGroup);
        hg.addGroup(hGroup);
        layout.setHorizontalGroup(hg);
        layout.setVerticalGroup(vg);
    }

    private void populateFavoritesPanelFromSettings(){
        favoritesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JPanel temp=new JPanel();
        temp.setBackground(new Color(207,219,236));
        temp.add(new JLabel("Favorites:"));
        Iterator it=settings.getFavorites().iterator();
        JButton b;
        String name;
        String full;
        while(it.hasNext()){
            full=(String)it.next();
            name=getNameOfSite(full);
            b=new JButton(name);
            b.setName("favorite");
            b.setToolTipText(full);
            b.addActionListener(this);
            b.addMouseListener(this);
            temp.add(b);
            coolButton(b,true,false,false);
        }//end while
        temp.add(addFavorite);
        favoritesPanel.add(temp);
        favoritesPanel.revalidate();
    }//end populate

    private void populateSuggestionPopupMenu(char toAdd){
        suggestionPopup.removeAll();
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);

        int indexHTTP=addressBar.getText().indexOf("http://");
        int start=0;
        if(indexHTTP!=-1)
            start=7;
        if(toAdd==KeyEvent.VK_BACK_SPACE)
            toAdd=' ';
        String fullAddressBarText=(addressBar.getText()+toAdd).trim();
        String currentText="";
        if(fullAddressBarText!=null)
            currentText=fullAddressBarText.substring(start);
        Pattern pattern=Pattern.compile(currentText);
        System.out.println(pattern.pattern());

        Iterator it=settings.getHistory().iterator();
        JButton b;
        String name;
        String full;
        int x=0;
        while(it.hasNext() && x<15){
            full=(String)it.next();
            name=this.getNameOfSite(full);
            Matcher matcher=pattern.matcher(full);
            if(!matcher.find())
                continue;
            b=new JButton(name+" - "+full);
            b.setToolTipText(full);
            b.setName("suggestion");
            b.addActionListener(this);
            b.addMouseListener(this);
            panel.add(b);
            panel.add(new JSeparator(SwingConstants.HORIZONTAL));
            coolButton(b,true,false,false);
            x++;
        }//end while
        JScrollPane scroll=new JScrollPane(panel);
        suggestionPopup.add(scroll);
        suggestionPopup.validate();
    }

    private void reload(){
        URL goodURL=verifyURL(this.getCurrentTabURL());
        if(goodURL==null){
            showError("Reload: Invalid URL");
            return;
        }
        JEditorPane singleTab=this.getEditorPaneFromTab();
        Document doc = singleTab.getDocument();
        doc.putProperty(Document.StreamDescriptionProperty, null);
        showPage(goodURL,false);
    }//end reload

    private void removeAllExcept(){
        int currentIndex=tabs.getSelectedIndex();
        removeAllTabs(currentIndex);
    }
    private void removeAllTabs(int exception){ 
        int tabCount=tabs.getTabCount();
        System.out.println("Remove all: Tabcount= "+tabCount);
        int currentDeleting=0;
        for(int x=0;x<tabCount-1;x++){
            if(tabs.getSelectedIndex()==0)
                currentDeleting=1;
            removeTabAtIndex(currentDeleting);
        }//end for

    }//end removeALlTabs

    private void removeCurrentTab(){
        int currentIndex=tabs.getSelectedIndex();
        removeTabAtIndex(currentIndex);
        
    }
    private void removeTabAtIndex(int index){
        if(tabs.getTabCount()==2)
            return;
        if(tabs.getSelectedIndex()==index && index>0)
            tabs.setSelectedIndex(index-1);  //Make sure tab removed isnt the selected tab
        history.remove(index);  //Remove from history arraylist
        tabs.removeTabAt(index);  //remove from JTabbedPane
    }
    private void resizeWindow(){
        Dimension d=this.getSize();
        windowWidth = d.width;
        windowHeight = d.height;
        setBounds(this.getX(), this.getY(), windowWidth, windowHeight);
        tabs.setPreferredSize(new Dimension(windowWidth,windowHeight-175));
        addressBar.setColumns(windowWidth/addressBarLengthDivisor);
        suggestionPopup.setPreferredSize(new Dimension(windowWidth/addressBarLengthDivisor*8,200));
        this.validate();
    }

    private void saveScreenshotInfo(ScreenshotInfo ssi){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(this.screenshotInfoFilename));
	    outputStream.writeObject(ssi);
            outputStream.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a problem saving your screenshotInfo("+this.screenshotInfoFilename+"): "+e.toString());
        }
        System.out.println("Saved");
    }

    private void saveSettings(){
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
	    outputStream.writeObject(settings);
            outputStream.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a problem saving your settings: "+e.toString());
        }
        System.out.println("Saved");
    }

    private void search(){
        String engine=(String)searchSelectCombo.getSelectedItem();
        String searchURL="";
        if(engine.equals("Bing"))
            searchURL="http://www.bing.com/search?q=";
        else if(engine.equals("Yahoo"))
            searchURL="http://search.yahoo.com/search?p=";
        else if(engine.equals("Wolfram"))
            searchURL="http://www.wolframalpha.com/input/?i=";
        else if(engine.equals("Wikipedia"))
            searchURL="http://en.wikipedia.org/wiki/?search=";
        String searchString=searchBar.getText().trim();
        showPage(verifyURL(searchURL + searchString), true);
    }

    private void setHomePage(){
        String homePageString=this.getCurrentTabURL();
        setHomePage(homePageString);
    }

   private void setHomePage(String homePageString){
        if(homePageString.equals("http://")){
            showError("The URL must be longer than http://");
            return;
       }
        if(verifyURL(homePageString)==null){
            showError("The URL must begin with http:// and be a valid URL");
            return;
       }
        settings.setHomePage(homePageString);
        saveSettings();
   }
   
   private void setTabTitle(String url){
        int length=getNameOfSite(url).length();
        String spacer="";
        for(int x=0;x<this.emptyTabTitleSpace-length;x++){
            spacer=spacer+" ";
       }
        tabs.setTitleAt(tabs.getSelectedIndex(), getNameOfSite(url)+spacer);
    }

   private void setUserInputHomepage(){
       String current=this.getCurrentTabURL();
       String s=JOptionPane.showInputDialog("Please enter a valid URL to set as your Homepage (must begin with http://)",current);
       this.setHomePage(s);
   }

   private void showError(String error){
        JOptionPane.showMessageDialog(null, error);
    }

    private void showPage(URL goodURL,boolean add){
        if(goodURL==null)
            return;

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        JEditorPane singleTab=null;
        try{

            singleTab=this.getEditorPaneFromTab();
            singleTab.setPage(goodURL);
            URL newURL=singleTab.getPage();
            setTabTitle(newURL.toString());
            
            if(add){
                ((PageHistory)history.get(tabs.getSelectedIndex())).addToVisitedPages(newURL);
                settings.addHistory(newURL.toString());
                saveSettings();
            }//end if

            addressBar.setText(newURL.toString());

            updateHistoryMenu();

            //updateButtons;
        }catch(IOException ex){
            tabs.setTitleAt(tabs.getSelectedIndex(), "404 Page Not Found");
            singleTab.setText("Oops! It looks like you are awful at entering URLs. Please Try again once you learn how to use a keyboard and the internet");
            showError(ex.toString());
        }
        catch(Exception e){
            showError("ShowPage: "+e.toString());
        }finally{
            setCursor(Cursor.getDefaultCursor());
        }
    }//end showPage

    private void toggleHoverEffects(JButton b, boolean display){
        b.setContentAreaFilled(display);
        b.setBorderPainted(display);
    }

    private void updateHistoryMenu(){
        historyMenu.removeAll();
        ArrayList<String> array=settings.getHistory();
        int size=array.size();
        int subtractSize=0;
        if(size>=15)
            subtractSize=size-15;
        Iterator it=array.listIterator(subtractSize);
        ArrayList<String> already=new ArrayList<String>();
        JButton b;
        String full;
        String name;
        int i=0; 
        while(it.hasNext() && i<15){
            full=(String)it.next();
            name=this.getNameOfSite(full);
            b=new JButton(name+" - "+full);
            b.setToolTipText(full);
            b.setName("history");
            b.addActionListener(this);
            b.addMouseListener(this);
            historyMenu.add(b);
            coolButton(b,true,false,false);
            already.add(full);
        }//end while
    }

    private void updateScreenshotInfo(String name,String url){
        ScreenshotInfo ssi=this.getScreenshotInfo();
        ssi.addScreenshot(name, url);
        saveScreenshotInfo(ssi);
    }
    
    private URL verifyURL(String urlString){
        if(!urlString.toLowerCase().startsWith("http://")){
            urlString="http://"+urlString;
        }
        URL url;
        try{
            url=new URL(urlString);
        }catch(Exception e){
            System.out.println("Bad URL");
            return null ;
        }

        return url;
    }//end verifyURL

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
            showPage(e.getURL(),true);
    }

    public void actionPerformed(ActionEvent e) {
        Object o=e.getSource();
        
        if(o==forward)
            forward();
        else if(o == back)
            back();
        else if(o == go)
            go();
        else if(o == searchButton)
            search();
        else if(o==reload)
            reload();
        else if(o==homePage)
            homePage();
        else if(o==newTab)
            insertNewTab(true);
        else if(o==closeTab)
            removeCurrentTab();
        else if(o==newWindow)
            newWindow(null);
        else if(o==closeWindow)
            closeWindow();
        else if(o==exit)
            closeWindow();
        else if(o==setHomepageItem)
            setUserInputHomepage();
        else if(o==deleteSettings)
            deleteSettings();
        else if(o==deleteHistory)
            deleteHistory();
        else if(o==hideScreenshots)
            hideScreenshots(hideScreenshots.isSelected());
        else if(o==windowsLookAndFeelItem)
            changeUI(WebBrowser.WINDOWS_LOOK);
        else if(o==javaLookAndFeelItem)
            changeUI(WebBrowser.JAVA_LOOK);
        else if(o==setHomePage)
            setHomePage();
        else if(o==closeTabPopup)
            removeCurrentTab();
        else if(o==closeAllTabsPopup)
            removeAllTabs(-1);
        else if(o==closeAllExceptPopup)
            removeAllExcept();
        else if(o==openInNewWindowPopup)
            goToFavorite(favoritesPopup.getLabel(),WebBrowser.OPEN_IN_NEW_WINDOW);
        else if(o==openInNewTabPopup)
            goToFavorite(favoritesPopup.getLabel(),WebBrowser.OPEN_IN_NEW_TAB);
        else if(o==addFavorite)
            addFavorite();
        else if(o==addressBar)
            go();
        else if(o==searchBar)
            search();
        else if(isFavorite(o))
            goToFavorite(((JButton)e.getSource()).getToolTipText(),WebBrowser.OPEN_IN_SAME_TAB);
        else if(isHistory(o))
            goToHistory(((JButton)e.getSource()).getToolTipText());
        else if(isSuggestion(o))
            goToSuggestion(((JButton)e.getSource()).getToolTipText());
        else if(isScreenshotButton(o))
            goToScreenshot(((JButton)e.getSource()).getToolTipText());
        else
            JOptionPane.showMessageDialog(null, "No Action Found. ");
        
    }

    public void stateChanged(ChangeEvent e) {
        if(tabs.getSelectedIndex()==tabs.getTabCount()-1)
            insertNewTab(true);
        JEditorPane pane=this.getEditorPaneFromTab();
        if(pane.getPage()==null)
            addressBar.setText("http://");
        else
            addressBar.setText(pane.getPage().toString());

        updateHistoryMenu();
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON3 && e.getSource()==tabs)
            displayTabPopupMenu(e);
        else if(e.getButton()==MouseEvent.BUTTON3 && isFavorite(e.getSource()))
            displayFavoritePopupMenu(e);
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        Object o=e.getSource();
         if(isFavorite(o) || isHistory(o) || isSuggestion(o) || isAddressBarButton(o))
            toggleHoverEffects((JButton)e.getSource(),true);
    }

    public void mouseExited(MouseEvent e) {
        Object o=e.getSource();
        if(isFavorite(o) || isHistory(o) || isSuggestion(o) || isAddressBarButton(o))
            toggleHoverEffects((JButton)e.getSource(),false);
    }

    public void componentResized(ComponentEvent e) {
        resizeWindow();
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void windowStateChanged(WindowEvent e) {
        if(e.getNewState()==Frame.MAXIMIZED_BOTH  || e.getOldState()==Frame.MAXIMIZED_BOTH)
            resizeWindow();
    }

    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()==KeyEvent.VK_ENTER){
            suggestionPopup.setVisible(false);
            return;
        }
        if(e.getSource()==addressBar)
            displaySuggestionPopupMenu(e.getKeyChar());

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void propertyChange(PropertyChangeEvent evt) {
        checkToSaveScreenshot();
    }




}//end class
