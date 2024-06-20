package com.hotSix.itemrier_boot.controller.item;

import com.hotSix.itemrier_boot.domain.category.Category;
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.RequestDto;
import com.hotSix.itemrier_boot.dto.item.UsedGoodsDto;
import com.hotSix.itemrier_boot.repository.item.CategoryRepository;
import com.hotSix.itemrier_boot.repository.user.UserRepository;
//import com.hotSix.itemrier_boot.service.search.UsedGoodsSearchService;
import com.hotSix.itemrier_boot.service.item.UsedGoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyEditorSupport;
import java.util.List;

@Controller
@RequestMapping("/usedGoods")
public class UsedGoodsController {

//    @Autowired
//    private UsedGoodsSearchService usedGoodsSearchService;
//
//    @GetMapping("/search")
//    public List<UsedGoods> searchUsedGoods(@RequestParam("keyword") String keyword) {
//        return usedGoodsSearchService.searchUsedGoods(keyword);
//    }
	
	@Autowired
	private UsedGoodsService usedGoodsService;
	
	@Autowired 
	private  UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ItemStatus.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
            	System.out.println("setAsText(): text = " + text);
                setValue(ItemStatus.valueOf(text));
            }
        });
    }
	
	// 상품 등록 폼
	@GetMapping("/create") 
	public String saveForm() {
		return "item/usedGoods/createForm";
	}
	
	
	// 상품 등록
	@PostMapping("/create")
	public String save(@AuthenticationPrincipal UserDetails userDetail, @ModelAttribute RequestDto dto, MultipartFile file) throws Exception{
		UserEntity user = userRepository.findByEmail(userDetail.getUsername());
		int sellerId = user.getUserId();
//		int sellerId = 1052; // Test용
		usedGoodsService.save(dto, sellerId, file);
		return "redirect:list";
	}
	
	// 상품 목록 보기
	@GetMapping("/list")
	public String findAll(Model model) {
		List<RequestDto> ugDtoList = usedGoodsService.findAll();
		model.addAttribute("ugList", ugDtoList);
		return "thymeleaf/item/usedGoods/list";
	}
	
	// 상품 상세보기
	@GetMapping("/view/{itemId}")
	public String findById(@PathVariable int itemId, Model model, @AuthenticationPrincipal UserDetails userDetail) {
		UsedGoodsDto ugDto = usedGoodsService.findById(itemId);
		model.addAttribute("usedGoods", ugDto);
		
		UserEntity user = userRepository.findByEmail(userDetail.getUsername());
		int sellerId = user.getUserId();
		model.addAttribute("loginUserId", sellerId);
		
		return "thymeleaf/item/usedGoods/view";
	}
	
	@GetMapping("/update/{itemId}")
	public String updateForm(@PathVariable int itemId, Model model) {
		UsedGoodsDto ugDto = usedGoodsService.findById(itemId);
		List<Category> category = categoryRepository.findAll();
		model.addAttribute("usedGoods", ugDto);
		model.addAttribute("category", category);
		return "thymeleaf/item/usedGoods/updateForm";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute UsedGoodsDto dto, @RequestParam("sellerId") int sellerId, Model model) {
		UserEntity seller = userRepository.getReferenceById(sellerId);
		dto.setSeller(seller);
		
		// 현황 변경
		if (dto.getStrStatus() != null) {
			if (dto.getStrStatus().equals("Available")) {
				dto.setStatus(ItemStatus.Available);
			} else if (dto.getStrStatus().equals("Complete")) {
				dto.setStatus(ItemStatus.Complete);
			}
		}
		
		UsedGoodsDto ugDto = usedGoodsService.update(dto);
		model.addAttribute("usedGoods", ugDto);
		return "redirect:view/" + dto.getItemId();
	}
	
	@GetMapping("/delete/{itemId}")
	public String delete(@PathVariable int itemId) throws Exception{
		usedGoodsService.delete(itemId);
		return "redirect:/usedGoods/list";
	}
}
