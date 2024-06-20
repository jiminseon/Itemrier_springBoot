package com.hotSix.itemrier_boot.controller.item;

import com.hotSix.itemrier_boot.domain.item.UsedGoods;
import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.UsedGoodsDto;
import com.hotSix.itemrier_boot.repository.user.UserRepository;
//import com.hotSix.itemrier_boot.service.search.UsedGoodsSearchService;
import com.hotSix.itemrier_boot.service.item.UsedGoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/usedGoods")
public class UsedGoodsController {

	@Autowired
	public UsedGoodsController(UsedGoodsService usedGoodsService) {
		this.usedGoodsService = usedGoodsService;
	}
	@Autowired
	private UsedGoodsService usedGoodsService;

	@Autowired 
	private  UserRepository userRepository;

	@GetMapping("/create")
	public String saveForm() {
		return "item/usedGoods/createForm";
	}

	@PostMapping("/create")
	public String save(@AuthenticationPrincipal UserDetails userDetail, @ModelAttribute UsedGoodsDto dto) {
		UserEntity user = userRepository.findByEmail(userDetail.getUsername()); 
		dto.setSeller(user);

		// Test
		//		UserEntity user = userRepository.getReferenceById(952);
		//		dto.setSeller(user);
		//		
		//		Category category = new Category();
		//		category.setCatId(52);
		//		category.setCatName("책상");
		//		dto.setCategory(category);
		//
		dto.setRegisterDate(LocalDateTime.now());
		//		
		//		usedGoodsService.save(dto);
		//		System.out.println(dto.getItemId());

		return "redirect:item/usedGoods/detail";
	}

	@GetMapping("/view/{itemId}")
	public String findById(@PathVariable int itemId, Model model) {
		UsedGoodsDto ugDto = usedGoodsService.findById(itemId);
		model.addAttribute("usedGoods", ugDto);
		return "item/usedGoods/view";
	}

	@GetMapping("/update/{itemId}")
	public String updateForm(@PathVariable int itemId, Model model) {
		UsedGoodsDto ugDto = usedGoodsService.findById(itemId);
		model.addAttribute("usedGoods", ugDto);
		return "item/usedGoods/updateForm";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute UsedGoodsDto dto, Model model) {
		UsedGoodsDto ugDto = usedGoodsService.update(dto);
		model.addAttribute("usedGoods", ugDto);
		return "item/usedGoods/view";
	}

	@GetMapping("/delete/{itemId}")
	public String delete(@PathVariable int itemId) {
		usedGoodsService.delete(itemId);
		return "redirect:item/usedGoods/list";
	}

	//중고거래 상품 나열
	@GetMapping("/list")
	public String listUsedGoods(Model model) {
		List<UsedGoods> usedGoodsList = usedGoodsService.getAllUsedGoods();
		model.addAttribute("usedGoodsList", usedGoodsList);
		return "thymeleaf/item/usedGoods/list";
	}

	// 중고거래 상품 검색
    @GetMapping("/search")
    public String searchUsedGoods(@RequestParam("query") String query, Model model) {
        List<UsedGoodsDto> searchResults = usedGoodsService.searchUsedGoods(query);
        model.addAttribute("usedGoodsList", searchResults);
        return "thymeleaf/item/usedGoods/list";
    }
}
