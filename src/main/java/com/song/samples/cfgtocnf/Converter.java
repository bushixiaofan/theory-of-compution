package com.song.samples.cfgtocnf;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: songzeqi
 * @Date: 2019-06-17 4:49 PM
 */

public class Converter extends JFrame {

    private JPanel cards = new JPanel(new CardLayout());
    private JPanel p0 = new JPanel(); // 面板1
    private JPanel p1 = new JPanel(); // 面板2
    private JPanel p2 = new JPanel(); // 面板3

    private CFG cfg;

    public void showConvert() {
        this.setTitle("FaPrinter");
        this.setSize(600, 600);
        this.setLocation(0, 0);
        this.add(cards);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cards.add(p0, "card0");
        cards.add(p1, "card1");
        cards.add(p2, "card2");

        JLabel l01 = new JLabel("变元集");
        l01.setBounds(20, 20, 100, 40);

        JTextArea t01 = new JTextArea();
        t01.setBounds(120, 20, 200, 40);
        t01.setText("S,A,B");

        JLabel l02 = new JLabel("终结符表");
        l02.setBounds(20, 80, 100, 40);

        JTextArea t02 = new JTextArea();
        t02.setBounds(120, 80, 200, 40);
        t02.setText("a,b,#");

        JLabel l03 = new JLabel("产生式规则");
        l03.setBounds(20, 140, 100, 40);

        JTextArea t03 = new JTextArea();
        t03.setBounds(120, 140, 200, 40);
        t03.setText("S->A,S,A;S->a,B;A->B;A->S;B->b;B->#");

        JLabel l04 = new JLabel("起始变元");
        l04.setBounds(20, 200, 100, 40);

        JTextArea t04 = new JTextArea();
        t04.setBounds(120, 200, 200, 40);
        t04.setText("S");

        JButton b01 = new JButton("打印CFG");
        b01.addActionListener(e -> {
            String variableStr = t01.getText();
            String terminalSymbolStr = t02.getText();
            String productionRulesStr = t03.getText();
            String initialVariable = t04.getText();
            Set<CFG.ProductionRule> productionRules = Sets.newHashSet();
            List<String> productionRulesStr1 = Lists.newArrayList(productionRulesStr.split(";"));
            productionRulesStr1.stream().forEach(a->{
                List<String> productionRuleStr = Lists.newArrayList(a.split("->"));
                productionRules.add(new CFG.ProductionRule(productionRuleStr.get(0), Lists.newArrayList(productionRuleStr.get(1).split(","))));
            });
            Set<String> variableSet = Sets.newHashSet(variableStr.split(","));
            Set<String> terminalSymbolSet = Sets.newHashSet(terminalSymbolStr.split(","));
            cfg = new CFG(variableSet, terminalSymbolSet, initialVariable, productionRules);
            CardLayout cl = (CardLayout) (cards.getLayout());
            cl.show(cards, "card1");
        });
        b01.setBounds(20, 340, 100, 40);

        JButton b02 = new JButton("打印CNF");
        b02.addActionListener(e -> {
            String variableStr = t01.getText();
            String terminalSymbolStr = t02.getText();
            String productionRulesStr = t03.getText();
            String initialVariable = t04.getText();
            Set<CFG.ProductionRule> productionRules = Sets.newHashSet();
            List<String> productionRulesStr1 = Lists.newArrayList(productionRulesStr.split(";"));
            productionRulesStr1.stream().forEach(a->{
                List<String> productionRuleStr = Lists.newArrayList(a.split("->"));
                productionRules.add(new CFG.ProductionRule(productionRuleStr.get(0), Lists.newArrayList(productionRuleStr.get(1).split(","))));
            });
            Set<String> variableSet = Sets.newHashSet(variableStr.split(","));
            Set<String> terminalSymbolSet = Sets.newHashSet(terminalSymbolStr.split(","));
            cfg = new CFG(variableSet, terminalSymbolSet, initialVariable, productionRules);
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
        p0.add(b01);
        p0.add(b02);


        JButton b11 = new JButton("打印CFG");
        JTextArea t11 = new JTextArea();
        t11.setBounds(20, 140, 400, 400);

        b11.setBounds(20, 20, 100, 40);
        b11.addActionListener(e -> {
            t11.setText(cfg.toString());
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
        p1.add(t11);


        JButton b21 = new JButton("打印CNF");
        b21.setBounds(20, 20, 100, 40);
        JTextArea t21 = new JTextArea();
        t21.setBounds(20, 140, 400, 400);
        b21.addActionListener(e -> {
            t21.setText(Converter.convertCFGToCNF(cfg).toString());
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
        p2.add(t21);


        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, "card0"); // 调用show()方法显示面板0
        this.setVisible(true);
    }


    public static CNF convertCFGToCNF(CFG cfg) {
        // 只有一个空规则的情况: s -> #
        if (cfg.getProductionRuleSet().size() == 1) {
            for (CFG.ProductionRule productionRule : cfg.getProductionRuleSet()) {
                if (productionRule.getRightStrs().size() == 1 && productionRule.getRightStrs().get(0).equals("#")) {
                    return new CNF(cfg.getVariableSet(), cfg.getTerminalSymbolSet(), cfg.getInitialVariable(), cfg.getProductionRuleSet());
                }
            }
        }
        // 产生式规则集
        Set<CFG.ProductionRule> productionRuleSet = cfg.getProductionRuleSet();
        // 变元集
        Set<String> variableSet = cfg.getVariableSet();
        // 终结符号集
        Set<String> terminalSymbolSet = cfg.getTerminalSymbolSet();
        // step1 添加新的起始变元s0和规则 s0 -> s
        String initialVar = "S0";
        variableSet.add(initialVar);
        CFG.ProductionRule productionRuleNew = new CFG.ProductionRule(initialVar, Lists.newArrayList(cfg.getInitialVariable()));
        productionRuleSet.add(productionRuleNew);
        Set<CFG.ProductionRule> productionRulesNull = Sets.newHashSet();

        // step2 处理所有的空规则（A -> #）
        while (containsOneNull(productionRuleSet)) {
            for (CFG.ProductionRule productionRule : productionRuleSet) {
                if (productionRule.getRightStrs().size() == 1 && productionRule.getRightStrs().get(0).equals("#")) {
                    Set<CFG.ProductionRule> productionRules = Sets.newHashSet(productionRuleSet);
                    productionRules.remove(productionRule);
                    productionRulesNull.add(productionRule);
                    // 替换包含变元的项
                    for (CFG.ProductionRule productionRuleOri : productionRuleSet) {
                        int count = (int) productionRuleOri.getRightStrs().stream().filter(a -> a.equals(productionRule.getLeftVariable())).count();
                        if (count <= 0) {
                            continue;
                        }
                        long n = (long) Math.pow((float) 2, count);
                        // 处理 R->uAvAw，会生成三条：R->uvAw,R->uAvw,r->uvw,复杂度O(2^n),其中n=A变元的个数
                        for (int i = 1; i < n; i++) {
                            List<String> strsCopy = new ArrayList<>(productionRuleOri.getRightStrs());
                            int idx = 0;
                            for (int j = 0; j < productionRuleOri.getRightStrs().size(); j++) {
                                if (productionRuleOri.getRightStrs().get(j).equals(productionRule.getLeftVariable())) {
                                    if (((i >> idx) & 1) == 1) {
                                        // 先用特殊字符替换，之后去掉特殊字符
                                        strsCopy.set(j, "*");
                                    }
                                    idx++;
                                }
                            }
                            List<String> strsCopy2 = new ArrayList<>(strsCopy);
                            while (strsCopy2.contains("*")) {
                                strsCopy2.remove("*");
                            }
                            if (strsCopy2.size() > 0) {
                                productionRules.add(new CFG.ProductionRule(productionRuleOri.getLeftVariable(), strsCopy2));
                            } else {
                                if (!productionRulesNull.contains(new CFG.ProductionRule(productionRuleOri.getLeftVariable(), Lists.newArrayList("#")))) {
                                    productionRules.add(new CFG.ProductionRule(productionRuleOri.getLeftVariable(), Lists.newArrayList("#")));
                                }
                            }
                        }
                    }
                    productionRuleSet = productionRules;
                }
            }
        }

        // step3 处理所有的单一规则
        Set<CFG.ProductionRule> productionRulesOne = Sets.newHashSet();
        while (containsOne(productionRuleSet, cfg.getVariableSet())) {
            for (CFG.ProductionRule productionRule : productionRuleSet) {
                if (productionRule.getRightStrs().size() == 1 && variableSet.contains(productionRule.getRightStrs().get(0))) {
                    Set<CFG.ProductionRule> productionRules = Sets.newHashSet(productionRuleSet);
                    productionRules.remove(productionRule);
                    productionRulesOne.add(productionRule);
                    for (CFG.ProductionRule productionRuleOri : productionRuleSet) {
                        // 对所有的A->B, B->u,替换A->u,除非A->u为前面删除的单一规则
                        if (productionRuleOri.getLeftVariable().equals(productionRule.getRightStrs().get(0))) {
                            if (!productionRulesOne.contains(new CFG.ProductionRule(productionRule.getLeftVariable(), productionRuleOri.getRightStrs()))) {
                                productionRules.add(new CFG.ProductionRule(productionRule.getLeftVariable(), productionRuleOri.getRightStrs()));
                            }
                        }
                    }
                    productionRuleSet = productionRules;
                }
            }
        }

        // step4 把所有留下的规则转换成适当的形式
        for (CFG.ProductionRule productionRule : productionRuleSet) {
            Set<CFG.ProductionRule> productionRules = Sets.newHashSet(productionRuleSet);
            if (productionRule.getRightStrs().size() < 3) {
                continue;
            }
            // A -> u1u2u3u4...un 这种形式的转换
            productionRules.remove(productionRule);
            variableSet.add("A" + 1);
            productionRules.add(new CFG.ProductionRule(productionRule.getLeftVariable(), Lists.newArrayList(productionRule.getRightStrs().get(0), "A" + 1)));
            for (int j = 1; j < productionRule.getRightStrs().size(); j++) {
                variableSet.add("A" + (j + 1));
                productionRules.add(new CFG.ProductionRule("A" + j, Lists.newArrayList(productionRule.getRightStrs().get(j), "A" + (j + 1))));
            }
            productionRuleSet = productionRules;
        }

        return new CNF(variableSet, terminalSymbolSet, "S0", productionRuleSet);

    }

    private static boolean containsVar(Set<CFG.ProductionRule> productionRules, String var) {
        for (CFG.ProductionRule productionRule : productionRules) {
            if (productionRule.getRightStrs().contains(var)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsOneNull(Set<CFG.ProductionRule> productionRules) {
        for (CFG.ProductionRule productionRule : productionRules) {
            if (productionRule.getRightStrs().size() == 1 && productionRule.getRightStrs().get(0).equals("#")) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsOne(Set<CFG.ProductionRule> productionRules, Set<String> variableSet) {
        for (CFG.ProductionRule productionRule : productionRules) {
            if (productionRule.getRightStrs().size() == 1 && variableSet.contains(productionRule.getRightStrs().get(0))) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Converter converter = new Converter();
        converter.showConvert();
    }

}
