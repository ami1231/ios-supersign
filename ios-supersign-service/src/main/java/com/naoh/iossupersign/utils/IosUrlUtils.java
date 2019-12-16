package com.naoh.iossupersign.utils;

public class IosUrlUtils {

    /**
     * 取得下载Plist
     * @param downloadUrl
     * @param udid
     * @param ipaId
     * @return
     */
    public static String getItemServiceUrl(String downloadUrl , String ipaId , String udid){
        return "itms-services://?action=download-manifest&url="+downloadUrl+"/apps/download/"+ipaId+"/"+udid+"/manifest.plist";
    }

    /**
     * 曲额下载ipa view
     * @param udidDownloadUrl
     * @param ipaId
     * @param udid
     * @return
     */
    public static String getRedirectIpaViewUrl(String udidDownloadUrl , String ipaId ,String udid){
        return udidDownloadUrl+"/udid/app/ipa/"+ipaId+"?udid="+udid;
    }

    /**
     * 取得下载ipa位置
     * @param downloadUrl
     * @return
     */
    public static String getIpaUrl(String downloadUrl){
        //FIXME
        return downloadUrl+"/ipa/signature.ipa";
    }

    public static String getUdidViewUrl(String ipaDownloadUrl, String ipaDownloadId) {
        return ipaDownloadUrl+"/udid/download/view/"+ipaDownloadId;
    }

    public static CharSequence getUDIDUrl(String mobileConfigUrlPath, String s, String ipaDownloadId) {
        return mobileConfigUrlPath+"/udid/getUDID/"+ipaDownloadId;
    }
}