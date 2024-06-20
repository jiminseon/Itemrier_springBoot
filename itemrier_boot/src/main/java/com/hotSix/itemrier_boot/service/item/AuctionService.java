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
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.AuctionDto;
import com.hotSix.itemrier_boot.repository.item.AuctionRepository;
import com.hotSix.itemrier_boot.repository.item.CategoryRepository;
import com.hotSix.itemrier_boot.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionService {	
	private final AuctionRepository auctionRepository;
	
	private final UserRepository userRepository;
	
	private final CategoryRepository categoryRepoistory;
	
	// 경매 상품 등록
	public void save(AuctionDto dto, int sellerId, MultipartFile file) throws Exception {
		UserEntity seller = userRepository.findByUserId(sellerId);
		dto.setSeller(seller);
		
		Category category = categoryRepoistory.getReferenceById(Integer.parseInt(dto.getCatId()));
		dto.setCategory(category);
		
		dto.setStatus(ItemStatus.Available);
		dto.setCurrentBid(dto.getStartPrice());
		
		// 파일 처리
		String fileName = saveFile(file);
		dto.setFileName(fileName);
		dto.setFilePath("/files/" + fileName);
		
		Auction auction = dto.toEntity(dto);
		auctionRepository.save(auction);
	}
	
	// 경매 상품 수정
	public AuctionDto update(AuctionDto dto) {
		Auction originalAuction = auctionRepository.getReferenceById(dto.getItemId());
		originalAuction.update(dto);
		
		auctionRepository.save(originalAuction);
		return findById(dto.getItemId());
	}
	
	// 경매 상품 삭제
	public void delete(int itemId) throws Exception {
		Auction auction = auctionRepository.getReferenceById(itemId);
		String fileName = auction.getFileName();
		if (fileName != null) {
			deleteFile(fileName);
		}
		auctionRepository.deleteById(itemId);
	}
	
	// 경매 상품 전체 목록 보기
	public List<AuctionDto> findAll() {
		List<Auction> auctionList = auctionRepository.findAllByOrderByStartTimeDesc();
		List<AuctionDto> auctionDtoList = new ArrayList<>();
		
		for (Auction auction: auctionList) {
			AuctionDto auctionDto = new AuctionDto();
			auctionDto.setItemId(auction.getItemId());
			auctionDto.setItemName(auction.getItemName());
			auctionDto.setCurrentBid(auction.getCurrentBid());
			auctionDto.setStatus(auction.getStatus());
			auctionDto.setFileName(auction.getFileName());
			auctionDto.setFilePath(auction.getFilePath());
			auctionDtoList.add(auctionDto);
		}
		return auctionDtoList;
	}
	
	// 경매 상품 상세보기
	public AuctionDto findById(int itemId) {
		Auction auction = auctionRepository.getReferenceById(itemId);
		AuctionDto auctionDto = AuctionDto.toDTO(auction);
		return auctionDto;
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
}
