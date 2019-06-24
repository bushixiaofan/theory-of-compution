package com.song.samples.cfgtocnf;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 上下文无关文法
 *
 * @author: songzeqi
 * @Date: 2019-06-17 4:13 PM
 */

public class CFG {

    /**
     * 变元集
     */
    protected Set<String> variableSet;
    /**
     * 终结符集
     */
    protected Set<String> terminalSymbolSet;
    /**
     * 起始变元
     */
    protected String initialVariable;
    /**
     * 产生式（替换规则集）
     */
    protected Set<ProductionRule> productionRuleSet;

    public CFG() {
    }

    public CFG(Set<String> variableSet, Set<String> terminalSymbolSet, String initialVariable, Set<ProductionRule> productionRuleSet) {
        this.variableSet = variableSet;
        this.terminalSymbolSet = terminalSymbolSet;
        this.initialVariable = initialVariable;
        this.productionRuleSet = productionRuleSet;
    }

    public static class ProductionRule {
        /**
         * 左变元
         */
        private String leftVariable;
        /**
         * 右导出串
         */
        private List<String> rightStrs;

        public ProductionRule() {
        }

        public ProductionRule(String leftVariable, List<String> rightStrs) {
            this.leftVariable = leftVariable;
            this.rightStrs = rightStrs;
        }

        public String getLeftVariable() {
            return leftVariable;
        }

        public void setLeftVariable(String leftVariable) {
            this.leftVariable = leftVariable;
        }

        public List<String> getRightStrs() {
            return rightStrs;
        }

        public void setRightStrs(List<String> rightStrs) {
            this.rightStrs = rightStrs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductionRule that = (ProductionRule) o;
            return Objects.equals(leftVariable, that.leftVariable) &&
                    Objects.equals(rightStrs, that.rightStrs);
        }

        @Override
        public int hashCode() {

            return Objects.hash(leftVariable, rightStrs);
        }

        @Override
        public String toString() {
            return "ProductionRule{" +
                    "leftVariable='" + leftVariable + '\'' +
                    ", rightStrs=" + rightStrs +
                    '}';
        }
    }

    public Set<String> getVariableSet() {
        return variableSet;
    }

    public void setVariableSet(Set<String> variableSet) {
        this.variableSet = variableSet;
    }

    public Set<String> getTerminalSymbolSet() {
        return terminalSymbolSet;
    }

    public void setTerminalSymbolSet(Set<String> terminalSymbolSet) {
        this.terminalSymbolSet = terminalSymbolSet;
    }

    public String getInitialVariable() {
        return initialVariable;
    }

    public void setInitialVariable(String initialVariable) {
        this.initialVariable = initialVariable;
    }

    public Set<ProductionRule> getProductionRuleSet() {
        return productionRuleSet;
    }

    public void setProductionRuleSet(Set<ProductionRule> productionRuleSet) {
        this.productionRuleSet = productionRuleSet;
    }

    @Override
    public String toString() {
        return "CFG{" +
                "variableSet=" + variableSet +
                "\n terminalSymbolSet=" + terminalSymbolSet +
                "\n initialVariable='" + initialVariable + '\'' +
                "\n productionRuleSet=" + productionRuleSet +
                '}';
    }
}
