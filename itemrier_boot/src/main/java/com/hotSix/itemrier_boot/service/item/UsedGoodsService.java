package com.hotSix.itemrier_boot.service.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotSix.itemrier_boot.domain.category.Category;
import com.hotSix.itemrier_boot.domain.item.GroupPurchase;
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.item.UsedGoods;
import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.item.RequestDto;
import com.hotSix.itemrier_boot.dto.item.UsedGoodsDto;
import com.hotSix.itemrier_boot.repository.item.CategoryRepository;
import com.hotSix.itemrier_boot.repository.item.ItemInfoRepository;
import com.hotSix.itemrier_boot.repository.item.UsedGoodsRepository;
import com.hotSix.itemrier_boot.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsedGoodsService {
	private final UsedGoodsRepository usedGoodsRepository;
	
	private final UserRepository userRepository;
	
	private final CategoryRepository categoryRepository;
	
	private final ItemInfoRepository itemInfoRepository;
	
	public void save(RequestDto dto, int sellerId, MultipartFile file) throws Exception {	
		UserEntity seller = userRepository.findByUserId(sellerId);
		dto.setSeller(seller);
		
		// Test(임시)
//		UserEntity user = userRepository.getReferenceById(952);
//		dto.setSeller(user);
		
		int categoryId = Integer.parseInt(dto.getCatId());
		Category category = categoryRepository.getReferenceById(categoryId);
		dto.setCategory(category);
		
		dto.setStatus(ItemStatus.Available);
		
		// 파일 처리
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";
		UUID uuid = UUID.randomUUID();
		String fileName = uuid + "_" + file.getOriginalFilename();
		File saveFile = new File(projectPath, fileName);
		file.transferTo(saveFile);
		dto.setFileName(fileName);
		dto.setFilePath("/files/" + fileName);
		
		UsedGoods usedGoods = dto.toUsedGoods(dto);
		usedGoodsRepository.save(usedGoods);
	}
	
	public List<RequestDto> findAll() {
		List<UsedGoods> usedGoodsList = usedGoodsRepository.findAllByOrderByRegisterDateDesc();
		List<RequestDto> usedGoodsDtoList = new ArrayList<>();
		for (UsedGoods usedGoods: usedGoodsList) {
			usedGoodsDtoList.add(RequestDto.toUsedGoodsDto(usedGoods));
		}
		return usedGoodsDtoList;
	}
	
	public UsedGoodsDto findById(int itemId) {
		UsedGoods usedGoods = usedGoodsRepository.getReferenceById(itemId);
		UsedGoodsDto ugDto = UsedGoodsDto.toUsedGoodsDto(usedGoods);
		return ugDto;
	}
	
	public UsedGoodsDto update(UsedGoodsDto dto) {
		UsedGoods usedGoods = UsedGoods.toUpdateEntity(dto);
		usedGoodsRepository.save(usedGoods);
		
		return findById(dto.getItemId());
	}
	
	public void delete(int itemId) throws Exception {
		UsedGoods usedGoods = usedGoodsRepository.getReferenceById(itemId);
		String fileName= usedGoods.getFileName();
		if (fileName != null) {
			deleteFile(fileName);
		}
		usedGoodsRepository.deleteById(itemId);
		itemInfoRepository.deleteById(itemId);
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
