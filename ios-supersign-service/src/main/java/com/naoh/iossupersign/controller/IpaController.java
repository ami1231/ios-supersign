package com.naoh.iossupersign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.naoh.iossupersign.assembly.IpaPackageAssembly;
import com.naoh.iossupersign.base.ApiResult;
import com.naoh.iossupersign.exception.ServiceException;
import com.naoh.iossupersign.model.bo.IpaPackageBO;
import com.naoh.iossupersign.model.po.IpaPackagePO;
import com.naoh.iossupersign.service.Ipapackage.IpaPackageBSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Peter.Hong
 * @date 2019/12/10
 */
@Api("ipa管理")
@Controller
@RequestMapping("ipa")
public class IpaController {

    private static final Integer DEFAULT_PAGE = 1;

    private final IpaPackageBSService ipaPackageBSService;

    private final IpaPackageAssembly ipaPackageAssembly;

    @Autowired
    public IpaController(IpaPackageBSService ipaPackageBSService, IpaPackageAssembly ipaPackageAssembly) {
        this.ipaPackageBSService = ipaPackageBSService;
        this.ipaPackageAssembly = ipaPackageAssembly;
    }

    @RequestMapping("/index")
    public String index(Model model){
        return search(DEFAULT_PAGE, "", model);
    }


    @ApiOperation(value = "/uploadPackage", notes = "上传ipa", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @PostMapping(value = "/uploadPackage")
    public ApiResult<String> uploadPackage(@RequestPart(value = "ipaFile")MultipartFile file, @RequestParam("summary")String summary) {
        ApiResult<String> result = new ApiResult<>();
        try {
            IpaPackageBO ipaPackageBO = ipaPackageAssembly.assembleDeviceBO(null, file, summary);
            ipaPackageBSService.uploadIpa(ipaPackageBO);
            result.setCode(ApiResult.SUCCESS_CODE);
            result.setMsg(ApiResult.SUCCESS_MSG);

        }catch (ServiceException e){
            result.setMsg(e.getErrorMsg());
        }
        return result;
    }

    @ApiOperation(value = "/uploadPackage/{id}", notes = "修改ipa", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @PostMapping(value = "/uploadPackage/{id}")
    public ApiResult<String> updatePackage(@PathVariable(value = "id")Long id, @RequestPart(value = "ipaFile")MultipartFile file, @RequestParam("summary")String summary) {
        ApiResult<String> result = new ApiResult<>();
        try {
            IpaPackageBO ipaPackageBO = ipaPackageAssembly.assembleDeviceBO(id, file, summary);
            ipaPackageBSService.uploadIpa(ipaPackageBO);
            result.setCode(ApiResult.SUCCESS_CODE);
            result.setMsg(ApiResult.SUCCESS_MSG);
        }catch (ServiceException e){
            result.setMsg(e.getErrorMsg());
        }
        return result;
    }


    @GetMapping("/search")
    public String search(@RequestParam(value = "page") Integer currentPage, String name, Model model){

        Page<IpaPackagePO> page = ipaPackageBSService.selectIpaByCondition(currentPage, name);

        model.addAttribute("page", page);
        model.addAttribute("name", name);

        return "supersignature/ipa/index";
    }
}
