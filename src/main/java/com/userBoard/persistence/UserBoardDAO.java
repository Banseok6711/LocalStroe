package com.userBoard.persistence;

import java.util.List;

import com.userBoard.domain.UserBoardVO;
import com.userBoard.paging.Criteria;

public interface UserBoardDAO {
	
	public void write(UserBoardVO boardVO)throws Exception;
	
	public UserBoardVO read(int bno)throws Exception;
	
	public void edit(UserBoardVO boardVO)throws Exception;
	
	public void delete(int bno)throws Exception;
	
	public List<UserBoardVO> list()throws Exception;
	
	public List<UserBoardVO> listPage(int page)throws Exception;
	
	public List<UserBoardVO> listCriteria(Criteria cri)throws Exception;

}
