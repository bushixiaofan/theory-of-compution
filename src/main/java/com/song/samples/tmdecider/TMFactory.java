package com.song.samples.tmdecider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: songzeqi
 * @Date: 2019-05-27 11:52 AM
 */

public class TMFactory {

    public static TM getTMFromInts(int[] ints) {
        // 状态数为输入int个数 + 接受状态 + 拒绝状态
        int[] stateTable = new int[ints.length + 2];
        for (int i = 0; i < ints.length + 2; i++) {
            stateTable[i] = i;
        }
        // 符号表长度为16（16进制数）
        int[] symbolTable = new int[16];
        for (int i = 0; i < 16; i++) {
            symbolTable[i] = i;
        }
        // 符号表长度为16（16进制数） + 1（结尾#）
        int[] tapeTable = new int[17];
        for (int i = 0; i <= 16; i++) {
            tapeTable[i] = i;
        }
        int qStart = stateTable[0];
        int qAccept = stateTable[ints.length];
        int qReject = stateTable[ints.length + 1];
        List<TransRule> transTable = new ArrayList<>();
        for (int i = 0; i < ints.length; i++) {
            transTable.add(new TransRule(stateTable[i], ints[i], stateTable[i + 1], ints[i], 1));
        }
        return new TM(stateTable, symbolTable, tapeTable, transTable, qStart, qAccept, qReject);
    }

    /**
     * 编码方式
     * 
     * @param myTM
     * @return
     */
    public static String tmEncode(TM myTM) {
        System.out.println(myTM);
        StringBuilder code = new StringBuilder();
        // 状态表编码
        for (int i = 0; i < myTM.getStateTable().length; i++) {
            code.append("0");
        }
        code.append("1");// 分隔符为1
        // 符号表编码
        for (int i = 0; i < myTM.getSymbolTable().length; i++) {
            code.append("0");
        }
        code.append("1");
        for (int i = 0; i < myTM.getTapeTable().length; i++) {
            code.append("0");
        }
        code.append("1");
        for (int i = 0; i < myTM.getqStart() + 1; i++) {
            code.append("0");
        }
        code.append("1");
        for (int i = 0; i < myTM.getqAccept() + 1; i++) {
            code.append("0");
        }
        code.append("1");
        for (int i = 0; i < myTM.getqReject() + 1; i++) {
            code.append("0");
        }
        code.append("1");
        // blank symbol 是纸袋字符的最后一个
        for (int i = 0; i < myTM.getTapeTable().length; i++) {
            code.append("0");
        }
        code.append("111");// 三个"1"切分
        // 状态转移函数编码
        for (int i = 0; i < myTM.getTransRules().size(); i++) {
            for (int j = 0; j < myTM.getTransRules().get(i).getQi() + 1; j++) {// 加1的目的是为了便于未来切分
                code.append("0");
            }
            code.append("1");
            for (int j = 0; j < myTM.getTransRules().get(i).getA() + 1; j++) {
                code.append("0");
            }
            code.append("1");
            for (int j = 0; j < myTM.getTransRules().get(i).getQj() + 1; j++) {
                code.append("0");
            }
            code.append("1");
            for (int j = 0; j < myTM.getTransRules().get(i).getB() + 1; j++) {
                code.append("0");
            }
            code.append("1");
            if (myTM.getTransRules().get(i).getRl() == 1) {
                code.append("0");
            }else {
                code.append("00");
            }
            if (i != myTM.getTransRules().size() - 1) {
                code.append("11");
            }
        }
        return code.toString();
    }

    public static int[] nameDecode(String nameEncode) {
        // 姓名解码
        String[] array3 = nameEncode.split("1");
        // 加1的目的是为了与输入一致，如5b8b6cfd9f50#
        int[] name0 = new int[array3.length + 1];
        for (int i = 0; i < array3.length; i++) {
            name0[i] = array3[i].length() - 1;
        }
        // 结尾符
        name0[array3.length] = 16;
        return name0;
    }

    public static TM tmDecode(String tmEncode) {

        String[] array = tmEncode.split("111");
        String[] array1 = array[0].split("1");
        // 状态集
        int[] stateTable = new int[array1[0].length()];
        for (int i = 0; i < array1[0].length(); i++) {
            stateTable[i] = i;
        }
        // 符号表
        int[] symbolTable = new int[array1[1].length()];
        for (int i = 0; i < array1[1].length(); i++) {
            symbolTable[i] = i;
        }
        // 纸袋符号表
        int[] tapeTable = new int[array1[2].length()];
        for (int i = 0; i < array1[2].length(); i++) {
            tapeTable[i] = i;
        }
        int qStart = array1[3].length() - 1;
        int qAccept = array1[4].length() - 1;
        int qReject = array1[5].length() - 1;

        // 根据TM编码构造TM的转移函数
        List<TransRule> transRules = new ArrayList<>();
        String[] array2 = array[1].split("11");
        for (String anArray2 : array2) {
            String[] array21 = anArray2.split("1");
            transRules.add(new TransRule(array21[0].length() - 1, array21[1].length() - 1, array21[2].length() - 1,
                    array21[3].length() - 1, array21[4].length()));
        }
        return new TM(stateTable, symbolTable, tapeTable, transRules, qStart, qAccept, qReject);

    }

    public static String nameEncode(int[] name) {
        StringBuilder nameCode = new StringBuilder();
        for (int i = 0; i < name.length - 2; i++) {
            for (int j = 0; j < name[i] + 1; j++) {
                nameCode.append("0");
            }
            nameCode.append("1");
        }
        for (int j = 0; j < name[name.length - 2] + 1; j++) {
            nameCode.append("0");
        }
        return nameCode.toString();
    }

    /**
     * 姓名转unicode
     *
     * @param cn
     * @return
     */
    public static String nameToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder returnStr = new StringBuilder();
        for (char aChar : chars) {
            returnStr.append(Integer.toString(aChar, 16));
        }
        return returnStr.toString() + "#";
    }

    /**
     * 姓名unicode转int
     *
     * @param name
     * @return
     */
    public static int[] nameToIntegers(String name) {
        int[] ints = new int[name.length()];
        for (int i = 0; i < name.length(); i++) {
            if ((int) name.charAt(i) <= 57 && (int) name.charAt(i) >= 48) {
                ints[i] = (int) name.charAt(i) - 48;
            } else if ((int) name.charAt(i) <= 102 && (int) name.charAt(i) >= 97) {
                ints[i] = (int) name.charAt(i) - 87;
            } else if ((int) name.charAt(i) == 35) {
                ints[i] = 16;
            } else
                return ints;
        }
        return ints;

    }
}
