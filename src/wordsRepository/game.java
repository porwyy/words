package wordsRepository;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import project.nav;

public class game extends nav implements ItemListener {
	static JPanel jp1;
	static JComboBox choosStyle;
	static String user;
	public game(String user){
		super(user);
		this.user = user;
		getWayBox();
		choosStyle.addItemListener(this); 
		this.add(jp1);
		setTitle("单词游戏");
        setBounds(100,100,600,400);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3,1));
        this.setPreferredSize(new Dimension(400,400));
        this.setVisible(true);
	}
	static void getWayBox(){
		jp1 = new JPanel();
		choosStyle = new JComboBox();
		choosStyle.addItem("");
		choosStyle.addItem("中英选择");
		choosStyle.addItem("英中选择");
		choosStyle.addItem("单词默写"); 
		jp1.add(choosStyle);
		
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		game aaGame = new game("wyy");
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String style = choosStyle.getSelectedItem().toString();
			if(style.equals("中英选择")){
				new gameCE("wyy","CE");
			}else if(style.equals("英中选择")){
				new gameCE("wyy","EC");
			}else {
				
			}
        }       
	}
}
