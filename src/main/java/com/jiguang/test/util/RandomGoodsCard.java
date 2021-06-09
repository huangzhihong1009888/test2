package com.jiguang.test.util;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;

import java.net.StandardSocketOptions;
import java.util.*;

/**
 * @author huang
 */
public class RandomGoodsCard {
    /**
     * 自定义32进制
     */
    final static char[] DIGITS =new char[]{
            '2','3','4','5','6','7','8','9',
            'A','B','C','D','E','F','G','H',
            'J','K','L','M','N','P','Q','R',
            'S','T','U','V','W','X','Y','Z'
    };

    /**
     * 32进制 最大四位数YYYY
     */
    private static final int MAX_LAST =1014750;

    public static void main(String[] args) {
        System.out.println(coverCode(4,"E","2"));

    }
    public static String coverCode(int size,String code,String coverData){
        if(code.length()<size){
            StringBuilder cover = new StringBuilder();
            for(int i=0;i<size-code.length();i++){
                cover.append(coverData);
            }
            code =  cover.append(code).toString();
        }
        return code;
    }
    public static List<String> getCode(int number){
        List<String> codeList = new ArrayList<>();

        Random random=new Random();
        Set<Integer> lastRan=new HashSet<>();
        while(lastRan.size()<number){
            lastRan.add(random.nextInt(MAX_LAST));
        }

        String code = digits32(System.currentTimeMillis()).substring(1);
        System.out.println(code);
        char[] codeArray = code.toCharArray();
        for (Integer last:lastRan) {
            String randomCode = digits32(last);
            code = parseCode(codeArray,randomCode);
            codeList.add(code);
        }
        return codeList;
    }

    private static String parseCode(char[] codeArray, String randomCode) {
        if(randomCode.length()<4){
            StringBuilder cover = new StringBuilder();
            for(int i=0;i<4-randomCode.length();i++){
                cover.append(DIGITS[0]);
            }
            randomCode =  cover.append(randomCode).toString();
        }
        char[] randomCodeArray = randomCode.toCharArray();
        System.out.println(JSON.toJSON(Arrays.toString(codeArray)));
        System.out.println(JSON.toJSON(Arrays.toString(randomCodeArray)));
        return new StringBuilder()
                .append(randomCodeArray[0])
                .append(codeArray[7])
                .append(codeArray[1])
                .append(randomCodeArray[1])
                .append(codeArray[0])
                .append(codeArray[6])
                .append(codeArray[4])
                .append(codeArray[5])
                .append(randomCodeArray[2])
                .append(codeArray[3])
                .append(codeArray[2])
                .append(randomCodeArray[3])
                .toString();
    }

    public static String digits32(long val) {
        // 32=2^5=二进制100000
        int shift = 5;
        // numberOfLeadingZeros 获取long值从高位连续为0的个数，比如val=0，则返回64
        // 此处mag=long值二进制减去高位0之后的长度
        int mag = Long.SIZE - Long.numberOfLeadingZeros(val);
        int len = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[len];
        do {
            // &31相当于%32
            buf[--len] = DIGITS[((int) val) & 31];
            val >>>= shift;
        } while (val != 0 && len > 0);
        return new String(buf);
    }

}
