package utils;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
    public static int getShares(String shares, int roll){
        String[] tier = shares.split(",");
        for(String t: tier){
            String[] tSplit = t.split("=");
            String desc = tSplit[0];
            String value = tSplit[1];

            if(desc.matches("\\d+-\\d+")){
                if(rollFitsRange(desc,roll))
                    return Integer.parseInt(value);
            }else if(desc.matches("\\d+\\+")){
                if(rollFistFloor(desc,roll))
                    return Integer.parseInt(value);
            }else if(desc.matches("\\d+")){
                if(rollFitsDigit(desc,roll))
                    return Integer.parseInt(value);
            }
        }

        return 1;
    }
    private static boolean rollFitsDigit(String desc,int roll){
        return roll == Integer.parseInt(desc);
    }
    private static boolean rollFistFloor(String desc, int roll){
        Matcher m = Pattern.compile("(\\d+)(\\+)").matcher(desc);
        if(m.find()){
            MatchResult res = m.toMatchResult();
            int base = Integer.parseInt(res.group(1));
            String mod = res.group(2);

            return roll == base ||
                    ((roll < base && mod.equals("-"))||
                     (roll > base && mod.equals("+")));
        }
        return false;
    }
    public static boolean rollFitsDesc(String desc, int roll){
        if(desc.matches("-1"))return true;

        if(desc.matches("\\d+-\\d+")){
            if(rollFitsRange(desc,roll))
                return true;
        }else if(desc.matches("\\d+\\+")){
            if(rollFistFloor(desc,roll))
                return true;
        }else if(desc.matches("\\d+")){
            if(rollFitsDigit(desc,roll))
                return true;
        }
        return false;
    }
    private static boolean rollFitsRange(String desc, int roll){
        String[] range = desc.split("-");
        int min = Integer.parseInt(range[0]);
        int max = Integer.parseInt(range[1]);
        return (roll >= min && roll <= max);
    }

    public static int getMaxTakeaway(String shares){
        String[] tiers = shares.split(",");
        String max = tiers[tiers.length-1];
        int val = Integer.parseInt(max.split("=")[1]);
        return val;
    }
    public static String righPadding(String str, int padding, char pad){

        for(int i = str.length(); i < padding;i++){
            str += pad;
        }
        return str;
    }
}
