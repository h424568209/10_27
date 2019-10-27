import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeeCode {
    /**
     * 求两个字符串对应的字符串二进制的和，将两个字符串都从后向前遍历，进行满2进一的运算
     * @param a 字符串
     * @param b 字符串
     * @return 两个字符串对应的二进制的和
     */
    public static String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder();
        int ca = 0;
        for(int i=a.length()-1, j=b.length()-1;i>=0||j>=0;i--,j--){
            int sum = ca;
            sum += i>=0? a.charAt(i)-'0':0;
            sum += j>=0? b.charAt(j)-'0':0;
            res.append(sum%2);
            ca = sum/2;
        }
        res.append(ca==1?ca:"");
        return res.reverse().toString();
    }
    /**
     * 使用 String中split方法进行切割字符串
     * @param s 字符串
     * @return 消除空格后最后一个字符串的长度
     */
    public int lengthOfLastWord(String s) {
        if(s==null||s.length()==0){
            return  0 ;
        }
        String[]res = s.split(" ");
        if(res.length==0){
            return 0;
        }
        return res[res.length-1].length();
    }
    /**
     * 在序列保持有效的时候添加"(" 和")" 可以通过跟踪左括号和右括号的个数来做到这一点
     * 如果最后剩一个位置 可以放左括号；如果 不超过左括号的个数 可以放右括号
     * @param n 括号的个数
     * @return 有效括号的列表
     */
    private List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    private void backtrack(List<String> ans, String cur, int open, int close, int max){
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }

        if (open < max)
            backtrack(ans, cur+"(", open+1, close, max);
        if (close < open)
            backtrack(ans, cur+")", open, close+1, max);
    }


    Map<String, String> phone = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};
    List<String> output = new ArrayList<String>();

    /**
     * 电话号码的字母组合
     *
     * 将2-9的字符串和对应的字符串放在phone的哈希表中
     * 使用回溯法，列举所有有可能的情况来寻找所有的解法
     * 相当于一个树形结构，列举了所有的可能性再进行寻找
     * @param digits 给定的由数字组成的字符串
     * @return 数字对应的字符的全部组合
     */
    private List<String> letterCombinations(String digits) {
        if(digits.length()!=0){
            backtruck("",digits);
        }
        return output;
    }

    private  void backtruck(String combination, String next_digits) {
        if (next_digits.length() == 0) {
            output.add(combination);
        } else {
            //得到第一个数字字符
            String digit = next_digits.substring(0, 1);
            //查询字符对应的字符串
            String letters = phone.get(digit);
            for (int i = 0; i < letters.length(); i++) {
                //字符串中第一个字符
                String letter = phone.get(digit).substring(i, i + 1);
                //将这个字符和别的字符进行组合
                backtruck(combination + letter, next_digits.substring(1));
            }
        }
    }
    /**
     * 将第一个字符串保留，在别的字符中搜索这个字符，若没有则将第一个字符减一继续寻找
     *
     * @param strs 字符数组
     * @return 字符数组中相同的前缀
     */
    private static String longestCommonPrefix(String[] strs) {
        if(strs ==null ||strs.length == 0){
            return null;
        }
        String s = strs[0];
        for(int i = 1;i<strs.length;i++){
            //若等于0，则他们是相同的
            while(strs[i].indexOf(s)!=0){
                s = s.substring(0,s.length()-1);
                if(s.isEmpty()){
                    return "";
                }
            }
        }
        return s;
    }
        /**
         * 遍历数组找出最小长度的那个字符串 而后根据二分查找去寻找字符串中相同部分的字符串
         * @param strs 字符数组
         * @return 字符数组中相同的首个字符串
         */
    private static String longestCommonPrefix2(String[] strs) {
        if(strs ==null ||strs.length == 0){
            return null;
        }
        int t = Integer.MAX_VALUE;
        for(String c :strs){
            t = Math.min(t,c.length());
        }
        int low = 1;
        int high = t;
        while(low<=high){
            int mid = (low+high)/2;
            if(isCommon(strs,mid)){
                low = mid+1;
            }else{
                high = mid -1;
            }
        }
        return strs[0].substring(0,(low+high)/2);
    }

    private static boolean isCommon(String[] strs, int mid ){
        String str1 = strs[0].substring(0,mid);
        for(int i = 1;i<strs.length;i++){
            if(!strs[i].startsWith(str1)){
                return false;
            }
        }
        return true;
    }

    /**
     *将字符数组的第一个字符取出来遍历这个字符串
     * 同时进行遍历整个数组的第2个到最后一个元素 如果 后面有字符串的长度是当前遍历的位置或者对应的字符不相等则返回字符切割后的字符串
     * 总体上是进行水平扫描切割
     * @param strs 字符数组
     * @return 字符数组每个元素相同的首个字符串
     */
    private static String longestCommonPrefix1(String[] strs) {
        if(strs ==null ||strs.length == 0){
            return null;
        }
        for(int i = 0 ;i<strs[0].length();i++){
            char c = strs[0].charAt(i);
            for(int j = 1;j<strs.length;j++){
                if(i == strs[j].length()||strs[j].charAt(i)!=c){
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }
    /**
     * 使用map存放有可能的结果，再将字符串进行遍历，只有单字符或者双字符两种情况
     * @param s 罗马字符串
     * @return 罗马字符串对应的数字
     */
    private static int romanToInt(String s) {
        Map<String,Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
        int ans = 0;
        for(int i = 0 ; i<s.length();){
            if(i+1<s.length()&& map.containsKey(s.substring(i,i+2))){
                ans += map.get(s.substring(i,i+2));
                i+=2;
            }else{
                ans+=map.get(s.substring(i,i+1));
                i++;
            }
        }
        return ans;
    }
    /**
     * 整数转为罗马数字
     * @param num 整数
     * @return 整数转成的罗马数字
     */
    private static String intToRoman(int num) {
        int []nums = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String []chars ={"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder res = new StringBuilder();
        for(int i = 0;i<nums.length;i++){
            while(num>=nums[i]){
                num -=nums[i];
                res.append(chars[i]);
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
//        System.out.println(LeeCode.intToRoman(1994));
//        String s = "V";
//        System.out.println(LeeCode.romanToInt("V"));
//        String[]arr = {"flower","flow","flight"};
//        System.out.println(LeeCode.longestCommonPrefix(arr));
//        LeeCode l = new LeeCode();
//        System.out.println(l.letterCombinations("23"));
//        System.out.println(l.generateParenthesis(2));
        System.out.println(LeeCode.addBinary("111","110"));
    }
}
