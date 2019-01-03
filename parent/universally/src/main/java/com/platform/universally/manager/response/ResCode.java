package com.platform.universally.manager.response;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码
 * Created by Administrator on 2018/12/25.
 */
public class ResCode {

    public final static Integer SUCCESS = 1;

    public final static Integer FAIL = 0;


    public final static Map<Integer, String> MSG = new HashMap<Integer, String>() {
        {
            put(SUCCESS, "成功");
            put(FAIL, "失败");
        }
    };

}
