package com.hotSix.itemrier_boot.dto.item;


import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.hotSix.itemrier_boot.domain.category.Category;
import com.hotSix.itemrier_boot.domain.item.GroupPurchase;
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Data
public class GroupPurchaseDto {
	private int itemId;
	
	@NotBlank(message = "상품명은 필수 입력 항목입니다.")
	private String itemName;
	
	@NotBlank(message = "상품 정보는 필수 입력 항목입니다.")
	private String description; // 상품 설명
	
	private Integer price; // 할인 된 가격
	
	@PositiveOrZero(message = "가격은 0 이상의 값이어야 합니다.")
	private Integer originalPrice; // 등록 가격 (할인 되기 전 가격)
	
	private ItemStatus status; // 거래 현황(판매 중, 판매 완료)
	
	private UserEntity seller;
	
	private UserEntity buyer; 
	
	private Category category;
	
	@NotNull(message = "카테고리는 필수 선택 항목입니다.")
	private String catId;
	
	@Positive(message = "최소 수량은 1개 이상의 값이어야 합니다.")
	private int minQuantity; // 최소수량
	
	private int totalQuantity; // 구매한 수량 총합
	
	private LocalDateTime startTime; // 시작시간(등록 날짜)
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endTime;
	
	private double discRate; // 할인율
	
	private Integer buyerId; // 구매자 아이디
	
	private String fileName; // 이미지 파일 이름
	
	private String filePath; // 이미지 파일 경로
	
	public GroupPurchase toGroupPurchase(GroupPurchaseDto groupPurchaseDto) {
		GroupPurchase groupPurchase = new GroupPurchase();
		groupPurchase.setItemId(groupPurchaseDto.getItemId());
		groupPurchase.setItemName(groupPurchaseDto.getItemName());
		groupPurchase.setDescription(groupPurchaseDto.getDescription());;
		groupPurchase.setPrice(groupPurchaseDto.getPrice());
		groupPurchase.setStatus(groupPurchaseDto.getStatus());
		groupPurchase.setSeller(groupPurchaseDto.getSeller());
		groupPurchase.setBuyer(groupPurchaseDto.getBuyer());
		groupPurchase.setCategory(groupPurchaseDto.getCategory());;
		groupPurchase.setMinQuantity(groupPurchaseDto.getMinQuantity());;
		groupPurchase.setTotalQuantity(groupPurchaseDto.getTotalQuantity());;
		groupPurchase.setStartTime(groupPurchaseDto.getStartTime());;
		groupPurchase.setEndTime(groupPurchaseDto.getEndTime());
		groupPurchase.setDiscRate(groupPurchaseDto.getDiscRate());
		groupPurchase.setBuyerId(groupPurchaseDto.getBuyerId());
		groupPurchase.setFileName(groupPurchaseDto.getFileName());
		groupPurchase.setFilePath(groupPurchaseDto.getFilePath());
		groupPurchase.setOriginalPrice(groupPurchaseDto.getOriginalPrice());
		return groupPurchase;		
	}
	
	public static GroupPurchaseDto toDTO(GroupPurchase gp) {
		GroupPurchaseDto dto = new GroupPurchaseDto();
		dto.setItemId(gp.getItemId());
		dto.setItemName(gp.getItemName());
		dto.setDescription(gp.getDescription());
		dto.setPrice(gp.getPrice());
		dto.setStatus(gp.getStatus());
		dto.setSeller(gp.getSeller());
		dto.setCategory(gp.getCategory());
		dto.setMinQuantity(gp.getMinQuantity());
		dto.setTotalQuantity(gp.getTotalQuantity());
		dto.setStartTime(gp.getStartTime());
		dto.setEndTime(gp.getEndTime());
		dto.setDiscRate(gp.getDiscRate());
		dto.setFileName(gp.getFileName());
		dto.setFilePath(gp.getFilePath());
		dto.setOriginalPrice(gp.getOriginalPrice());
		return dto;
	}
	
}
