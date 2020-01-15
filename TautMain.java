package Tautology;

import java.io.*;

public class TautMain {
    public static void main(String[] args) {
        try {

            InputStream inStream
                    = TautMain.class.getClassLoader().getResourceAsStream("input.txt");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    inStream, "UTF-8"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println((line));
                Proposition pro = new Proposition(line);
                ProEvaluator evl = new ProEvaluator(pro);
                System.out.println(verify(pro, evl));
            }
        } catch (UnsupportedEncodingException f) {
            System.out.println("UnsupportedEncodingException exception : " + f);
        } catch (IOException e) {
            System.out.println("IOException exception : " + e);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    static boolean verify(Proposition proposition, ProEvaluator proEvaluator) {
        int count = (1 << proposition.getNumVars());
        for (int i = 0; i < count; i++) {
            if (!proEvaluator.evaluate(i)) {
                return false;
            }
        }
        return true;
    }
}
