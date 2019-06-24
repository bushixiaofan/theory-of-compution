package com.song.samples.tmdecider;

/**
 * 状态转移函数
 *
 * (qi, a)->(qj, b, rl)
 *
 */
public class TransRule {

    private int qi;
    private int qj;
    private int rl;
    private int a;
    private int b;

    public TransRule(int qi, int a, int qj, int b, int rl) {
        this.a = a;
        this.b = b;
        this.qi = qi;
        this.qj = qj;
        this.rl = rl;
    }

    public int getQi() {
        return qi;
    }

    public void setQi(int qi) {
        this.qi = qi;
    }

    public int getQj() {
        return qj;
    }

    public void setQj(int qj) {
        this.qj = qj;
    }

    public int getRl() {
        return rl;
    }

    public void setRl(int rL) {
        rl = rL;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

	@Override
	public String toString() {
		return "TransRule{" +
				"qi=" + qi +
				", qj=" + qj +
				", rl=" + rl +
				", a=" + a +
				", b=" + b +
				'}';
	}
}
