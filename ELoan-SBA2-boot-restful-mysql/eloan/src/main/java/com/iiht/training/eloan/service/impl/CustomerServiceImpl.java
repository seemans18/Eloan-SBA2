package com.iiht.training.eloan.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.exception.InvalidDataException;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.AdminService;
import com.iiht.training.eloan.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository pProcessingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public UserDto register(UserDto userDto) {
		if(adminService.isValidUserDetails(userDto)) {
			Users user = adminService.convertToUserEntity(userDto);
			user.setRole("Customer");
			usersRepository.save(user);
			userDto.setId(user.getId());
		}
		return userDto;
	}

	@Override
	public LoanOutputDto applyLoan(Long customerId, LoanDto loanDto) {
		if(isValidLoanDetails(loanDto)) {
			
		}
		return null;
	}

	@Override
	public LoanOutputDto getStatus(Long loanAppId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LoanOutputDto> getStatusAll(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isValidLoanDetails(LoanDto loanDto) {
		boolean isValid = true;
		List<String> errMsgs = new ArrayList<>();
		
		if(loanDto!=null) {
			String loanName = loanDto.getLoanName();
			if(!(loanName != null && loanName.length()>=3 && loanName.length()<=100)) {
				isValid=false;
				errMsgs.add("Loan name cannot be left blank and must be of length between 3 and 100 characters");
			}
			
			Double loanAmount = loanDto.getLoanAmount();
			if(!(loanAmount != null && loanAmount != 0)) {
				isValid=false;
				errMsgs.add("Loan amount cannot be left blank and must not be zero");
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
}
