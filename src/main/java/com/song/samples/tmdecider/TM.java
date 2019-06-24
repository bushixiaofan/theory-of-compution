package com.song.samples.tmdecider;

import java.util.Arrays;
import java.util.List;

/**
 * 图灵机
 */
public class TM {
    /**
     * 状态集
     */
    private int[] stateTable;
    /**
     * 符号表
     */
    private int[] symbolTable;
    /**
     * 纸袋符号表
     */
    private int[] tapeTable;
    /**
     * 状态转移函数
     */
    private List<TransRule> transRules;
    /**
     * 初始状态
     */
    private int qStart;
    /**
     * 接受状态
     */
    private int qAccept;
    /**
     * 拒绝状态
     */
    private int qReject;
    /**
     * 当前状态
     */
    private Pattern pattern;

    public TM(int[] stateTable, int[] symbolTable, int[] tapeTable, List<TransRule> transTable, int qStart, int qAccept,
            int qReject) {
        super();
        this.stateTable = stateTable;
        this.symbolTable = symbolTable;
        this.tapeTable = tapeTable;
        this.transRules = transTable;
        this.qStart = qStart;
        this.qAccept = qAccept;
        this.qReject = qReject;
    }

    public boolean run(int[] tapes) {
        this.initPattern(tapes);
        int i = 0;
        // 16表示纸袋结束(#)
        while (tapes[i] != 16) {
			trans(this.pattern);
            i++;
        }
        return this.pattern.getCurState() == qAccept;
    }

    private void initPattern(int[] taps) {
		this.pattern = new Pattern();
        this.pattern.setTaps(taps);
        this.pattern.setCurPoint(0);
        this.pattern.setCurState(qStart);
    }

    public void trans(Pattern pattern) {
		for (TransRule transRule : transRules) {
			if (pattern.getCurState() == transRule.getQi()
					&& pattern.getTaps()[pattern.getCurPoint()] == transRule.getA()) {
				System.out.println(transRule.toString());
				pattern.setCurState(transRule.getQj());
				pattern.getTaps()[pattern.getCurPoint()] = transRule.getB();
				pattern.setCurPoint(pattern.getCurPoint() + 1);

			}
		}
    }

    public int[] getStateTable() {
        return stateTable;
    }

    public void setStateTable(int[] stateTable) {
        this.stateTable = stateTable;
    }

    public int[] getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(int[] symbolTable) {
        this.symbolTable = symbolTable;
    }

    public int[] getTapeTable() {
        return tapeTable;
    }

    public void setTapeTable(int[] tapeTable) {
        this.tapeTable = tapeTable;
    }

    public List<TransRule> getTransRules() {
        return transRules;
    }

    public void setTransRules(List<TransRule> transRules) {
        this.transRules = transRules;
    }

    public int getqStart() {
        return qStart;
    }

    public void setqStart(int qStart) {
        this.qStart = qStart;
    }

    public int getqAccept() {
        return qAccept;
    }

    public void setqAccept(int qAccept) {
        this.qAccept = qAccept;
    }

    public int getqReject() {
        return qReject;
    }

    public void setqReject(int qReject) {
        this.qReject = qReject;
    }

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public String toString() {
		return "TM{" +
				"stateTable=" + Arrays.toString(stateTable) +
				", symbolTable=" + Arrays.toString(symbolTable) +
				", tapeTable=" + Arrays.toString(tapeTable) +
				", transRules=" + transRules +
				", qStart=" + qStart +
				", qAccept=" + qAccept +
				", qReject=" + qReject +
				", pattern=" + pattern +
				'}';
	}

	/**
     * 图灵机格局
     */
    public class Pattern {
        /**
         * 当前纸袋
         */
        private int[] taps;

        /**
         * 当前指针未知
         */
        private int curPoint = 0;

        /**
         * 当前状态
         */
        private int curState;

		public Pattern() {
		}

		public Pattern(int[] taps, int curPoint, int curState) {
            this.taps = taps;
            this.curPoint = curPoint;
            this.curState = curState;
        }

        public int[] getTaps() {
            return taps;
        }

        public void setTaps(int[] taps) {
            this.taps = taps;
        }

        public int getCurPoint() {
            return curPoint;
        }

        public void setCurPoint(int curPoint) {
            this.curPoint = curPoint;
        }

        public int getCurState() {
            return curState;
        }

        public void setCurState(int curState) {
            this.curState = curState;
        }

		@Override
		public String toString() {
			return "Pattern{" +
					"taps=" + Arrays.toString(taps) +
					", curPoint=" + curPoint +
					", curState=" + curState +
					'}';
		}
	}
}
