package com.naoh.iossupersign.thridparty.appleapi;

import com.naoh.iossupersign.enums.AppleApiEnum;
import com.naoh.iossupersign.model.bo.AuthorizeBO;
import com.naoh.iossupersign.model.dto.AppleApiResult;
import com.naoh.iossupersign.model.dto.AppleReqBody;
import com.naoh.iossupersign.model.dto.AppleResultDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public List<AppleResultDTO> getNumberOfAvailableDevices(AuthorizeBO authorizeBO) {
        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();
        ResponseEntity<AppleApiResult<List<AppleResultDTO>>> response = restTemplate.exchange(url,AppleApiEnum.LIST_DEVICE_API.getHttpMethod(),httpEntity,
                new ParameterizedTypeReference<AppleApiResult<List<AppleResultDTO>>>(){});
        AppleApiResult<List<AppleResultDTO>> responseBody = response.getBody();
        return responseBody.getData();
    }

    /**
     * 註冊新設備到蘋果帳號
     * @param udid
     * @param authorizeBO
     * @return
     */
    public AppleResultDTO registerNewDevice(String udid , AuthorizeBO authorizeBO){
        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());

        AppleReqBody attributes = AppleReqBody.init().add("name", udid).add("udid", udid).add("platform", "IOS");
        AppleReqBody body = AppleReqBody.init().add("type", "devices").add("attributes", attributes);
        AppleReqBody data = AppleReqBody.init().add("data",body);
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(data,headers);
        String url = AppleApiEnum.LIST_DEVICE_API.getApiPath();
        ResponseEntity<AppleApiResult<AppleResultDTO>> response = restTemplate.exchange(url,AppleApiEnum.REGISTER_NEW_DEVICE_API.getHttpMethod(),httpEntity,
                new ParameterizedTypeReference<AppleApiResult<AppleResultDTO>>(){});
        return response.getBody().getData();
    }

    public AppleResultDTO registerNewBundleId(AuthorizeBO authorizeBO){

        HttpHeaders headers = getToken(authorizeBO.getP8(), authorizeBO.getIss(), authorizeBO.getKid());

        AppleReqBody attributes = AppleReqBody.init().add("identifier", "com.*").add("name", "AppBundleId").add("platform", "IOS");
        AppleReqBody body = AppleReqBody.init().add("type", "bundleIds").add("attributes", attributes);
        AppleReqBody data = AppleReqBody.init().add("data",body);
        HttpEntity<Map<String,Object>> httpEntity = new HttpEntity<>(data,headers);
        String url = AppleApiEnum.REGISTER_NEW_BUNDLEID_API.getApiPath();
        ResponseEntity<AppleApiResult<AppleResultDTO>> response = restTemplate.exchange(url,AppleApiEnum.REGISTER_NEW_BUNDLEID_API.getHttpMethod(),httpEntity,
                new ParameterizedTypeReference<AppleApiResult<AppleResultDTO>>(){});

        System.out.println(response.getBody().getData());
        return response.getBody().getData();
    }


}
