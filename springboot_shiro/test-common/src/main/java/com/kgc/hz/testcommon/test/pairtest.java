package com.kgc.hz.testcommon.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: szh
 * @since:
 */
public class pairtest {
    /**
     * 循环遍历这个数组，计算数组中每个元素与target的差值
     * 先进行判断，map中是否存在这个差值，如果有直接返回
     * 如果没有 值座位键，下标作为值存到map中
     * 循环全部结束后没有找到返回空
     * map查找需要O（1） 循环遍历需要O（n）时间复杂度为O（n）
     * @param nums
     * @param target
     * @return
     */
    public static int[] findSum(int []nums, int target){
        Map map = new HashMap<>();
        for(int i=0;i< nums.length;i++){
            int difference = target - nums[i];
            if(map.containsKey(difference)){
                return new int[]{(int) map.get(difference),i};
            }
            map.put(nums[i],i);
        }
        System.out.println("没有配对");
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,11,15};
        System.out.println(Arrays.toString(findSum(nums,9)));
    }
}
