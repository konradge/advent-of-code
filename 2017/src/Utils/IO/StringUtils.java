package Utils.IO;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String find(String str, String regex) {
        return find(str, regex, -1);
    }

    public static String find(String str, String regex, int groupNumber) {
        return findInternal(str, regex, groupNumber, false)[0];
    }

    private static String[] findInternal(String str, String regex, int groupNumber, boolean findAll) {
        ArrayList<String> res = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (groupNumber == -1) {
                res.add(m.group());
            } else {
                res.add(m.group(groupNumber));
            }

            if (!findAll) {
                return res.toArray(new String[0]);
            }
        }
        if (!findAll) return new String[]{null};
        return res.toArray(new String[0]);
    }

    public static String[] findAll(String str, String regex) {
        return findAll(str, regex, -1);
    }

    public static String[] findAll(String str, String regex, int groupNumber) {
        return findInternal(str, regex, groupNumber, true);
    }
}
