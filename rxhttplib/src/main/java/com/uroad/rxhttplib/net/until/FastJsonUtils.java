package com.uroad.rxhttplib.net.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class FastJsonUtils {

    /**
     * 判断接口返回数据状态是否正常
     *
     * @param jsonObject
     * @return
     */
    public static boolean getJsonStatu(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("code").equalsIgnoreCase("200")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断接口返回数据状态是否正常
     *
     * @param jsonObject
     * @return
     */
    public static boolean getJsonStatuByStatus(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("status").equalsIgnoreCase("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断接口返回数据状态是否正常
     *
     * @param jsonObject
     * @return
     */
    public static boolean getJsonStatuByStatus(JSONObject jsonObject,String key,String state) {
        try {
            if (jsonObject.getString(key).equalsIgnoreCase(state)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 判断接口返回数据状态是否正常
     *
     * @param jsonObject
     * @return
     */
    public static boolean getJsonStatuByInt(JSONObject jsonObject) {
        try {
            if (jsonObject.getInteger("status") == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取接口返回的错误信息
     *
     * @param jsonObject
     * @param key
     * @return: String
     */
    public static String getErrorString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONArray getArr(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONArray(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getData(JSONObject json) {
        try {
            return json.get("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getMsg(JSONObject json) {
        try {
            return json.get("msg").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取错误信息
     *
     * @param result json数据
     * @return 错误信息
     */
    public static String getErrMsg(JSONObject result) {
        String msg = "网络异常";
        try {
            if ("error".equalsIgnoreCase(result.getString("status"))) {
                msg = result.getString("msg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    /***
     * 获取json里面的某个key 对应value值
     *
     * @param json
     * @param key
     * @return
     */
    public static String getString(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取json里面的某个key 对应value值
     *
     * @param json
     * @param key
     * @return
     */
    public static boolean getBoolean(JSONObject json, String key) {
        try {
            return json.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /***
     * 获取json里面的某个key 对应value值
     *
     * @param json
     * @param key
     * @return
     */
    public static int getInt(JSONObject json, String key) {
        try {
            return json.getInteger(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -99;
    }

    /***
     * 获取data里面的某个key 对应value值
     *
     * @param json
     * @param key
     * @return
     */
    public static String getDataString(JSONObject json, String key) {
        try {
            return json.getJSONObject("data").getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * to list 传入data里面的数据
     */
    public static <T> List<T> parseArrayJSON(String jsonText, Class<T> clazz) {
        try {
            return JSON.parseArray(jsonText, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * to object 传入data里面的数据
     */
    public static <T> T parseObjectJSON(String jsonText, Class<T> clazz) {
        try {
            return JSON.parseObject(jsonText, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * to list data 传入后台返回的整个json
     */
    public static <T> List<T> parseDataArrayJSON(JSONObject jsonObject, Class<T> clazz) {
        try {
            String jsonText = getData(jsonObject);
            return JSON.parseArray(jsonText, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * to object data 传入后台返回的整个json
     */
    public static <T> T parseDataObjectJSON(JSONObject jsonObject,
                                            Class<T> clazz) {
        try {
            String jsonText = getData(jsonObject);
            return JSON.parseObject(jsonText, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json里面的数组转为list
     *
     * @param json
     * @param clazz
     * @param key
     * @return
     */
    public static <T> List<T> parseToList(JSONObject json, Class<T> clazz, String key) {
        try {
            String jsonText = json.get(key).toString();
            List<T> list = JSON.parseArray(jsonText, clazz);
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json里面的数据转为object
     *
     * @param json
     * @param clazz
     * @param key
     * @return
     */
    public static <T> T parseToObject(JSONObject json, Class<T> clazz, String key) {
        try {
            String jsonText = json.get(key).toString();
            return JSON.parseObject(jsonText, clazz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * map转为json
     *
     * @param map
     * @return
     */
    public static String map2JSON(Map map) {
        String json = "";
        try {
            return JSON.toJSONString(map);
        } catch (Exception e) {
            return json;
        }
    }

    /**
     * string 转 json
     *
     * @param data
     * @return
     */
    public static JSONObject string2JSON(String data) {
        try {
            return JSON.parseObject(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断list 是否有数据
     *
     * @param list
     * @return
     */
    public static <T> boolean listHasData(List<T> list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
}
