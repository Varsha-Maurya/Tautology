package Tautology;

import java.util.Stack;

public class ProEvaluator {

    private String val;
    private final Proposition proEvaluated;

    ProEvaluator(Proposition pro) {
        proEvaluated = pro;
    }

    private int checkOp(char chk, int rm, int otp) {
        boolean check, res, outpt;
        check = (1 == otp);
        res = (1 == rm);
        switch (chk) {
            case '&':
                outpt = check & res;
                return (outpt) ? 1 : 0;
            case '|':
                outpt = check | res;
                return (outpt) ? 1 : 0;

        }
        return -1;
    }

    private int checkOp(char chk, int otp) {
        boolean check = (1 == otp);
        boolean output;
        if (chk == '!') {
            output = !check;
            return (output) ? 1 : 0;
        }
        return -1;
    }


    private boolean prior(char op1, char op2) {

        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if (((op2 == '!') && (op1 == '|')) || (op1 == '&')) {
            return true;
        } else if (((op2 == '|') || (op2 == '&')) && (op1 == '!')) {
            return false;
        }
        return true;
    }

    private boolean letter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private boolean polEval() {

        char[] tokens = proEvaluated.toString().toCharArray();
        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();
        for (char token : tokens) {

            if (letter(token)) {
                int bitValue = getVarValue(token);
                values.push(bitValue);
            } else if (token == '(') {
                ops.push(token);
            } else if (token == ')') {
                while (ops.peek() != '(') {
                    evaluateToken(values, ops);
                }
                ops.pop();
            } else if (token == '&' || token == '|' || token == '!') {
                while (!ops.empty() && prior(token, ops.peek())) {
                    evaluateToken(values, ops);
                }
                ops.push(token);
            }
        }
        while (!ops.empty()) {
            evaluateToken(values, ops);
        }

        return (values.pop() == 1);
    }

    private void evaluateToken(Stack<Integer> values, Stack<Character> ops) {
        char opCh;
        opCh = ops.pop();
        if (opCh == '&' || opCh == '|') {
            values.push(checkOp(opCh, values.pop(), values.pop()));
        } else if (opCh == '!') {
            values.push(checkOp(opCh, values.pop()));
        }
    }

    private void setVariables(int value) {
        val = Integer.toBinaryString(value);
        while (val.length() < 10) {
            val = "0" + val;
        }
    }

    public boolean evaluate(int value) {
        setVariables(value);

        return polEval();
    }

    private int getVarValue(char varCh) {

        int pos = proEvaluated.getVarPos(varCh);
        return Character.getNumericValue(val.charAt(9 - pos));
    }
}