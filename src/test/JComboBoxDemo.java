package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class JComboBoxDemo extends JFrame implements ItemListener
{  
    JComboBox jb;
   JPanel p=new JPanel();
  public JComboBoxDemo()
  {
      jb=new JComboBox();
      jb.addItem("选项1");
      jb.addItem("选项2");
      jb.addItem("选项3");
      jb.addItemListener(this);
       
       p.add( jb);
     this.getContentPane().add(p);  
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setSize(360, 260);  
    this.setVisible(true);  
 }  
   
 public void itemStateChanged(ItemEvent e)
 {
       if(e.getStateChange() == ItemEvent.SELECTED)
             {
   if(jb.getSelectedItem()=="选项1") {
System.out.println("选择了选项1");
} 
  if(jb.getSelectedItem()=="选项2") {
System.out.println("选择了选项2");
} 
  if(jb.getSelectedItem()=="选项3") {
System.out.println("选择了选项3");
} 
                
             }
}  
 public static void main(String args[])
  {  
    new JComboBoxDemo();  
   }  
}  