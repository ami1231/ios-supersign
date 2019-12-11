package com.naoh.iossupersign.thridparty.appleapi;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.naoh.iossupersign.enums.AppleApiEnum;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AppleApiService extends AppleApi{

    /**
     * 取得帳號下的設備資訊
     * @param authorizeBO
     * @return
     */
    public Map getNumberOfAvailableDevices(AuthorizeBO authorizeBO) {
        Map header = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());
        Map res = new HashMap();
        String url = AppleApiEnum.ACCOUNT_DEVICE_API.getApiPath();
        String result = HttpRequest.get(url).
                addHeaders(header).
                execute().body();
        Map map = JSON.parseObject(result, Map.class);
        System.out.println(result);
        JSONArray data = (JSONArray)map.get("data");
        List devices = new LinkedList();
        for (Object datum : data) {
            Map device = new HashMap();
            Map m = (Map) datum;
            String id = (String)m.get("id");
            Map attributes = (Map)m.get("attributes");
            String udid = (String)attributes.get("udid");
            device.put("deviceId", id);
            device.put("udid", udid);
            devices.add(device);
        }
//        removeCertificates(header);
//        removeBundleIds(header);
//        String cerId = insertCertificates(header, authorize.getCsr());
//        String bundleIds = insertBundleIds(header);
//        Map meta = (Map) map.get("meta");
//        Map paging = (Map)meta.get("paging");
//        int total = (int) paging.get("total");
//        res.put("number", Config.total-total);
//        res.put("devices", devices);
//        res.put("cerId", cerId);
//        res.put("bundleIds", bundleIds);
        System.out.println(res);
        return res;
    }





}
