package com.hotSix.itemrier_boot.controller.myPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class myPageController {
	
	@GetMapping("/myPage")
	public String showMyPage() {
		return "myPage/myPage";
	}
	
	@GetMapping("/myPage/delete")
	public String deleteProfile() {
		
		return "myPage/profile/deleteForm";
	}
}
