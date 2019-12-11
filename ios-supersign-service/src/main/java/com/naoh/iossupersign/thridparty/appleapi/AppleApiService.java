package com.naoh.iossupersign.thridparty.appleapi;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.naoh.iossupersign.enums.AppleApiEnum;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.po.ApplePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AppleApiService extends AppleApi{

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 取得帳號下的設備資訊
     * @param authorizeBO
     * @return
     */
    public Map<String,String> getNumberOfAvailableDevices(AuthorizeBO authorizeBO) {
        Map header = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());
        Map<String,String> res = new HashMap<>();
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();


//        restTemplate.exchange(url,AppleApiEnum.LIST_DEVICE_API.getHttpMethod(),)


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

    public String addDeviceUdidToAppleAccount(String udid , AuthorizeBO authorizeBO){
        Map<String,String> body = new HashMap<>();
        body.put("type", "devices");
        Map attributes = new HashMap();
        attributes.put("name", udid);
        attributes.put("udid", udid);
        attributes.put("platform", "IOS");
//        body.put("attributes", attributes);
        Map data = new HashMap();
        data.put("data", body);
        String url = AppleApiEnum.REGISTER_NEW_DEVICE_API.getApiPath();
        String result = HttpRequest.post(url).
                addHeaders(getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid())).
                body(JSON.toJSONString(data)).execute().body();
        Map map = JSON.parseObject(result, Map.class);
        Map data1 = (Map) map.get("data");
        String id = (String)data1.get("id");
        return id;
    }


    public File getMobileprovision(ApplePO applePO, String devId){

        Map<String, Object> requestData = new HashMap<>();

        Map<String, Object> profileCreateRequest = new HashMap<>();
        profileCreateRequest.put("attributes", getAttributes());
        profileCreateRequest.put("relationships", getRelationships(applePO, devId));
        profileCreateRequest.put("type", "profiles");

        requestData.put("data", profileCreateRequest);

        String url = AppleApiEnum.CREATE_PROFILE_API.getApiPath();
        restTemplate.exchange(url, AppleApiEnum.CREATE_PROFILE_API.getHttpMethod(), )

        File file = null;
        return file;
    }

    private Map<String, Object> getAttributes(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "Mobileprovision123");
        attributes.put("profileType", "IOS_APP_DEVELOPMENT");
        return attributes;
    }

    private Map<String, Object> getRelationships(ApplePO applePO, String devId){
        Map<String, Object> relationships = new HashMap<>();
        relationships.put("bundleId", getBundleId(applePO));
        relationships.put("certificates", getCertificates(applePO));
        relationships.put("devices", getDevices(devId));
        return relationships;
    }

    private Map<String, Object> getBundleId(ApplePO applePO){
        Map<String, Object> bundleId = new HashMap<>();

        Map<String, Object> data = new HashMap<>();
        data.put("id", applePO.getBundleIds());
        data.put("type", "bundleIds");

        bundleId.put("data", data);

        return bundleId;
    }

    private Map<String, Object> getCertificates(ApplePO applePO){
        Map<String, Object> certificates = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> data = new HashMap<>();
        data.put("id", applePO.getCerId());
        data.put("type", "certificates");

        list.add(data);

        certificates.put("data", list);
        return certificates;
    }

    private Map<String, Object> getDevices(String devId){
        Map<String, Object> devices = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> data = new HashMap<>();
        data.put("id", devId);
        data.put("type", "devices");

        list.add(data);
        devices.put("data", list);

        return devices;
    }


}
