package com.naoh.iossupersign.controller;

import com.naoh.iossupersign.base.ApiResult;
import com.naoh.iossupersign.model.po.MemberPO;
import com.naoh.iossupersign.service.member.MemberBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberBSService memberBSService;

	@RequestMapping("/index")
	public String index(MemberPO memberPO, Model model) {
		List<MemberPO> list =  memberBSService.selectMemberByCondition(memberPO);
		model.addAttribute("members", list);
		return "member/index";
	}

	@ResponseBody
	@PostMapping("/create")
	public ApiResult<Void> createMember(MemberPO memberPO) {
		ApiResult<Void> result = new ApiResult<Void>();
		memberBSService.create(memberPO);
		result.setCode(ApiResult.SUCCESS_CODE);
		result.setMsg("帐号建立成功,画面将于3秒后刷新");
		return result;
	}

	@ResponseBody
	@PostMapping("/updateMember")
	public ApiResult<Void> updateMember(MemberPO memberPO) {
		ApiResult<Void> result = new ApiResult<Void>();
		memberBSService.update(memberPO);
		result.setCode(ApiResult.SUCCESS_CODE);
		result.setMsg("修改成功,画面将于3秒后刷新");
		return result;
	}
}
