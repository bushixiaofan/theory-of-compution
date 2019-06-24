package com.song.samples.nfatodfa;

import java.util.List;
import java.util.Set;

/**
 * 有穷状态自动机
 *
 * @author: songzeqi
 * @Date: 2019-05-29 8:43 PM
 */

public class FAM<T> {
    /**
     * 状态集
     */
    protected Set<T> stateTable;
    /**
     * 符号表
     */
    protected Set<Character> symbolTable;
    /**
     * 状态转移函数
     */
    protected List<TransFunc<T>> transFuncs;
    /**
     * 起始状态
     */
    protected T qStart;
    /**
     * 结束状态集
     */
    protected Set<T> qEnds;

    public FAM() {
    }

    public FAM(Set<T> stateTable, Set<Character> symbolTable, List<TransFunc<T>> transFuncs, T qStart, Set<T> qEnds) {
        this.stateTable = stateTable;
        this.symbolTable = symbolTable;
        this.transFuncs = transFuncs;
        this.qStart = qStart;
        this.qEnds = qEnds;
    }

    public Set<T> getStateTable() {
        return stateTable;
    }

    public void setStateTable(Set<T> stateTable) {
        this.stateTable = stateTable;
    }

    public Set<Character> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(Set<Character> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public List<TransFunc<T>> getTransFuncs() {
        return transFuncs;
    }

    public void setTransFuncs(List<TransFunc<T>> transFuncs) {
        this.transFuncs = transFuncs;
    }

    public T getqStart() {
        return qStart;
    }

    public void setqStart(T qStart) {
        this.qStart = qStart;
    }

    public Set<T> getqEnds() {
        return qEnds;
    }

    public void setqEnds(Set<T> qEnds) {
        this.qEnds = qEnds;
    }

    @Override
    public String toString() {
        return "FAM{" +
                "stateTable=" + stateTable +
                ", symbolTable=" + symbolTable +
                ", transFuncs=" + transFuncs +
                ", qStart=" + qStart +
                ", qEnds=" + qEnds +
                '}';
    }
}
