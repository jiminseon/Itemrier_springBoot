package com.hotSix.itemrier_boot.repository.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.item.UsedGoods;
import com.hotSix.itemrier_boot.domain.user.UserEntity;

public interface UsedGoodsRepository extends JpaRepository<UsedGoods, Integer> {

	List<UsedGoods> findBySellerAndStatus(UserEntity seller, ItemStatus status);

	List<UsedGoods> findByBuyerId(int buyerId);

	// 최신순으로 정렬
	// 최신 날짜 순으로 정렬
	List<UsedGoods> findAllByOrderByRegisterDateDesc();

	//상품이름 검색
	List<UsedGoods> findByItemNameContaining(String query);
}