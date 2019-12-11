package com.naoh.iossupersign.thridparty.appleapi;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.naoh.iossupersign.enums.AppleApiEnum;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleApiResult;
import com.naoh.iossupersign.model.dto.AppleDeviceDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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





}
