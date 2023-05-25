package com.shu.leettest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    public static double strSimilarity2Percent(String s, String t) {
        int l = Math.max(s.length(), t.length());
        double d = strSimilarity2Number(s, t);
        return (double) Math.round((1 - d / l) * 10000) / 10000;
    }

    public static double strSimilarity2Number(String s, String t) {
        int n = s.length(), m = t.length();
        double[][] d = new double[n + 1][m + 1];
        int i, j, cost;
        char s_i, t_j;
        if (n == 0) return m;
        if (m == 0) return n;
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);
                if (s_i == t_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }
        }
        return d[n][m];
    }

    public static double fuzzyMatch(String str, String key) {
        int index = -1, count = 0;
        String[] arr = key.split("");

        for (String s : arr) {
            //有一个关键字都没匹配到，则没有匹配到数据
            if (!str.contains(s)) {
                //count为在答案中未找到的知识点字符个数
                count++;
            } else {
                Pattern pattern = Pattern.compile(s);
                Matcher matcher = pattern.matcher(str);

                while (matcher.find()) {
                    if (matcher.start() > index) {
                        index = matcher.start();
                        break;
                    }
                }
            }
        }
        return (double) Math.round((1 - (float) count / key.length()) * 10000) / 10000;
    }


    public static double Minimum(double a, double b, double c) {
        return a < b ? (Math.min(a, c)) : Math.min(b, c);
    }

}
