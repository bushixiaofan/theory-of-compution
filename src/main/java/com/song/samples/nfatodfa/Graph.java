package com.song.samples.nfatodfa;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: songzeqi
 * @Date: 2019-05-30 8:33 PM
 */

public class Graph<T> {
    /**
     * 自动机
     */
    private FAM<T> fam;
    /**
     * 自动机对应的邻接矩阵
     */
    private String[][] matrix;

    /**
     * 起始状态序号
     */
    private int sStartNo;

    /**
     * 结束状态序号列表
     */
    List<Integer> sEndList = Lists.newArrayList();

    public void convertMatrix() {
        Map<T, Integer> stateNoMap = Maps.newHashMap();
        List<T> stateList = Lists.newArrayList(fam.getStateTable());
        int n = fam.stateTable.size();
        matrix = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = "";
            }
        }
        for (int i = 0; i < n; i++) {
            stateNoMap.put(stateList.get(i), i);
        }
        // 构造邻接矩阵
        fam.transFuncs.forEach(a -> a.getQjs().forEach(b -> {
            matrix[stateNoMap.get(a.getQi())][stateNoMap.get(b)] = matrix[stateNoMap.get(a.getQi())][stateNoMap.get(b)]
                    + String.valueOf(a.getA());
        }));

        stateNoMap.forEach((key, value) -> {
            // 构造初始状态序号
            if (key.equals(fam.getqStart())) {
                sStartNo = value;
            }
            // 构造结束状态序号列表
            if (fam.getqEnds().contains(key)) {
                sEndList.add(value);
            }
        });
    }

    public void drawGraph(Graphics g1, int hight, int length) {
        int num = matrix.length;
        int step = length / (num + 2);
        int y = hight / 2;
        int radius = step / 6;
        for (int i = 0; i < num; i++) {
            if (sStartNo == i) {
                g1.drawLine((i + 1) * step - 4 * radius, y, (i + 1) * step - radius, y);
                g1.drawLine((i + 1) * step - radius - 5, y + 5, (i + 1) * step - radius, y);
                g1.drawLine((i + 1) * step - radius - 5, y - 5, (i + 1) * step - radius, y);
            }
            if (sEndList.contains(i)) {
                g1.drawOval((i + 1) * step - radius - 5, y - radius - 5, (radius + 5) * 2, (radius + 5) * 2);
            }
            g1.drawOval((i + 1) * step - radius, y - radius, radius * 2, radius * 2);
            g1.drawString(i+1 + "", (i + 1) * step, y);
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (!matrix[i][j].isEmpty() && i < j) {
                    g1.drawArc((i + 1) * step, y - step * (j - i) / 4 - radius, step * (j - i), step * (j - i) / 2, 0,
                            180);
                    g1.drawLine((j + 1) * step - 5, y - radius - 5, (j + 1) * step, y - radius);
                    g1.drawLine((j + 1) * step + 5, y - radius - 5, (j + 1) * step, y - radius);
                    g1.drawString(matrix[i][j], (i + j + 2) / 2 * step, y - step * (j - i) / 4 - radius);
                }
                if (!matrix[i][j].isEmpty() && i > j) {
                    g1.drawArc((j + 1) * step, y - step * (i - j) / 4 + radius, step * (i - j), step * (i - j) / 2, 0,
                            -180);
                    g1.drawLine((j + 1) * step - 5, y + radius + 5, (j + 1) * step, y + radius);
                    g1.drawLine((j + 1) * step + 5, y + radius + 5, (j + 1) * step, y + radius);
                    g1.drawString(matrix[i][j], (i + j + 2) / 2 * step, y - step * (j - i) / 4 + radius);

                }
                if (!matrix[i][j].isEmpty() && i == j) {
                    g1.drawOval((i + 1) * step - radius, y - 3 * radius, radius * 2, radius * 2);
                    g1.drawLine((j + 1) * step + 5, y - radius + 5, (j + 1) * step, y - radius);
                    g1.drawLine((j + 1) * step + 5, y - radius - 5, (j + 1) * step, y - radius);
                    g1.drawString(matrix[i][j], (i + 1) * step, y - 3 * radius);
                }
            }
        }

    }

    public Graph(FAM<T> fam) {
        this.fam = fam;
        this.convertMatrix();
    }

    public FAM<T> getFam() {
        return fam;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public void setFam(FAM<T> fam) {
        this.fam = fam;
    }

    public int getsStartNo() {
        return sStartNo;
    }

    public void setsStartNo(int sStartNo) {
        this.sStartNo = sStartNo;
    }

    public List<Integer> getsEndList() {
        return sEndList;
    }

    public void setsEndList(List<Integer> sEndList) {
        this.sEndList = sEndList;
    }

    @Override
    public String toString() {
        return "Graph{" + "fam=" + fam + ", matrix=" + Arrays.toString(matrix) + ", sStartNo=" + sStartNo
                + ", sEndList=" + sEndList + '}';
    }

    public static void main(String[] args) {
        String stateTableStr = "1,2,3,4,5,6";
        String symbolTableStr = "a,b,#";
        String transFunsStr = "1 # 1,3;2 a 3;3 b 6;4 b 5;5 a 2,6;6 # 1";
        String qStartStr = "1";
        String qEndsStr = "6";
        NFA nfa = new NFA(stateTableStr, symbolTableStr, transFunsStr, qStartStr, qEndsStr);
        System.out.println(nfa.getStateTable());
        System.out.println(nfa.getSymbolTable());
        System.out.println(nfa.getTransFuncs());
        System.out.println(nfa.getqStart());
        System.out.println(nfa.getqEnds());

        DFA dfa = nfa.getDfa();
        System.out.println(dfa.getStateTable());
        System.out.println(dfa.getSymbolTable());
        System.out.println(dfa.getTransFuncs());
        System.out.println(dfa.getqStart());
        System.out.println(dfa.getqEnds());

        Graph<String> nfaGraph = new Graph<>(nfa);
        System.out.println(nfaGraph);
        Graph<Set<String>> dfaGraph = new Graph<>(dfa);
        System.out.println(dfaGraph);
    }
}
