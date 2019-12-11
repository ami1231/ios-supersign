package com.naoh.iossupersign.thridparty.appleapi;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.naoh.iossupersign.enums.AppleApiEnum;
import com.naoh.iossupersign.model.bo.AuthorizeBO;

import com.naoh.iossupersign.model.po.ApplePO;

import com.naoh.iossupersign.model.dto.AppleApiResult;
import com.naoh.iossupersign.model.dto.AppleDeviceDTO;
import com.naoh.iossupersign.model.dto.AppleReqBody;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppleApiService extends AppleApi{

//    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 取得帳號下的設備資訊
     * @param authorizeBO
     * @return
     */
    public List<AppleDeviceDTO> getNumberOfAvailableDevices(AuthorizeBO authorizeBO) {
        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();
        ResponseEntity<AppleApiResult<List<AppleDeviceDTO>>> response = restTemplate.exchange(url,AppleApiEnum.LIST_DEVICE_API.getHttpMethod(),httpEntity,
                new ParameterizedTypeReference<AppleApiResult<List<AppleDeviceDTO>>>(){});
        AppleApiResult<List<AppleDeviceDTO>> responseBody = response.getBody();
        return responseBody.getData();
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
//        String result = HttpRequest.post(url).
//                addHeaders(getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid())).
//                body(JSON.toJSONString(data)).execute().body();
//        Map map = JSON.parseObject(result, Map.class);
//        Map data1 = (Map) map.get("data");
//        String id = (String)data1.get("id");
        return "";
    }

    /**
     * 註冊新設備到蘋果帳號
     * @param udid
     * @param authorizeBO
     * @return
     */
    public AppleDeviceDTO registerNewDevice(String udid , AuthorizeBO authorizeBO){
        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());

        AppleReqBody attributes = AppleReqBody.init().add("name", udid).add("udid", udid).add("platform", "IOS");
        AppleReqBody body = AppleReqBody.init().add("type", "devices").add("attributes", attributes);
        AppleReqBody data = AppleReqBody.init().add("data",body);
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(data,headers);
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();
        ResponseEntity<AppleApiResult<AppleDeviceDTO>> response = restTemplate.exchange(url,AppleApiEnum.REGISTER_NEW_DEVICE_API.getHttpMethod(),httpEntity,
                new ParameterizedTypeReference<AppleApiResult<AppleDeviceDTO>>(){});
        return response.getBody().getData();
    }


    public File getMobileprovision(ApplePO applePO, String devId){

        Map<String, Object> requestData = new HashMap<>();

        Map<String, Object> profileCreateRequest = new HashMap<>();
        profileCreateRequest.put("attributes", getAttributes());
        profileCreateRequest.put("relationships", getRelationships(applePO, devId));
        profileCreateRequest.put("type", "profiles");

        requestData.put("data", profileCreateRequest);

        String url = AppleApiEnum.CREATE_PROFILE_API.getApiPath();
//        restTemplate.exchange(url, AppleApiEnum.CREATE_PROFILE_API.getHttpMethod(), )

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

    private Map<String, Object> getDevices(String devId) {
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
