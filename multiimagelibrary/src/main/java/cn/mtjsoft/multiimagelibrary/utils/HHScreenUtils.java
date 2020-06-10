package cn.mtjsoft.multiimagelibrary.utils;

import android.content.Context;

import java.util.HashMap;

public class HHScreenUtils
{
	private static final String tag=HHScreenUtils.class.getName();
	public static final String SCREEN_HEIGHT="height";
	public static final String SCREEN_WIDTH="width";
	/**
     * 获取屏幕的宽度
     * @param context 上下文对象
     * @return
     */
    public static int getScreenWidth(Context context)
    {
    	return context.getResources().getDisplayMetrics().widthPixels;
    }
    /**
     * 获取屏幕的高度
     * @param context 上下文对象
     * @return
     */
    public static int getScreenHeight(Context context)
    {
    	return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕的高度和宽度
     * @param context 上下文对象
     * @return 返回一个HashMap<String,Integer>,获取宽度用SCREEN_WIDTH，获取高度用SCREEN_HEIGHT
     */
    public static HashMap<String, Integer> getScreenHeightAndWidth(Context context)
    {
    	HashMap<String, Integer> map=new HashMap<String, Integer>();
    	map.put(SCREEN_HEIGHT, getScreenHeight(context));
    	map.put(SCREEN_WIDTH, getScreenWidth(context));
    	return map;
    }
}
