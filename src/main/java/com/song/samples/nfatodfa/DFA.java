package com.song.samples.nfatodfa;

import java.util.List;
import java.util.Set;

/**
 * 确定的有穷状态自动机
 *
 * @author: songzeqi
 * @Date: 2019-05-30 2:39 PM
 */

public class DFA extends FAM<Set<String>> {
    public DFA(Set<Set<String>> stateTable, Set<Character> symbolTable, List<TransFunc<Set<String>>> transFuncs, Set<String> qStart,
               Set<Set<String>> qEnds) {
        super(stateTable, symbolTable, transFuncs, qStart, qEnds);
    }

    @Override
    public String toString() {
        return "DFA{" +
                "stateTable=" + stateTable +
                ", symbolTable=" + symbolTable +
                ", transFuncs=" + transFuncs +
                ", qStart=" + qStart +
                ", qEnds=" + qEnds +
                "} " + super.toString();
    }
}
