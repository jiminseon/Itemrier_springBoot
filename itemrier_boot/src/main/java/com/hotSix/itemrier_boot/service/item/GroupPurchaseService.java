package com.hotSix.itemrier_boot.service.item;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.hotSix.itemrier_boot.domain.category.Category;
import com.hotSix.itemrier_boot.domain.item.Auction;
import com.hotSix.itemrier_boot.domain.item.GroupPurchase;
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.GroupPurchaseDto;
import com.hotSix.itemrier_boot.repository.item.CategoryRepository;
import com.hotSix.itemrier_boot.repository.item.GroupPurchaseRepository;
import com.hotSix.itemrier_boot.repository.item.ItemInfoRepository;
import com.hotSix.itemrier_boot.repository.order.OrderItemRepository;
import com.hotSix.itemrier_boot.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupPurchaseService {
	
	private final GroupPurchaseRepository groupPurchaseRepository;
	
	private final UserRepository userRepository;
	
	private final CategoryRepository categoryRepository;
	
	private final ItemInfoRepository itemInfoRepository;
	
	private final OrderItemRepository orderItemRepository;
	
	// 경매 상품 등록
	public void save(GroupPurchaseDto dto, int sellerId, MultipartFile file) throws Exception {
		UserEntity seller = userRepository.findByUserId(sellerId);
		dto.setSeller(seller);
		
		Category category = categoryRepository.getReferenceById(Integer.parseInt(dto.getCatId()));
		dto.setCategory(category);
		
		dto.setStatus(ItemStatus.Available);
		
		// 할인율 적용한 가격 구하기
		int discountedPrice = calculateDiscountedPrice(dto);
		dto.setPrice(discountedPrice);
		
		// 파일 처리
		String fileName = saveFile(file);
		dto.setFileName(fileName);
		dto.setFilePath("/files/" + fileName);
		
		GroupPurchase groupPurchase = dto.toGroupPurchase(dto);
		groupPurchaseRepository.save(groupPurchase);
	}
	
	// 경매 상품 수정
	public GroupPurchaseDto update(GroupPurchaseDto dto) {
		GroupPurchase originalGroupPurchase = groupPurchaseRepository.getReferenceById(dto.getItemId());
		
		int discountedPrice = calculateDiscountedPrice(dto);
		dto.setPrice(discountedPrice);
		
		originalGroupPurchase.update(dto);
		
		groupPurchaseRepository.save(originalGroupPurchase);
		return findById(dto.getItemId());
	}
	
	// 경매 상품 삭제
	public void delete(int itemId) throws Exception {
		GroupPurchase groupPurchase = groupPurchaseRepository.getReferenceById(itemId);
		String fileName= groupPurchase.getFileName();
		if (fileName != null) {
			deleteFile(fileName);
		}
		groupPurchaseRepository.deleteById(itemId);
		itemInfoRepository.deleteById(itemId);
	}
	
	// 공동구매 상품 전체 목록 보기
	public List<GroupPurchaseDto> findAll() {
		List<GroupPurchase> gpList = groupPurchaseRepository.findAllByOrderByStartTimeDesc();
		List<GroupPurchaseDto> gpDtoList = new ArrayList<>();
		
		for (GroupPurchase gp: gpList) {
			GroupPurchaseDto gpDto = new GroupPurchaseDto();
			gpDto.setItemId(gp.getItemId());
			gpDto.setItemName(gp.getItemName());
			gpDto.setPrice(gp.getPrice()); // 이 부분 할인율 적용된 걸로 고쳐야 함
			gpDto.setStatus(gp.getStatus());
			gpDto.setFileName(gp.getFileName());
			gpDto.setFilePath(gp.getFilePath());
			gpDtoList.add(gpDto);
		}
		return gpDtoList;
	}
	
	// 공동구매 상품 상세보기
	public GroupPurchaseDto findById(int itemId) {
		GroupPurchase groupPurchase = groupPurchaseRepository.getReferenceById(itemId);
		GroupPurchaseDto gpDto = GroupPurchaseDto.toDTO(groupPurchase);
		return gpDto;
	}
	
	// 유효성 체크
	public Map<String, String> validateHandling(BindingResult result) {
		Map<String, String> validatorResult = new HashMap<>();
		for (FieldError error: result.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		return validatorResult;
	}
	
	// 파일 처리
	public String saveFile(MultipartFile file) throws Exception {
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File saveFile = new File(projectPath, fileName);
		file.transferTo(saveFile);
		return fileName;
	}
	
	// 파일 삭제
	public void deleteFile(String fileName) throws Exception {
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
        File file = new File(projectPath, fileName);
        if (file.exists()) {
        	if (!file.delete()) {
                throw new Exception("Failed to delete file: " + fileName);
            } 
        }
	}
	
	// 할인율 적용하기
	public int calculateDiscountedPrice(GroupPurchaseDto dto) {
		int originalPrice = dto.getOriginalPrice();
		double discRate = dto.getDiscRate();
		int discountedPrice = (int) (originalPrice * (1 - discRate * 0.01));
		return discountedPrice;
	}
	
	// 현재판매수량 업데이트
	public void updateCurrentQuantity(int itemId) {
		int count = orderItemRepository.countByItemId(itemId);
		GroupPurchase gp = groupPurchaseRepository.getReferenceById(itemId);
		gp.setTotalQuantity(count);
	}
}
