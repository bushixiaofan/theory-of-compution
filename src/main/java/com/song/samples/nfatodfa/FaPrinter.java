package com.song.samples.nfatodfa;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * @author: songzeqi
 * @Date: 2019-05-29 8:42 PM
 */

public class FaPrinter extends JFrame {
    private JPanel cards = new JPanel(new CardLayout());
    private JPanel p0 = new JPanel(); // 面板1
    private JPanel p1 = new JPanel(); // 面板2
    private JPanel p2 = new JPanel(); // 面板3

    private NFA nfa;

    public void faPrint() {
        this.setTitle("FaPrinter");
        this.setSize(600, 600);
        this.setLocation(0, 0);
        this.add(cards);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cards.add(p0, "card0");
        cards.add(p1, "card1");
        cards.add(p2, "card2");

        JLabel l01 = new JLabel("状态集");
        l01.setBounds(20, 20, 100, 40);

        JTextArea t01 = new JTextArea();
        t01.setBounds(120, 20, 200, 40);
        t01.setText("1,2,3");

        JLabel l02 = new JLabel("字母表");
        l02.setBounds(20, 80, 100, 40);

        JTextArea t02 = new JTextArea();
        t02.setBounds(120, 80, 200, 40);
        t02.setText("a,b,#");

        JLabel l03 = new JLabel("状态转移函数");
        l03.setBounds(20, 140, 100, 40);

        JTextArea t03 = new JTextArea();
        t03.setBounds(120, 140, 200, 40);
        t03.setText("1 b 2;1 # 3;2 a 2,3;2 b 3;3 a 1");

        JLabel l04 = new JLabel("起始状态");
        l04.setBounds(20, 200, 100, 40);

        JTextArea t04 = new JTextArea();
        t04.setBounds(120, 200, 200, 40);
        t04.setText("1");

        JLabel l05 = new JLabel("结束状态集");
        l05.setBounds(20, 280, 100, 40);

        JTextArea t05 = new JTextArea();
        t05.setBounds(120, 280, 200, 40);
        t05.setText("1");

        JButton b01 = new JButton("绘制NFA");
        b01.addActionListener(e -> {
            String stateTableStr = t01.getText();
            String symbolTableStr = t02.getText();
            String transFunsStr = t03.getText();
            String qStartStr = t04.getText();
            String qEndsStr = t05.getText();
            nfa = new NFA(stateTableStr, symbolTableStr, transFunsStr, qStartStr, qEndsStr);
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card1");
        });
        b01.setBounds(20, 340, 100, 40);

        JButton b02 = new JButton("绘制DFA");
        b02.addActionListener(e -> {
            String stateTableStr = t01.getText();
            String symbolTableStr = t02.getText();
            String transFunsStr = t03.getText();
            String qStartStr = t04.getText();
            String qEndsStr = t05.getText();
            nfa = new NFA(stateTableStr, symbolTableStr, transFunsStr, qStartStr, qEndsStr);
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card2");
        });
        b02.setBounds(120, 340, 100, 40);

        p0.setLayout(null);
        p0.add(l01);
        p0.add(t01);
        p0.add(l02);
        p0.add(t02);
        p0.add(l03);
        p0.add(t03);
        p0.add(l04);
        p0.add(t04);
        p0.add(l05);
        p0.add(t05);
        p0.add(b01);
        p0.add(b02);


        JButton b11 = new JButton("绘制NFA");

        b11.setBounds(20, 20, 100, 40);
        b11.addActionListener(e -> {
            Graph<String> nfaGraph = new Graph<>(nfa);
            Graphics g1 = p1.getGraphics();
            nfaGraph.drawGraph(g1, 600, 600);

        });
        JButton b12 = new JButton("返回");
        b12.setBounds(120, 20, 100, 40);
        b12.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card0"); // 调用show()方法显示面板0

        });

        p1.setLayout(null);
        p1.add(b11);
        p1.add(b12);

        JButton b21 = new JButton("绘制DFA");
        b21.setBounds(20, 20, 100, 40);
        b21.addActionListener(e -> {
            Graph<Set<String>> dfaGraph = new Graph<>(nfa.getDfa());
            Graphics g1 = p2.getGraphics();
            dfaGraph.drawGraph(g1, 600, 600);

        });
        JButton b22 = new JButton("返回");
        b22.setBounds(120, 20, 100, 40);
        b22.addActionListener(e -> {
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card0"); // 调用show()方法显示面板0

        });
        p2.setLayout(null);
        p2.add(b21);
        p2.add(b22);


        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "card0"); // 调用show()方法显示面板0
        this.setVisible(true);
    }

    public static void main(String[] args) {
        FaPrinter faPrinter = new FaPrinter();
        faPrinter.faPrint();
    }
}
