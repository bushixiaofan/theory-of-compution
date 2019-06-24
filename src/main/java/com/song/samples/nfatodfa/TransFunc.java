package com.song.samples.nfatodfa;

import java.util.Set;

/**
 * @author: songzeqi
 * @Date: 2019-05-30 7:27 PM
 */

public class TransFunc <T>{
    private T qi;
    private Set<T> qjs;
    private char a;

    public TransFunc(T qi, Set<T> qjs, char a) {
        this.qi = qi;
        this.qjs = qjs;
        this.a = a;
    }

    public T getQi() {
        return qi;
    }

    public void setQi(T qi) {
        this.qi = qi;
    }

    public Set<T> getQjs() {
        return qjs;
    }

    public void setQjs(Set<T> qjs) {
        this.qjs = qjs;
    }

    public char getA() {
        return a;
    }

    public void setA(char a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "TransFunc{" +
                "qi=" + qi +
                ", qjs=" + qjs +
                ", a=" + a +
                '}';
    }
}
