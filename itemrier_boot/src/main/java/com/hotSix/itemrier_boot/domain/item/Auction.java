package com.hotSix.itemrier_boot.domain.item;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.hotSix.itemrier_boot.dto.item.AuctionDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter                  
@Setter
@ToString                       
@Table(name = "AUCTION")
@DiscriminatorValue("auction")
@EntityListeners(AuditingEntityListener.class)
public class Auction extends ItemInfo {
    @Column(nullable = false)
    private String condition; // 상품 상태
    
    @Column(nullable = false)
    private String contactType; // 연락 수단
    
    @Column(nullable = false)
    private int startPrice; // 시작가
    
    private int currentBid; // 현재 최고가
    
    @Column(updatable = false)
    @CreatedDate                                        
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime startTime; // 시작시간(등록 날짜)
    
    @Column(nullable = false)
    private LocalDateTime endTime; // 종료시간
        
    private int buyerId; // 구매자 아이디
    
    private String fileName; // 이미지 파일 이름
    
    private String filePath; // 이미지 파일 경로
    
    private Integer winner; // 낙찰자
    
    public void update(AuctionDto dto) {
    	this.setItemName(dto.getItemName());
    	this.setDescription(dto.getDescription());
    	this.setCondition(dto.getCondition());
    	this.setContactType(dto.getContactType());
    	this.setPrice(dto.getPrice());
    	this.setStartPrice(dto.getStartPrice());
    	this.setCategory(dto.getCategory());
    	this.setStatus(dto.getStatus());
    	this.setEndTime(dto.getEndTime());
    }
    public static AuctionDto toDTO(Auction auction) {
    	AuctionDto dto = new AuctionDto();
    	dto.setItemId(auction.getItemId());
    	dto.setItemName(auction.getItemName());
    	dto.setDescription(auction.getDescription());
    	dto.setCondition(auction.getCondition());
    	dto.setContactType(auction.getContactType());
    	dto.setStartPrice(auction.getStartPrice());
    	dto.setStatus(auction.getStatus());
    	dto.setSeller(auction.getSeller());
    	dto.setCategory(auction.getCategory());
    	dto.setCurrentBid(auction.getCurrentBid());
    	dto.setStartTime(auction.getStartTime());
    	dto.setEndTime(auction.getEndTime());
    	dto.setWinner(auction.getWinner());
    	dto.setFileName(auction.getFileName());
    	dto.setFilePath(auction.getFilePath());
    	return dto;
    }
}
