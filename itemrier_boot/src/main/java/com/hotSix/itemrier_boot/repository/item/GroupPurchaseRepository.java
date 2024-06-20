package com.hotSix.itemrier_boot.repository.item;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotSix.itemrier_boot.domain.item.GroupPurchase;
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;

public interface GroupPurchaseRepository extends JpaRepository<GroupPurchase, Integer> {
	List<GroupPurchase> findBySellerAndStatus(UserEntity seller, ItemStatus status);
	
	// 최신 날짜 순으로 정렬
	List<GroupPurchase> findAllByOrderByStartTimeDesc();
	
	// 마감시간 지난 공동구매 내역 리스트
	List<GroupPurchase> findAllByEndTimeBeforeAndStatus(LocalDateTime endTime, ItemStatus status);
}
