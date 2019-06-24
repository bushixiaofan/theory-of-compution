package com.song.samples.cfgtocnf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class CFG1 {
    private HashMap<Object, Object> convertFunction;
    private ArrayList<String> leftState;
    private String tempLeftState;
    private ArrayList<String> rightStateTempStore;

    public CFG1() {
        convertFunction = new HashMap<Object, Object>();
        leftState = new ArrayList<String>();
    }

    public HashMap<Object, Object> getConvertFunction() {
        return convertFunction;
    }

    public void setConvertFunction(HashMap<Object, Object> convertFunction) {
        this.convertFunction = convertFunction;
    }

    public void input(HashMap<Object, Object> convertFunction,ArrayList<String> leftState) {
//		ArrayList<String> rightState = new ArrayList<String>();
//		rightState.add("ASA");
//		rightState.add("aB");
//		leftState.add("S");
//		convertFunction.put(leftState.get(0), rightState);
//
//		rightState = new ArrayList<String>();
//		rightState.add("B");
//		rightState.add("S");
//
//		leftState.add("A");
//		convertFunction.put(leftState.get(1), rightState);
//
//		rightState = new ArrayList<String>();
//		rightState.add("b");
//		rightState.add("*");
//
//		leftState.add("B");
//		convertFunction.put(leftState.get(2), rightState);
        this.convertFunction = convertFunction;
        this.leftState = leftState;

    }

    public void output() {

        Iterator<String> leftStateIt = leftState.iterator();
        while (leftStateIt.hasNext()) {
            tempLeftState = leftStateIt.next().toString();

            System.out.println(tempLeftState + " --> "
                    + convertFunction.get(tempLeftState));
        }
    }

    ArrayList<String> calRightStateRecursion(String tempRightStateCell, char displaceChar) {
        ArrayList<String> tempRightStateCellForRecursion = new ArrayList<String>();
        for (int i = 0; i < tempRightStateCell.length(); i ++) {
            if (tempRightStateCell.charAt(i) == displaceChar) {
//				System.out.println("第" + i + "个");
                String tempSubRightStateCell;
                if (i == 0) {
                    tempSubRightStateCell = tempRightStateCell.substring(i + 1);
                } else if (i == tempRightStateCell.length() - 1) {
                    tempSubRightStateCell = tempRightStateCell.substring(0, i);
                } else {
                    tempSubRightStateCell = tempRightStateCell.substring(0, i) + tempRightStateCell.substring(i);
                }
//				System.out.println(tempSubRightStateCell);
                tempRightStateCellForRecursion.add(tempSubRightStateCell);
//				System.out.println(tempRightStateCellForRecursion);
                tempRightStateCellForRecursion.addAll(calRightStateRecursion(tempSubRightStateCell, displaceChar));
            }

        }
        return tempRightStateCellForRecursion;
    }

    public void toCNF() {
        ArrayList<String> rightState = new ArrayList<String>();
        ArrayList<String> leftStateHasNull = new ArrayList<String>();
        HashMap<Object, Object> leftStateHasOnlyOneRightState = new HashMap<Object, Object>();
        // step 1
        // 引入新变元S0 -> S
        leftState.add("S0");
        rightState.add("S");
        convertFunction.put("S0", rightState);

        // step 2
        // 删除所有X -> *的操作
        // step 2.1
        // 遍历可以推导出*的字符,记录在leftStateHasNull变量中
        Iterator<String> leftStateIt = leftState.iterator();
        while (leftStateIt.hasNext()) {
            tempLeftState = leftStateIt.next().toString();
            ArrayList<String> tempRightState = new ArrayList<String>();
            tempRightState = (ArrayList) convertFunction.get(tempLeftState);
            if (tempRightState.contains("*")) {
                leftStateHasNull.add(tempLeftState);
            }
        }
        // System.out.println(leftStateHasNull);
        // step 2.2
        // 将右边状态中含有leftStateHasNull成员的字母，替换为空，构成新状态，加入到原有的rightState中

        while (! leftStateHasNull.isEmpty()) {
            ArrayList<String> tempLeftStateHasNull = new ArrayList<String>();

            leftStateIt = leftState.iterator();
            while (leftStateIt.hasNext()) {
                tempLeftState = leftStateIt.next().toString();
                ArrayList<String> tempRightState = new ArrayList<String>();
                tempRightState = (ArrayList) convertFunction.get(tempLeftState);

                // System.out.println(tempRightState);
                Iterator<String> tempRightStateIt = tempRightState.iterator();
                while (tempRightStateIt.hasNext()) {
                    String tempRightStateCell;
                    ArrayList<String> rightStateRecursion = new ArrayList<String>();
                    tempRightStateCell = (String) tempRightStateIt.next();
                    for (int i = 0; i < leftStateHasNull.size(); i++) {
                        if (tempRightStateCell
                                .contains(leftStateHasNull.get(i))) {
                            // 替换可以推导为*的成员
                            String tempAddRightStateCell;
                            if (tempRightStateCell.length() == 1) {
                                tempAddRightStateCell = tempRightStateCell
                                        .replace(leftStateHasNull.get(i), "*");
                                tempLeftStateHasNull.add(tempLeftState);
                                // System.out.println(leftStateHasNull);
                            } else {
                                if (tempRightStateCell.length() == 2) {
                                    tempAddRightStateCell = tempRightStateCell
                                            .replace(leftStateHasNull.get(i), "");
                                } else {
//									System.out.println(tempRightStateCell);
//									System.out.println(leftStateHasNull.get(i).charAt(0));
//									System.out.println(calRightStateRecursion(tempRightStateCell, leftStateHasNull.get(i).charAt(0)));
                                    rightStateRecursion = calRightStateRecursion(tempRightStateCell, leftStateHasNull.get(i).charAt(0));
                                    Collections.sort(rightStateRecursion);
                                    for (int j = rightStateRecursion.size() - 1; j > 0; j --) {
                                        if (rightStateRecursion.get(j).equals(rightStateRecursion.get(j - 1))) {
                                            rightStateRecursion.remove(j);
                                        }
                                    }
                                    tempAddRightStateCell = null;
                                }

                            }

                            ArrayList<String> tempAddedRightState = new ArrayList<String>();
                            tempAddedRightState = (ArrayList) tempRightState
                                    .clone();
                            if (tempAddRightStateCell != null) {
                                tempAddedRightState.add(tempAddRightStateCell);
                            }
                            tempAddedRightState.addAll(rightStateRecursion);
                            convertFunction.put(tempLeftState,
                                    tempAddedRightState);
                        }
                    }
                }
            }
            leftStateHasNull.clear();
            leftStateHasNull = (ArrayList)tempLeftStateHasNull.clone();
        }
        // step 2.3
        // 将右边状态中的*去掉,同时消掉左边为S的右边S
        // 合并操作为了使算法更为方便，提高效率
        rightStateTempStore = new ArrayList<String>();
        leftStateIt = leftState.iterator();
        while (leftStateIt.hasNext()) {
            tempLeftState = leftStateIt.next();
            ArrayList<String> tempRightState = new ArrayList<String>();
            tempRightState = (ArrayList)convertFunction.get(tempLeftState);
            tempRightState.remove("*");
            if (tempLeftState.equals("S") && tempRightState.contains("S")) {
                tempRightState.remove("S");
                rightStateTempStore = tempRightState;
            }
        }

        // step 3.1
        // 消去右边的S,记录单一规则
        leftStateIt = leftState.iterator();
        while (leftStateIt.hasNext()) {
            tempLeftState = leftStateIt.next();
            ArrayList<String> tempRightState = new ArrayList<String>();
            tempRightState = (ArrayList)convertFunction.get(tempLeftState);
            if (tempRightState.contains("S") && ! tempLeftState.equals("S")) {
                tempRightState.remove("S");
                tempRightState.addAll(rightStateTempStore);
            }
            if (tempRightState.size() == 1) {
                leftStateHasOnlyOneRightState.put(tempLeftState, tempRightState.get(0));
            }
        }

        // step 3.2
        // 消去单一规则
        leftStateIt = leftState.iterator();
        while (leftStateIt.hasNext()) {
            tempLeftState = leftStateIt.next();
            ArrayList<String> tempRightState = new ArrayList<String>();
            tempRightState = (ArrayList)convertFunction.get(tempLeftState);
            for (int i = 0; i < tempRightState.size(); i ++) {
                //判断在只能推导出一个终结符的集合中，是否存在tempRightState的某个推导
                //其实本意是为了找到右边推导中包含只能推导出一个终结符的集合元素
                if (leftStateHasOnlyOneRightState.containsKey(tempRightState.get(i))) {
                    //设置新的推导值
                    tempRightState.set(i, leftStateHasOnlyOneRightState.get(tempRightState.get(i)).toString());
                }
            }
        }

        // step 4
        // 添加新变元和规则，把留下的所有规则转换成合适的形式
        // step 4.1
        // 将右边的多元组变为两元组
        HashMap<Object, Object> stateWillAdd = new HashMap<Object, Object>();
        char replaceAlpha = 'M', generateOnlyOneAlpha = 'U';
        leftStateIt = leftState.iterator();
        while (leftStateIt.hasNext()) {
            tempLeftState = leftStateIt.next();
            ArrayList<String> tempRightState = new ArrayList<String>();
            tempRightState = (ArrayList)convertFunction.get(tempLeftState);
            for (int i = 0; i < tempRightState.size(); i ++) {
                String tempRightStateCell = tempRightState.get(i);
                if (tempRightStateCell.length() > 2) {
                    if (! stateWillAdd.containsKey(tempRightStateCell.substring(1))) {
                        stateWillAdd.put(tempRightStateCell.substring(1), replaceAlpha);
                        replaceAlpha ++;
                    }
                    if (stateWillAdd.containsKey(tempRightStateCell.substring(1))) {
                        tempRightStateCell = tempRightStateCell.replace(tempRightStateCell.substring(1), stateWillAdd.get(tempRightStateCell.substring(1)).toString());
                        tempRightState.set(i, tempRightStateCell);
                    }
                }
                if (tempRightStateCell.length() == 1) {
                    if (! leftStateHasOnlyOneRightState.containsValue(tempRightStateCell) && ! stateWillAdd.containsKey(tempRightStateCell)) {
                        stateWillAdd.put(tempRightStateCell, generateOnlyOneAlpha);
                        generateOnlyOneAlpha ++;
                    }
                }
            }
        }
        //循环结束，继续求添加入stateWillAdd里的状态变化
        Iterator stateWillAddKeyIt = stateWillAdd.keySet().iterator();
        while (stateWillAddKeyIt.hasNext()) {
            String tempStateWillAddKey = stateWillAddKeyIt.next().toString();
            if (tempStateWillAddKey.length() > 2) {
                stateWillAdd.put(tempStateWillAddKey.substring(1), replaceAlpha);
                replaceAlpha ++;
            }
        }

        //最终加入到convertFunction中
        stateWillAddKeyIt = stateWillAdd.keySet().iterator();
        while (stateWillAddKeyIt.hasNext()) {
            ArrayList<String> tempRightState = new ArrayList<String>();
            String temp = stateWillAddKeyIt.next().toString();
            tempRightState.add(temp);
            leftState.add(stateWillAdd.get(tempRightState.get(0)).toString());
//			System.out.println(stateWillAdd.get(tempRightState.get(0)) + "  " + tempRightState);
            convertFunction.put(stateWillAdd.get(tempRightState.get(0)).toString(), tempRightState);
        }

    }
}
