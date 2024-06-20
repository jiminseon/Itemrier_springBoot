package com.hotSix.itemrier_boot.service.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hotSix.itemrier_boot.domain.item.UsedGoods;
import com.hotSix.itemrier_boot.dto.item.UsedGoodsDto;
import com.hotSix.itemrier_boot.repository.item.UsedGoodsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsedGoodsService {
	private final UsedGoodsRepository usedGoodsRepository;
	
	public void save(UsedGoodsDto dto) {	
		UsedGoods usedGoods = dto.toUsedGoods(dto);
		usedGoodsRepository.save(usedGoods);
	}
	
	public UsedGoodsDto findById(int itemId) {
		UsedGoods usedGoods = usedGoodsRepository.getReferenceById(itemId);
		UsedGoodsDto ugDto = usedGoods.toUsedGoodsDto(usedGoods);
		return ugDto;
	}
	
	public UsedGoodsDto update(UsedGoodsDto dto) {
		UsedGoods usedGoods = UsedGoods.toUpdateEntity(dto);
		usedGoodsRepository.save(usedGoods);
		return findById(dto.getItemId());
	}
	
	public void delete(int itemId) {
		usedGoodsRepository.deleteById(itemId);
	}
	public List<UsedGoods> getAllUsedGoods() {
        return usedGoodsRepository.findAll();
    }
	
	// 중고거래 상품 검색
    public List<UsedGoodsDto> searchUsedGoods(String query) {
        List<UsedGoods> searchResults = usedGoodsRepository.findByItemNameContaining(query);
        return searchResults.stream()
                .map(UsedGoodsDto::toUsedGoodsDto)
                .collect(Collectors.toList());
    }
}
