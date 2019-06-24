package com.song.samples.tmdecider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TMRunAWT extends JFrame implements ActionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JTextArea t1 = new JTextArea();
    private JTextArea t11 = new JTextArea();
    private JTextArea t2 = new JTextArea();
    private JTextArea t3 = new JTextArea();
    private JTextArea t4 = new JTextArea();
    private JTextArea t5 = new JTextArea();
    private JLabel L1 = new JLabel("姓名");
    private JLabel L11 = new JLabel("Unicode编码");
    private JLabel L2 = new JLabel("运行结果");
    private JLabel L3 = new JLabel("姓名二进制编码");
    private JLabel L4 = new JLabel("TM二进制编码 ");
    private JLabel L5 = new JLabel("<TM,name>二进制编码");

    @SuppressWarnings("deprecation")
    public TMRunAWT() {
        this.setTitle("Decider");
        this.setSize(610, 610);
        this.setLocation(0, 0);

        Container c = this.getContentPane();
        getContentPane().setLayout(null);
        p1.setBounds(1, 0, 600, 100);
        p1.setBorder(BorderFactory.createLineBorder(Color.black));
        c.add(p1);
        p1.setLayout(null);
        p2.setBounds(1, 105, 600, 450);
        p2.setBorder(BorderFactory.createLineBorder(Color.black));
        c.add(p2);
        p2.setLayout(null);
        p1.add(L1);
        L1.setBounds(5, 5, 80, 40);
        p1.add(L11);
        L11.setBounds(205, 5, 80, 40);
        p1.add(L2);
        L2.setBounds(5, 50, 80, 40);
        p2.add(L3);
        L3.setBounds(5, 5, 100, 100);
        p2.add(L4);
        L4.setBounds(5, 110, 100, 100);
        p2.add(L5);
        L5.setBounds(5, 225, 100, 200);

        t1.setBounds(100, 5, 100, 40);
        t11.setBounds(300, 5, 100, 40);
        t2.setBounds(100, 50, 100, 40);
        t3.setBounds(100, 5, 400, 100);
        t4.setBounds(100, 110, 400, 100);
        t5.setBounds(100, 225, 400, 180);
        t1.setLineWrap(true);
        t11.setLineWrap(true);
        t2.setLineWrap(true);
        t3.setLineWrap(true);
        t4.setLineWrap(true);
        t5.setLineWrap(true);
        p1.add(t1);
        p1.add(t11);
        p1.add(t2);
        p2.add(t3);
        p2.add(t4);
        p2.add(t5);

        JButton b1 = new JButton("开始");
        b1.setBounds(450, 50, 100, 40);
        p1.add(b1);

        b1.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.show();
        this.setVisible(rootPaneCheckingEnabled);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("开始")) {
            String myName = "宋泽齐";
            String myNameCode = TMFactory.nameToUnicode(myName);
            int[] myNameTrans = TMFactory.nameToIntegers(myNameCode);
            String name = t1.getText();
            String nameCode = TMFactory.nameToUnicode(name);
            int[] nameTrans = TMFactory.nameToIntegers(nameCode);
            String nameBinCode = TMFactory.nameEncode(nameTrans);
            TM myTM = TMFactory.getTMFromInts(myNameTrans);
            String tMCode = TMFactory.tmEncode(myTM);
            String code = tMCode + "111" + nameBinCode;
            if (myTM.run(nameTrans)) {
                t2.setText("accept");
            } else
                t2.setText("reject");
            t11.setText(nameCode);
            t3.setText(tMCode);
            t4.setText(nameBinCode);
            t5.setText(code);

        }

    }

}
