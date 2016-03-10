package com.userBoard.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.user.domain.UserVO;
import com.user.service.UserService;
import com.userBoard.domain.UserBoardVO;
import com.userBoard.paging.Criteria;
import com.userBoard.paging.PageMaker;
import com.userBoard.service.UserBoardService;


@Controller
@RequestMapping("/userBoard/*")
public class UserBoardController {
	
	private static Logger logger = LoggerFactory.getLogger(UserBoardController.class);
	
	@Inject	
	private UserService userService;
	
	@Inject
	private UserBoardService userBoardService;
	
	@RequestMapping(value="/write" ,method=RequestMethod.GET)
	public String write(Model model , UserBoardVO vo , HttpSession session)throws Exception{
		
		UserVO user=(UserVO)session.getAttribute("login");
		
		// session is valid
		if( user != null ){
			UserVO userVO =userService.read(user.getUserid());
			model.addAttribute("userVO",userVO);
			
			System.out.println("userid:"+user.getUserid());
		}else{
			
		}
		//session is invalid		
		
		return "userBoard/userBoardRegister";
	}
	
	@RequestMapping(value="/write" , method=RequestMethod.POST)
	public String write(Model model , HttpSession session, UserBoardVO boardVO )throws Exception{
		
		UserVO vo =(UserVO)session.getAttribute("login");
		
		boardVO.setUserid(vo.getUserid());
		
		userBoardService.write(boardVO);		
		
		UserVO userVO = userService.read(boardVO.getUserid());
		
		logger.info(userVO.toString());
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("userVO", userVO);
		
		return "userBoard/userBoardInfo";
	}
	
	@RequestMapping(value="/read" , method=RequestMethod.GET)
	public String read(Model model , String bno , HttpSession session)throws Exception{
		
		int bnoNumber = Integer.parseInt(bno);
		
		UserBoardVO boardVO= userBoardService.read(bnoNumber);
		
		UserVO userVO = userService.read(boardVO.getUserid());
				
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("userVO", userVO);
		
		return "userBoard/userBoardInfo";
	}
	
	@RequestMapping(value="/boardList" , method=RequestMethod.GET)
	public String list(Model model)throws Exception{
		
		List<UserBoardVO> list= userBoardService.list();
		
		model.addAttribute("boardList", list);
		
		return "userBoard/userBoardList";
	}
	
	
	@RequestMapping(value="/userBoardDelete" , method=RequestMethod.GET)
	public String delete(String bno)throws Exception{
		
		int bnoNumber = Integer.parseInt(bno);
		
		userBoardService.delete(bnoNumber);
		
		
		return "redirect:/userBoard/boardList";
	}
	
	@RequestMapping(value="/userBoardEdit" , method=RequestMethod.POST)
	public String edit(UserBoardVO boardVO , Model model , String username)throws Exception{
		
		
		System.out.println("boardVO:"+boardVO.toString());
		
		UserBoardVO vo= userBoardService.read(boardVO.getBno());
		
		boardVO.setUserid(vo.getUserid());
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("username", username);
		
		return "userBoard/userBoardEdit";
	}
	
	@RequestMapping(value="/userBoardEditPro" , method=RequestMethod.POST)
	public String editPro(Model model , UserBoardVO boardVO)throws Exception{
		
//		int bnoNum = Integer.parseInt(bno);
		
//		boardVO.setBno(bnoNum);
		
		userBoardService.edit(boardVO);
		
		return "redirect:/userBoard/boardList";
	}
	
	@RequestMapping(value="/listCri" , method=RequestMethod.GET)
	public String listAll(@ModelAttribute Criteria cri , Model model)throws Exception{
		
		logger.info("show list page with Criteria.......");
		
		model.addAttribute("boardList", userBoardService.listCriteria(cri));
		
		return "userBoard/userBoardListCri";
	}
	
	@RequestMapping(value="/listPage" , method=RequestMethod.GET)
	public String listPage(Criteria cri , Model model)throws Exception{
		
		logger.info(cri.toString());
		
		model.addAttribute("boardList",userBoardService.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(131);
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "userBoard/userBoardListPage";
		
	}
	
	
	
	
	
	
	
	

}
