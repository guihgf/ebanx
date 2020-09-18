package br.com.gfermino.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gfermino.exception.ResourceNotFoundException;
import br.com.gfermino.model.Account;
import br.com.gfermino.vo.AccountVo;

@Service
public class AccountService {
	
	private List<Account> accounts;
	public AccountService() {
		accounts=new  ArrayList<Account>();
	}
	
	public Account create(AccountVo vo ) {
		Account account=new Account();
		account.setId(vo.getDestination());
		account.deposit(vo.getAmount());
		
		this.accounts.add(account);
		
		return account;
	}
	
	public Account findById(int id) {
		return accounts.stream().filter(account->id==account.getId()).findFirst().orElseThrow(()->new ResourceNotFoundException("0"));
	}
	
	public Account deposit(AccountVo vo) {
		Account account;
		try {
			account= this.findById(vo.getDestination());
			account.deposit(vo.getAmount());
		}
		catch (ResourceNotFoundException e) {		
			//create account
			account=this.create(vo);
		}
		
		return account;
	}		
	
	public Account withdraw(AccountVo vo) {
		Account account= this.findById(vo.getOrigin());
		account.withdraw(vo.getAmount());
		return account;
	}
	
	public void transfer(AccountVo vo) {
		Account accountOrigin = this.withdraw(vo);
		Account accountDestination = this.deposit(vo);
				
		this.accounts.stream().filter(account->accountOrigin.getId()==account.getId()).forEach(account->account.setBalance(accountOrigin.getBalance()));
		
		this.accounts.stream().filter(account->accountDestination.getId()==account.getId()).forEach(account->account.setBalance(accountDestination.getBalance()));
		
	}
	
	
}
