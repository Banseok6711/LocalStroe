package com.user.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.user.domain.UserVO;
import com.user.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Inject
	private UserService service;
	// private BoardService service;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET() throws Exception {

		/*
		 * BoardVO vo = new BoardVO(); vo.setTitle("test"); vo.setContent(
		 * "content test"); vo.setWriter("test");
		 */

		UserVO vo = new UserVO();
		vo.setUserid("banseok");
		vo.setUserpw("1234");
		vo.setUsername("이반석");
		vo.setAge(14);
		vo.setBirth(870411);
		vo.setPhone(01023232232);

		service.insert(vo);

		List<UserVO> list = service.listAll();
		for (int i = 0; i < list.size(); i++) {
			logger.info("list.num :" + i + " , " + list.get(i).getUserid() + ": " + list.get(i).getUsername());

		}

	}
	
	
	

}
