package com.hotSix.itemrier_boot.controller.myPage;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.GroupPurchaseDto;
import com.hotSix.itemrier_boot.repository.user.UserRepository;
import com.hotSix.itemrier_boot.service.myPage.GroupPurchaseHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/myPage")
public class GroupPurchaseHistoryController {
	
	private final GroupPurchaseHistoryService groupPurchaseService;
	private final UserRepository userRepository;
	
	//공동구매 판매 중인 상품내역
	@GetMapping("/groupPurchase/inProgress")
	public List<GroupPurchaseDto> getUsedGoodsInProgress(@AuthenticationPrincipal UserDetails userDetail, Model model) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername()); 
		int userId = user.getUserId(); //로그인한 계정의 PK값
		Boolean status = true;
		
		List<GroupPurchaseDto> groupPurchaseList = groupPurchaseService.getGroupPurchaseStatusSearch(userId, status);
		System.out.println("groupPurchase" + groupPurchaseList);
		model.addAttribute(groupPurchaseList);
		
		return groupPurchaseList;
	}
	
	//공동구매 판매완료된 상품내역
	@GetMapping("/groupPurchase/ended")
	public List<GroupPurchaseDto> getUsedGoodsEnded(@AuthenticationPrincipal UserDetails userDetail, Model model) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername()); 
		int userId = user.getUserId(); //로그인한 계정의 PK값
		Boolean status = false;
		
		List<GroupPurchaseDto> groupPurchaseList = groupPurchaseService.getGroupPurchaseStatusSearch(userId, status);
		System.out.println("groupPurchase" + groupPurchaseList);
		model.addAttribute(groupPurchaseList);
		
		return groupPurchaseList;
	}
	
}
