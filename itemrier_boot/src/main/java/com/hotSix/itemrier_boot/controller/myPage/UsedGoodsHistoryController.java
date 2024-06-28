package com.hotSix.itemrier_boot.controller.myPage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.UsedGoodsDto;
import com.hotSix.itemrier_boot.repository.user.UserRepository;
import com.hotSix.itemrier_boot.service.item.UsedGoodsService;
import com.hotSix.itemrier_boot.service.myPage.UsedGoodsHistoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/myPage")
public class UsedGoodsHistoryController {
	@Autowired
	private UsedGoodsService usedGoodsBuyerService;
	private final UsedGoodsHistoryService usedGoodsService;
	private final UserRepository userRepository;

	// 중고거래 판매,예약 상품내역
	@GetMapping("/usedGoodsTransaction/inProgress")
	public String getUsedGoodsInProgress(@AuthenticationPrincipal UserDetails userDetail, Model model) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername());
		int userId = user.getUserId(); // 로그인한 계정의 PK값
		Boolean status = true;

		List<UsedGoodsDto> userGoodsList = usedGoodsService.getUsedGoodsStatusSearch(userId, status);
		model.addAttribute("userGoodsList", userGoodsList);
		for (int i = 0; i < userGoodsList.size(); i++) {
			System.out.println(userGoodsList.get(i));
		}
		return "myPage/usedGoods/usedGoodsInProgress";
	}

	// 중고거래 판매완료된 상품내역
	@GetMapping("/usedGoodsTransaction/ended")
	public String getUsedGoodsEnded(@AuthenticationPrincipal UserDetails userDetail, Model model) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername());
		int userId = user.getUserId(); // 로그인한 계정의 PK값
		Boolean status = false;

		List<UsedGoodsDto> userGoodsList = usedGoodsService.getUsedGoodsStatusSearch(userId, status);
		System.out.println("usedGoods" + userGoodsList);
		model.addAttribute("userGoodsList", userGoodsList);

		return "myPage/usedGoods/usedGoodsEnded";
	}

	// 구매내역 조회
	@GetMapping("/usedGoodsTransactionHistory")
	public String getUsedGoodsBuyHistory(@AuthenticationPrincipal UserDetails userDetail, Model model) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername());
		int userId = user.getUserId(); // 로그인한 계정의 PK값

		List<UsedGoodsDto> userGoodsList = usedGoodsService.getUsedGoodsBuyHistory(userId);
		System.out.println("usedGoods" + userGoodsList);
		model.addAttribute("usedGoods", userGoodsList);

		return "myPage/usedGoods/purchasedUsedGoods";
	}

	// 중고거래 구매자 확정 폼
	@GetMapping("/usedGoodsBuyerForm")
	public String viewWriteBuyerForm(@AuthenticationPrincipal UserDetails userDetail,
			@RequestParam("itemId") int itemId, Model model) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername());
		int sellerId = user.getUserId();

		model.addAttribute("sellerId", sellerId);
		model.addAttribute("itemId", itemId);
		return "myPage/usedGoods/writeBuyerForm";
	}

	// 중고거래 구매자 확정
	@PostMapping("/usedGoodTransaction/insertBuyer")
	public String insertBuyerId(@RequestParam("itemId") int itemId, @RequestParam("buyerId") Integer buyerId, Model model) {
		boolean rslt = userRepository.existsById(buyerId);
		String message="";
		if(rslt==true) {
			UsedGoodsDto ugDto = usedGoodsBuyerService.findById(itemId);
			ugDto.setBuyerId(buyerId);
			ugDto.setStatus(ItemStatus.Complete);
			System.out.println("buyerId : "+ buyerId);
			UsedGoodsDto result = usedGoodsBuyerService.update(ugDto);
			System.out.println(result);
			message="완료되었습니다.";
		}else {
			message="존재하지 않는 사용자입니다.";
		}
		model.addAttribute("message", message);
		return "myPage/usedGoods/completeResult";
	}
}
