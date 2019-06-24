package com.song.samples.nfatodfa;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: songzeqi
 * @Date: 2019-05-30 2:39 PM
 */

public class NFA extends FAM<String> {

    /**
     * 对应的dfa
     */
    private DFA dfa;

    public NFA(Set<String> stateTable, Set<Character> symbolTable, List<TransFunc<String>> transFuncs, String qStart, Set<String> qEnds, DFA dfa) {
        super(stateTable, symbolTable, transFuncs, qStart, qEnds);
        this.dfa = dfa;
    }

    public NFA(String stateTableStr, String symbolTableStr, String transFunsStr, String qStartStr, String qEndsStr) {
        super();
        Set<String> stateTable = Sets.newHashSet(stateTableStr.split(","));
        Set<Character> symbolTable = Sets.newHashSet(Lists.newArrayList(symbolTableStr.split(",")).stream()
                .map(s -> s.charAt(0)).collect(Collectors.toList()));
        String qStart = qStartStr;
        Set<String> qEnds = Sets.newHashSet(qEndsStr.split(","));
        List<TransFunc<String>> transFuncs = Lists.newArrayList(transFunsStr.split(";")).stream()
                .map(s -> {
                    String[] elem = s.split(" ");

                    return new TransFunc<>(elem[0], Sets.newHashSet(elem[2].split(",")), elem[1].charAt(0));
                }).collect(Collectors.toList());
        this.stateTable = stateTable;
        this.symbolTable = symbolTable;
        this.transFuncs = transFuncs;
        this.qStart = qStart;
        this.qEnds = qEnds;
        this.convertToDFA();

    }

    public NFA(Set<String> stateTable, Set<Character> symbolTable, List<TransFunc<String>> transFuncs, String qStart,
            Set<String> qEnds) {
        super(stateTable, symbolTable, transFuncs, qStart, qEnds);
        this.convertToDFA();
    }

    public void convertToDFA() {

        // 1、初始化状态集合
        Set<Set<String>> stateTableOfDFA = Sets.newHashSet();
        // 2、构造结束状态集
        Set<Set<String>> qEndsOfDFA = Sets.newHashSet();
        List<String> stateTableList = Lists.newArrayList(stateTable);
        int size = stateTable.size();
        long n = (long) Math.pow((float) 2, size);
        // $表示空集
        stateTableOfDFA.add(Sets.newHashSet("$"));
        for (int i = 1; i < n; i++) {
            int temp = i;
            boolean isEndState = false;
            Set<String> state = Sets.newHashSet();
            for (int j = 0; j < size; j++) {
                if ((temp & 1) == 1) {
                    state.add(stateTableList.get(j));
                    if (qEnds.contains(stateTableList.get(j))) {
                        isEndState = true;
                    }
                }
                temp = temp >> 1;
            }
            stateTableOfDFA.add(state);
            // 结束状态集
            if (isEndState) {
                qEndsOfDFA.add(state);
            }
        }

        // 3、构造初始状态
        Set<String> qStartOfDFA = Sets.newHashSet();
        qStartOfDFA.add(qStart);
        addEmptyState(qStart, qStartOfDFA, transFuncs);

        // 4、构造状态转移函数
        List<TransFunc<Set<String>>> transFuncsOfDFA = Lists.newArrayList();
        // 遍历所有的状态
        for (Set<String> state : stateTableOfDFA) {
            // 遍历所有的符号
            for (Character character : symbolTable) {
                // 遍历所有的NFA状态转移函数
                Set<String> qjOfDFA = Sets.newHashSet();
                for (TransFunc<String> transFunc : transFuncs) {
                    if (state.contains(transFunc.getQi()) && character.equals(transFunc.getA())) {
                        qjOfDFA.addAll(transFunc.getQjs());
                        transFunc.getQjs().stream().forEach(a -> addEmptyState(a, qjOfDFA, transFuncs));
                    }
                }
                Set<Set<String>> qjOfDFASet = Sets.newHashSet();
                if (qjOfDFA.size() > 0) {
                    qjOfDFASet.add(qjOfDFA);
                    transFuncsOfDFA.add(new TransFunc<>(state, qjOfDFASet, character));
                }
            }
        }
        this.dfa = new DFA(stateTableOfDFA, symbolTable, transFuncsOfDFA, qStartOfDFA, qEndsOfDFA);
    }

    private void addEmptyState(String qStart, Set<String> qOfDFA, List<TransFunc<String>> transFuncs) {
        for (TransFunc<String> transFunc : transFuncs) {
            // #表示空字符
            if (transFunc.getQi().equals(qStart) && transFunc.getA() == '#') {
                qOfDFA.add(transFunc.getQi());
            }
        }
    }

    public DFA getDfa() {
        return dfa;
    }

    public void setDfa(DFA dfa) {
        this.dfa = dfa;
    }

    @Override
    public String toString() {
        return "NFA{" +
                "dfa=" + dfa +
                ", stateTable=" + stateTable +
                ", symbolTable=" + symbolTable +
                ", transFuncs=" + transFuncs +
                ", qStart=" + qStart +
                ", qEnds=" + qEnds +
                "} " + super.toString();
    }
}
