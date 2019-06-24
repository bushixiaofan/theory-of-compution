package com.song.samples.tmdecider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TMDeciderAWT extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel p1=new JPanel();
    private JTextArea t1=new JTextArea();
    private JTextArea t2=new JTextArea();
    private JLabel L1=new JLabel("<TM,W>编码");
    private JLabel L2=new JLabel("运行结果");
 
	public TMDeciderAWT(){
    	this.setTitle("Decider");
	    this.setSize(650, 600);
	    this.setLocation(0,0);
	    
		Container c=this.getContentPane();
	     getContentPane().setLayout(null);
	     p1.setBounds(0, 0, 600, 600);
	     c.add(p1);
	     p1.setLayout(null);
	     //���ñ�ǩ
	     p1.add(L1);
	     L1.setBounds(1, 2, 200, 40);
	     p1.add(L2);
	     L2.setBounds(1, 450, 100, 40);
	    
	     //����text
	     t1.setBounds(100, 2, 450, 400);
	     t2.setBounds(100, 450, 300, 40);
	     t1.setLineWrap(true);
	     t2.setLineWrap(true);
	     
	    
	     p1.add(t1);
	     p1.add(t2);
	   
	     //����ȷ����ť
	     JButton b1=new JButton("运行");
	     b1.setBounds(480, 450, 100, 40);
	     p1.setBorder(BorderFactory.createLineBorder(Color.black));
	     p1.add(b1);
	     
	     b1.addActionListener(this);
	     
	     this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	     this.show();
	     this.setVisible(rootPaneCheckingEnabled);
	     
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd=e.getActionCommand();
		if(cmd.equals("运行")){

			String name=t1.getText();
			String []arrays=name.split("111");
			String nameEncode = arrays[2];
			String tmEncode = arrays[0] + "111" + arrays[1];
			TM myTm = TMFactory.tmDecode(tmEncode);
			System.out.println(myTm);
			int[] nameDecode = TMFactory.nameDecode(nameEncode);
			if(myTm.run(nameDecode)){
				t2.setText("accept");
			}
			else
				t2.setText("reject");
			
		}
		
	}

}
