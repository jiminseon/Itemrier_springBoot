package com.hotSix.itemrier_boot.repository.item;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotSix.itemrier_boot.domain.item.Auction;
import com.hotSix.itemrier_boot.domain.item.GroupPurchase;
import com.hotSix.itemrier_boot.domain.item.ItemStatus;
import com.hotSix.itemrier_boot.domain.user.UserEntity;

public interface AuctionRepository extends JpaRepository<Auction, Integer>{
	// 사용자가 상태 변경(판매 완료 / 판매 중)
	@Modifying
	@Query("update Auction a set a.status=:status where a.itemId=:itemId")
	int updateAuctionStatus(@Param("itemId") int itemId, @Param("status") ItemStatus status);
	
	// 최신 날짜 순으로 정렬
	List<Auction> findAllByOrderByStartTimeDesc();
	
	// 마감시간 지난 입찰 내역 리스트
	List<Auction> findAllByEndTimeBeforeAndStatus(LocalDateTime endTime, ItemStatus status);
	
	List<Auction> findBySellerAndStatus(UserEntity seller, ItemStatus status);
}
