package com.hotSix.itemrier_boot.service.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.hotSix.itemrier_boot.domain.user.UserEntity;
import com.hotSix.itemrier_boot.dto.user.ProfileDto;
import com.hotSix.itemrier_boot.dto.user.UserDto;
import com.hotSix.itemrier_boot.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public UserDto insertUser(UserDto userDto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); 
		userRepository.save(userDto.toUserEntity(userDto));
		return userDto;
	}
	
	public int emailCheck(String email) {
		int cnt = userRepository.countByEmail(email);
		System.out.println("cnt: " + cnt);
		return cnt;
	}	
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {// /user/login 자동실행
        UserEntity user = userRepository.findByEmail(email);
        System.out.println("이밍ㄹ과 유저" + user);
        if(user != null){
            return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
        }

        return null;
    }
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    
    public void updateUser(UserEntity user, ProfileDto profileDto) {
		System.out.println("t수정");
		user.setName(profileDto.getName());
		user.setNickname(profileDto.getNickname());
		user.setPhoneNum(profileDto.getPhoneNum());
		user.setZipcode(profileDto.getZipcode());
		user.setAddDetail(profileDto.getAddDetail());
		user.setAddStreet(profileDto.getAddStreet());
    	userRepository.save(user);
    }
    
    public boolean deleteUser(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);

        if (passwordEncoder.matches(password, user.getPassword())) {
        	userRepository.delete(user);
            return true;
        } else {
            return false;
        }	
    }
    
    public boolean findUserId(int userId) {
    	Boolean rslt = userRepository.existsById(userId);
    	return rslt;
    }
}
