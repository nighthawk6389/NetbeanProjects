package browser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class BrowserFrame extends JFrame {
	//private Data data;
	private JTextField txt;
	private JTabbedPane tPane;
	//private HashMap<JComponent,String> currentURLs=new HashMap<JComponent,String>();
	private String defaultText="Type web address or file path here to begin surfing...";
        private JProgressBar bar;
        private int frameWidth;
        private int frameHeight;
	public BrowserFrame() {
		super("JavaBrowser");
		//data=new Data();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		frameWidth = screenSize.width;
		frameHeight = screenSize.height;
		setSize(frameWidth, frameHeight);

		setIconImage(kit.getImage("icon.jpg"));
		LayoutManager bl = new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS);
		setLayout(bl);
		JPanel topPanel = new JPanel();

		LayoutManager bl1 = new BoxLayout(topPanel, BoxLayout.PAGE_AXIS);
		topPanel.setLayout(new BorderLayout());
		// ((FlowLayout) topPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		// topPanel.setAlignmentX(LEFT_ALIGNMENT);
		topPanel.setSize(frameWidth,frameHeight/7);
		int debug= topPanel.getHeight();
		@SuppressWarnings("unused")
		int debug1=topPanel.getWidth();

                JPanel container=new JPanel();
                LayoutManager bl2 = new BoxLayout(container, BoxLayout.PAGE_AXIS);
                container.setLayout(bl2);

		makeMenu(container);
		makeAddressBar(container);
                topPanel.add(container,"North");
		makeTabs(topPanel);
	//	getContentPane().add(topPanel, BorderLayout.NORTH);
		int debug2= topPanel.getHeight();
		int debug3=topPanel.getWidth();


		bar=new JProgressBar();
		bar.setValue(0);

		//getContentPane().add(bar, BorderLayout.CENTER);
                getContentPane().add(topPanel);
		getContentPane().add(bar);
		nTab();
	}

        public static void main(String args []){
            BrowserFrame f=new BrowserFrame();
            f.setVisible(true);
            f.setSize(400,200);
            
        }

	public void makeAddressBar(JPanel jp) {
		JPanel addBar=new JPanel();
		((FlowLayout)addBar.getLayout()).setAlignment(FlowLayout.LEFT);
 txt= new JTextField("http://",100);
 txt.setFont(new Font("Comic Sans MS",Font.PLAIN,12));
 JButton go=new JButton("Go");
txt.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e) {

			changePage(txt.getText());

	}});
txt.addMouseListener(new MouseListener(){



	@Override
	public void mouseClicked(MouseEvent e) {

		txt.setFocusable(true);
		txt.requestFocusInWindow();
		if(txt.getText().equals(defaultText)){
	txt.setText("");	}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}});
go.addActionListener(new ActionListener(){

	@Override
	public void actionPerformed(ActionEvent e) {
	changePage(txt.getText());


	}



});
//addBar.setSize(frameWidth,txt.getHeight());
addBar.add(txt);
addBar.add(go);
addBar.setBackground(Color.red);

