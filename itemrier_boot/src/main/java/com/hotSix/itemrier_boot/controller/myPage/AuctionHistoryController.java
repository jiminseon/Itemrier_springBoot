//package com.hotSix.itemrier_boot.controller.myPage;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//import com.hotSix.itemrier_boot.domain.user.UserEntity;
//import com.hotSix.itemrier_boot.dto.item.AuctionDto;
//import com.hotSix.itemrier_boot.repository.user.UserRepository;
//import com.hotSix.itemrier_boot.service.myPage.AuctionHistoryService;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping(value = "/myPage")
//public class AuctionHistoryController {
//	private final AuctionHistoryService auctionHistoryService;
//	private final UserRepository userRepository;
//	
//	//경매 판매 중인 상품내역
//	@GetMapping("/auction/inProgress")
//	public String getUsedGoodsInProgress(@AuthenticationPrincipal UserDetails userDetail, Model model) {
//		UserEntity user = userRepository.findByEmail(userDetail.getUsername()); 
//		int userId = user.getUserId(); //로그인한 계정의 PK값
//		Boolean status = true;
//		
//		List<AuctionDto> auctionList = auctionHistoryService.getAuctionsProgressSearch(userId, status);
//		System.out.println("auctionList" + auctionList);
//		model.addAttribute("auctionList", auctionList);
//		
//		return "myPage/auction/auctionInProgress";
//	}
//	
//	//경매 판매완료된 상품내역
//	@GetMapping("/auction/ended")
//	public String getUsedGoodsEnded(@AuthenticationPrincipal UserDetails userDetail, Model model) {
//		UserEntity user = userRepository.findByEmail(userDetail.getUsername()); 
//		int userId = user.getUserId(); //로그인한 계정의 PK값
//		Boolean status = false;
//		
//		HashMap<AuctionDto, String> auctionList = auctionHistoryService.getAuctionsEndedSearch(userId, status);
//		System.out.println("auctionList" + auctionList);
//		model.addAttribute("auctionList", auctionList);
//		
//		return "myPage/auction/auctionEnded";
//	}
//}
