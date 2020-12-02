package com.iiht.training.eloan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public UserDto registerClerk(UserDto userDto) throws InvalidDataException {
		if(isValidUserDetails(userDto)) {
			Users user = convertToUserEntity(userDto);
			user.setRole("Clerk");
			usersRepository.save(user);
			userDto.setId(user.getId());
			//UserDto userDtoSaved = convertToUserDto(userSaved);
		}
		return userDto;
	}

	@Override
	public UserDto registerManager(UserDto userDto) throws InvalidDataException{
		if(isValidUserDetails(userDto)) {
			Users user = convertToUserEntity(userDto);
			user.setRole("Manager");
			usersRepository.save(user);
			userDto.setId(user.getId());
		}
		return userDto;
	}

	@Override
	public List<UserDto> getAllClerks() {
		List<Users> users = usersRepository.findAllByRole("Clerk");
		
		List<UserDto> usersDto = new ArrayList<>(); 
		for(Users u:users) {
			usersDto.add(convertToUserDto(u));
		}
		return usersDto;
	}

	@Override
	public List<UserDto> getAllManagers() {
		List<Users> users = usersRepository.findAllByRole("Manager");
		
		List<UserDto> usersDto = new ArrayList<>(); 
		for(Users u:users) {
			usersDto.add(convertToUserDto(u));
		}
		return usersDto;
	}

	@Override
	public boolean isValidUserDetails(UserDto userDto) throws InvalidDataException {
		boolean isValid = true;
		List<String> errMsgs = new ArrayList<>();
		
		if(userDto!=null) {
			String firstName = userDto.getFirstName();
			if(!(firstName != null && firstName.length()>=3 && firstName.length()<=100)) {
				isValid=false;
				errMsgs.add("First name cannot be left blank and must be of length between 3 and 100 characters");
			}
			
			String lastName = userDto.getLastName();
			if(!(lastName != null && lastName.length()>=3 && lastName.length()<=100)) {
				isValid=false;
				errMsgs.add("Last name cannot be left blank and must be of length between 3 and 100 characters");
			}
			
			String email = userDto.getEmail();
			if(!(email != null && email.length()>=3 && email.length()<=100 && email.matches("[a-zA-Z][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"))) {
				isValid=false;
				errMsgs.add("Email cannot be left blank and must be of length between 3 and 100 characters with proper format");
			}
			
			String mobileNumber = userDto.getMobile();
			if(!(mobileNumber != null && mobileNumber.length()==10)) {
				isValid=false;
				errMsgs.add("Mobile Number cannot be left blank and must be of length 10 characters");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new InvalidDataException("Invalid details: "+errMsgs);
				}
			}else {
				isValid=false;
				throw new InvalidDataException("Details are not supplied");
			}

		return isValid;	
	}
	
	@Override
	public Users convertToUserEntity(UserDto userDto) {
		
		Users user = new Users();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setMobile(userDto.getMobile());
		return user;	
	}
	
	@Override
	public UserDto convertToUserDto(Users user) {
		
		UserDto userDto = new UserDto();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
		userDto.setId(user.getId());
		return userDto;	
	}
}