jp.add(addBar,"North");
int debug=jp.getWidth();
int debug1=jp.getHeight();

	}

	public void makeTabs(JPanel jp) {
		tPane = new JTabbedPane();
		tPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		//tPane.setLayout(new FlowLayout());
		tPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int n = tPane.getSelectedIndex();
				//txt.setText(data.getCurrentURLs().get(((Container)tPane.getComponentAt(n))));
				setTitle(txt.getText());
			}
		});
		jp.add(tPane,"Center");

	}

	public void makeMenu(JPanel jp) {
		JPanel menuHolder = new JPanel();
		((FlowLayout) menuHolder.getLayout()).setAlignment(FlowLayout.LEFT);
		JMenuBar mb = new JMenuBar();
		//mb.setSize(mb.getMaximumSize().width, mb.getMaximumSize().height);
		// mb.setAlignmentX(Component.LEFT_ALIGNMENT);
		JMenu fm = new JMenu("File");

		// fm.setAlignmentX(Component.LEFT_ALIGNMENT);
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem nTab = new JMenuItem("New Tab");
		// nTab.setAlignmentX(LEFT_ALIGNMENT);
		nTab.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nTab();
			}
		});
		nTab.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
		fm.add(nTab);
		fm.addSeparator();
		fm.add(exit);


		// jp.add(mb);
		JMenu hm = new JMenu("History");
		JMenuItem view = new JMenuItem(" View History...");
		view.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {

						nTab();
						File h=new File("history.html");
						try {
							changePage("file:"+h.getCanonicalPath());

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}




				});

		JMenuItem clear=new JMenuItem("Clear History");
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//data.clearHistory();
			}

		});
		mb.add(fm);
		hm.add(clear);
		hm.add(view);
		mb.add(hm);

		menuHolder.add(mb);
		jp.add(menuHolder,"North");
	}

	public void nTab() {
		JPanel jp=new JPanel();
		jp.setBounds(0,0,500,500);
		jp.setLayout(new BorderLayout());
//		JButton b=new JButton("Test");
		JEditorPane jep=new SEditorPane();
		jep.setBounds(0,0,300,300);
	//jep.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
		jep.setEditable(false);
		jep.addHyperlinkListener(new HyperlinkListener(){

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)){
					changePage(e.getURL().toString());
				}

			}});
	//	JScrollPane scroll= new JScrollPane(jep);



		//jp.add(jep);


		//jp.add(scroll,BorderLayout.NORTH);


                        /*
		File hello=new File("newTab.html");

		try {
			jep.setPage("file:"+hello.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tPane.addTab("Browse Away!", jp);
		jp.add(jep);
                         * 
                         */


		JScrollPane scroll= new JScrollPane(jp);
		//scroll.setBounds(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
	//	scroll.getViewport().setBounds(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
	//	scroll.getVerticalScrollBar().setBounds(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
	//	scroll.getHorizontalScrollBar().setBounds(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

		//String debug="";
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scroll.getc
		tPane.addTab("Browse Away!", scroll);
		int i=tPane.getComponentCount();
		//tPane.setSelectedComponent(tPane.getComponentAt(tPane.getComponents().length-1));
		txt.setText(defaultText);
		txt.setFocusable(false);

	}

public void changePage(String url){

	try {
		//if(url==null){
		//url=txt.getText();}
		JComponent curPanel=(JComponent)tPane.getComponentAt(tPane.getSelectedIndex());
		Component[] comps=curPanel.getComponents();
		JEditorPane jep=null;

	//	if(comps[0] instanceof JScrollPane){
	//		jep=(JEditorPane) ((JScrollPane) comps[0]).getViewport().getComponent(0);



	//	}
		/*
		for(int i=0;i<comps.length;i++){

			if(comps[i] instanceof JEditorPane){
				jep=(JEditorPane) comps[i];}
			}
		*/

		for(int i=0;i<comps.length;i++){
			Component[] comps1=((Container) comps[i]).getComponents();
			for(int j=0;j<comps1.length;j++){
				Component[] comps2=((Container) comps1[j]).getComponents();
				for(int k=0;k<comps2.length;k++){
			if(comps2[k] instanceof SEditorPane){
				jep=(SEditorPane) comps2[k];}
			}
			}
		}

	//	comps[0].setBounds(0,0,Integer.MAX_VALUE,Integer.MAX_VALUE);
//		((JScrollPane) comps[0]).getViewport().setBounds(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
		if((!url.startsWith("http://")) &&(!url.startsWith("file:"))){
			if(Character.isLetter(url.charAt(0))&&url.charAt(1)==':'){
				url="file:"+url;
			}
			else {url="http://"+url;}}

		bar.setIndeterminate(true);

		jep.setPage(url);
		bar.setIndeterminate(false);
		bar.setValue(0);
	setTitle("JavaBrowser - "+url);
	txt.setText(url);
	//data.addURL(curPanel, url);

	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
class SEditorPane extends JEditorPane{

	public boolean getScrollableTracksViewportHeight(){


		return true;
	}
	public boolean getScrollableTracksViewportWidth(){


		return true;
	}

}

}