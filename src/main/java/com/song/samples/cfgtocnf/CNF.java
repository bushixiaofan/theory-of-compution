package com.song.samples.cfgtocnf;

import java.util.List;
import java.util.Set;

/**
 * 乔姆斯基文法
 *
 * @author: songzeqi
 * @Date: 2019-06-17 4:37 PM
 */

public class CNF extends CFG{
    public CNF() {
    }

    public CNF(Set<String> variableSet, Set<String> terminalSymbolSet, String initialVariable, Set<ProductionRule> productionRuleSet) {
        super(variableSet, terminalSymbolSet, initialVariable, productionRuleSet);
        cnfCheck(variableSet, terminalSymbolSet, initialVariable, productionRuleSet);
    }

    public void cnfCheck(Set<String> variableSet, Set<String> terminalSymbolSet, String initialVariable, Set<ProductionRule> productionRuleSet) {
        if (!variableSet.contains(initialVariable)) {
            throw new IllegalArgumentException("起始变元不存在，不符合CNF规则");
        }
        // CNF规则校验
        for (ProductionRule productionRule : productionRuleSet) {
            List<String> rightStrs = productionRule.getRightStrs();
            if (rightStrs.size() == 1) {
                if (!terminalSymbolSet.contains(rightStrs.get(0))) {
                    throw new IllegalArgumentException("右串不是终结符，不符合CNF规则");
                }
            } else if (rightStrs.size()> 2){
                throw new IllegalArgumentException("右串长度超过2，不符合CNF规则");
            }
        }
    }

    @Override
    public String toString() {
        return "CNF{" +
                "variableSet=" + variableSet +
                "\n terminalSymbolSet=" + terminalSymbolSet +
                "\n initialVariable='" + initialVariable + '\'' +
                "\n productionRuleSet=" + productionRuleSet +
                "} ";
    }
}
