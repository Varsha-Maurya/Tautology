package Tautology;

import java.util.HashMap;

public class Proposition {
    HashMap <Character, Integer> proMap;
    private int num = 0;
    private String eq;

    private String removeSp(String eq) {
        String ret = "";
        for(int i = 0; i < eq.length();i++)
        {
            if(eq.charAt(i) != ' '){
                ret = ret +eq.charAt(i);
            }
        }
        return ret;

    }

    Proposition(String boolExpr) {

        eq = removeSp(boolExpr);
        proMap = new HashMap<>();

        for (int i = 0; i < eq.length(); i++) {
            char ch = eq.charAt(i);
            if (ch == '(') {
            } else if (ch == ')') {
            } else if (ch == '&' || ch == '|' || ch == '!') {
            } else if (ch >= 'a' && ch <= 'z') {

                if (proMap.get(ch) == null) {
                    num++;
                    if (num > 10) {
                        throw new IllegalArgumentException(" variables > 10");
                    }
                    proMap.put(ch, num-1);
                }
            } else {
                throw new IllegalArgumentException("Unrecognized chars in eq");
            }

        }
    }

    @Override
    public String toString() {
        return eq;
    }

    public int getNumVars() {
        return num;
    }

    public int getVarPos(char varCh) {
        return proMap.get(varCh);
    }

}
