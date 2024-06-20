//package com.hotSix.itemrier_boot.service.myPage;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.hotSix.itemrier_boot.domain.item.Auction;
//import com.hotSix.itemrier_boot.domain.item.ItemStatus;
//import com.hotSix.itemrier_boot.domain.item.UsedGoods;
//import com.hotSix.itemrier_boot.domain.user.UserEntity;
//import com.hotSix.itemrier_boot.dto.item.AuctionDto;
//import com.hotSix.itemrier_boot.dto.item.UsedGoodsDto;
//import com.hotSix.itemrier_boot.repository.item.AuctionRepository;
//import com.hotSix.itemrier_boot.repository.user.UserRepository;
//
//import lombok.RequiredArgsConstructor;
//@Service
//@RequiredArgsConstructor
//public class AuctionHistoryService {
//	private final AuctionRepository auctionRepository;
//	private final UserRepository userRepository;
//	
//	public List<AuctionDto> getAuctionsProgressSearch(int sellerId, Boolean status) {  // 공동 거래 판매 중 or 예약 중/판매완료된 내역 조회
//		List<Auction> auctions;
//
//		UserEntity seller = userRepository.findByUserId(sellerId);
//		
//		auctions = auctionRepository.findBySellerAndStatus(seller, ItemStatus.Available);
//		
//        List<AuctionDto> auctionDtoList = new ArrayList<>();
//        for (Auction auction : auctions) {
//        	AuctionDto auctionDto = auction.toDTO(auction);
//        	auctionDtoList.add(auctionDto);
//        }
//        System.out.println("판매중" + auctionDtoList);
//        return auctionDtoList;
//	}
//	
//	public HashMap<AuctionDto, String> getAuctionsEndedSearch(int sellerId, Boolean status) {  // 공동 거래 판매 중 or 예약 중/판매완료된 내역 조회
//		List<Auction> auctions;
//		HashMap<AuctionDto, String> auctionList = new HashMap<AuctionDto, String>();
//		UserEntity seller = userRepository.findByUserId(sellerId);
//
//		auctions = auctionRepository.findBySellerAndStatus(seller, ItemStatus.Complete);
//		
//        List<AuctionDto> auctionDtoList = new ArrayList<>();
//        for (Auction auction : auctions) {
//        	AuctionDto auctionDto = auction.toDTO(auction);
//        	auctionDtoList.add(auctionDto);
//        }
//        System.out.println("판매완료" + auctionDtoList);
//        
//		for (AuctionDto auction : auctionDtoList) {
//			if (auction.getWinner() == null) {
//				throw new NullPointerException();
//			}
//			UserEntity winner = userRepository.findByUserId(auction.getWinner());
//			auctionList.put(auction, winner.getNickname());
//		}
//        
//        return auctionList;
//	}
//	
//}
