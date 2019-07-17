package com.example.education.tools;

import android.util.Log;

import com.sun.script.javascript.JSAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonService {
    // 单实例
    private static JsonService instance;

    private JsonService() {
    }

    public static JsonService getInstance() {
        if (instance == null)
            instance = new JsonService();
        return instance;
    }

    // 初始时调用，否则必定出错
    public void createJsonObject(String str) {
        try {
            JSONObject rootObject = new JSONObject(str);
            JSONObject dataObject = rootObject.getJSONObject("data");

            /*********************** 第一部分的数据 **************************/
            JSONObject dataObject_ZG = dataObject.getJSONObject("zhuangGuaTable");
            JSONObject dataObject_1 = dataObject_ZG.getJSONObject("basicData");
            JSONArray dataObject_kong_zhuanggua = dataObject_ZG.getJSONArray("kongIndex");
            JSONArray dataObject_kong_dizhi = dataObject_ZG.getJSONArray("kongDiZhi");
            // 六亲
            liuqin_1 = dataObject_1.getString("six_relatives");
            zhuangGua1 = dataObject_1.getString("five_elements");
            zhuangGua2 = dataObject_ZG.getJSONArray("column2");
            zhuangGua3 = dataObject_ZG.getJSONArray("column3");
            zhuangGua4 = dataObject_ZG.getJSONArray("column4");
            zhuangGua5 = dataObject_ZG.getJSONArray("column5");
            zhuangGua6 = dataObject_ZG.getJSONArray("column6");
            //身
            shen_gua = dataObject_1.getString("gua_shen");
            Log.e("shen", shen_gua);

            heavenly_stems_zhuanggua = dataObject_1.getString("heavenly_stems");
            earthly_branches = dataObject_1.getString("earthly_branches");
            //content
            content_zhuanggua = dataObject_1.getString("content");
            Log.e("DAYT", content_zhuanggua);
            //times
            times_zhuanggua = dataObject_1.getString("times");

            //空表
            kong_zhuanggua = new ArrayList();
            for (int i = 0; i < dataObject_kong_zhuanggua.length(); ++i) {
                kong_zhuanggua.add(dataObject_kong_zhuanggua.get(i));
            }
            //空地支
            kong_dizhi = new ArrayList();
            for (int i = 0; i < dataObject_kong_dizhi.length(); ++i) {
                kong_dizhi.add(dataObject_kong_dizhi.get(i));
            }

            // 世应
            shiying = dataObject_1.getString("shi_ying");

            /*********************** 第二部分的数据 **************************/
            JSONObject dataObject_BG = dataObject.getJSONObject("bianGuaTable");
            JSONObject dataObject_2 = dataObject_BG.getJSONObject("basicData");

            // 六亲
            liuqin_2 = dataObject_2.getString("six_relatives");
            //变卦五行的部分
            bianGua1 = dataObject_2.getString("five_elements");
            //变卦第二列新增
            bianGua2 = dataObject_BG.getJSONArray("column2");
            bianGua3 = dataObject_BG.getJSONArray("column3");
            bianGua4 = dataObject_BG.getJSONArray("column4");
            bianGua5 = dataObject_BG.getJSONArray("column5");
            bianGua6 = dataObject_BG.getJSONArray("column6");
            //content
            content_biangua = dataObject_2.getString("content");
            //earthly_branches
            earthly_biangua = dataObject_2.getString("earthly_branches");
            //heavenly_stems
            heavenly_stems_biangua = dataObject_2.getString("heavenly_stems");
            Log.e("DAYww", content_biangua);
            //times
            times_biangua = dataObject_2.getString("times");
            //变卦部分的show下标
            JSONArray dataObject_show_biangua = dataObject_BG.getJSONArray("showIndex");
            show_biangua = new ArrayList();
            for (int i = 0; i < dataObject_show_biangua.length(); ++i) {
                show_biangua.add(dataObject_show_biangua.get(i));
            }
            //变卦部分的kong下标
            JSONArray dataObject_kong_biangua = dataObject_BG.getJSONArray("kongIndex");
            kong_biangua = new ArrayList();
            for (int i = 0; i < dataObject_kong_biangua.length(); ++i) {
                kong_biangua.add(dataObject_kong_biangua.get(i));
            }

            /*********************** 第三部分的数据 **************************/
            JSONObject dataObject_FS = dataObject.getJSONObject("fuShenTable");
            JSONObject dataObject_3 = dataObject_FS.getJSONObject("basicData");

            // 六亲
            liuqin_3 = dataObject_3.getString("six_relatives");
            //五行（伏神部分）
            fuShen1 = dataObject_3.getString("five_elements");
            //复审第二列新增
            fuShen2 = dataObject_FS.getJSONArray("column2");
            fuShen3 = dataObject_FS.getJSONArray("column3");
            fuShen4 = dataObject_FS.getJSONArray("column4");
            fuShen5 = dataObject_FS.getJSONArray("column5");
            fuShen6 = dataObject_FS.getJSONArray("column6");
            Log.e("WX", fuShen1);
            //content
            content_fushen = dataObject_3.getString("content");
            //times
            times_fushen = dataObject_3.getString("times");

            heavenly_stems_fushen = dataObject_3.getString("heavenly_stems");
            earthly_fushen = dataObject_3.getString("earthly_branches");

            //伏神部分的show下标
            JSONArray dataObject_show_fushen = dataObject_FS.getJSONArray("showIndex");
            show_fushen = new ArrayList();
            for (int i = 0; i < dataObject_show_fushen.length(); ++i) {
                show_fushen.add(dataObject_show_fushen.get(i));
            }
            //复审部分的kong下标
            JSONArray dataObject_kong_fushen = dataObject_FS.getJSONArray("kongIndex");
            kong_fushen = new ArrayList();
            for (int i = 0; i < dataObject_kong_fushen.length(); ++i) {
                kong_fushen.add(dataObject_kong_fushen.get(i));
            }

            /*********************** 第四部分的数据 **************************/
            JSONArray dataObject_da = dataObject.getJSONArray("dayTable");
            daytable = "";
            for (int i = 0; i < 5; ++i) {
                daytable += dataObject_da.getString(i);
            }

            JSONArray dataObject_qin = dataObject.getJSONArray("qingTable");
            qintable = new ArrayList();
            for (int i = 0; i < 5; ++i) {
                qintable.add(dataObject_qin.get(i));
            }

            JSONObject dataObject_biantable = dataObject.getJSONObject("bianYaoTable");
            JSONArray dataObject_bian = dataObject_biantable.getJSONArray("bianYao");
            biantable = new ArrayList();
            for (int i = 0; i < 5; ++i) {
                biantable.add(dataObject_bian.get(i));
            }
            Log.e("bianYao", dataObject_bian.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /************************* 存储数据 *************************/

    // 六亲，从左至右
    private String liuqin_1;

    public String getLiuqin_1() {
        return liuqin_1;
    }

    private String liuqin_2;

    public String getLiuqin_2() {
        return liuqin_2;
    }

    private String liuqin_3;

    public String getLiuqin_3() {
        return liuqin_3;
    }

    //content
    //装卦里的content
    private String content_zhuanggua;

    public String getContent_zhuanggua() {
        return content_zhuanggua;
    }

    //装卦里的卦次
    private String times_zhuanggua;

    public String getTimes_zhuanggua() {
        return times_zhuanggua;
    }

    //变卦里的content
    private String content_biangua;

    public String getContent_biangua() {
        return content_biangua;
    }

    //变卦里的卦次
    private String times_biangua;

    public String getTimes_biangua() {
        return times_biangua;
    }

    //身表
    private String shen_gua;

    public String getShen_gua() {
        return shen_gua;
    }

    //伏神里的content
    private String content_fushen;

    public String getContent_fushen() {
        return content_fushen;
    }

    //伏神里的卦次
    private String times_fushen;

    public String getTimes_fushen() {
        return times_fushen;
    }


    //五行，装卦里的那部分
    private String zhuangGua1;

    public String getZhuangGua1() {
        return zhuangGua1;
    }

    //五行，变卦里的那部分
    private String bianGua1;

    public String getBianGua1() {
        return bianGua1;
    }

    //五行，伏神里的那部分
    private String fuShen1;

    public String getFuShen1() {
        return fuShen1;
    }

    //装挂，变卦，复审的第二列
    private JSONArray zhuangGua2;

    public JSONArray getZhuangGua2() {
        return zhuangGua2;
    }

    private JSONArray bianGua2;

    public JSONArray getBianGua2() {
        return bianGua2;
    }

    private JSONArray fuShen2;

    public JSONArray getFuShen2() {
        return fuShen2;
    }

    //第三列
    private JSONArray zhuangGua3;

    public JSONArray getZhuangGua3() {
        return zhuangGua3;
    }

    private JSONArray bianGua3;

    public JSONArray getBianGua3() {
        return bianGua3;
    }

    private JSONArray fuShen3;

    public JSONArray getFuShen3() {
        return fuShen3;
    }

    //第四列
    private JSONArray zhuangGua4;

    public JSONArray getZhuangGua4() {
        return zhuangGua4;
    }

    private JSONArray bianGua4;

    public JSONArray getBianGua4() {
        return bianGua4;
    }

    private JSONArray fuShen4;

    public JSONArray getFuShen4() {
        return fuShen4;
    }

    //5
    private JSONArray zhuangGua5;

    public JSONArray getZhuangGua5() {
        return zhuangGua5;
    }

    private JSONArray bianGua5;

    public JSONArray getBianGua5() {
        return bianGua5;
    }

    private JSONArray fuShen5;

    public JSONArray getFuShen5() {
        return fuShen5;
    }

    //6
    private JSONArray zhuangGua6;

    public JSONArray getZhuangGua6() {
        return zhuangGua6;
    }

    private JSONArray bianGua6;

    public JSONArray getBianGua6() {
        return bianGua6;
    }

    private JSONArray fuShen6;

    public JSONArray getFuShen6() {
        return fuShen6;
    }

    //heavenly_stems
    private String heavenly_stems_zhuanggua;

    public String getHeavenly_stems() {
        return heavenly_stems_zhuanggua;
    }

    private String heavenly_stems_biangua;

    public String getHeavenly_stems_biangua() {
        return heavenly_stems_biangua;
    }

    private String heavenly_stems_fushen;

    public String getHeavenly_stems_fushen() {
        return heavenly_stems_fushen;
    }

    //earthly_branches
    private String earthly_branches;

    public String getEarthly_branches() {
        return earthly_branches;
    }

    private String earthly_biangua;

    public String getEarthly_biangua() {
        return earthly_biangua;
    }

    private String earthly_fushen;

    public String getEarthly_fushen() {
        return earthly_fushen;
    }

    // 世应
    private String shiying;

    public String getShiying() {
        return shiying;
    }

    //日表（下端那五个）
    private String daytable;

    public String getDaytable() {
        return daytable;
    }

    //亲表(下端那个）
    private List qintable = new ArrayList();

    public List getQintable() {
        return qintable;
    }

    //变表
    private List biantable = new ArrayList();

    public List getBiantable() {
        return biantable;
    }

    //装卦表的kong下标
    private List kong_zhuanggua = new ArrayList();

    public List getKong_zhuanggua() {
        return kong_zhuanggua;
    }

    //装挂的kong地支
    private List kong_dizhi = new ArrayList();

    public List getKong_dizhi() {
        return kong_dizhi;
    }


    //变卦表的show下标
    private static List show_biangua = new ArrayList();

    public static List getShow_biangua() {
        return show_biangua;
    }

    //变卦表的kong下标
    private List kong_biangua = new ArrayList();

    public List getKong_biangua() {
        return kong_biangua;
    }

    //伏神表的show下标
    private static List show_fushen = new ArrayList();

    public static List getShow_fushen() {
        return show_fushen;
    }

    //伏神表的kong下标
    //伏神的空表貌似后台还没有算出来，始终返回的都是null
    private List kong_fushen = new ArrayList();

    public List getKong_fushen() {
        return kong_fushen;
    }


}
