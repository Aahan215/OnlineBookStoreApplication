package com.aahan.onlineBookStore.controller;

import com.aahan.onlineBookStore.model.InfoData;
import com.aahan.onlineBookStore.model.UserForm;
import com.aahan.onlineBookStore.pojo.UserPojo;
import com.aahan.onlineBookStore.service.ApiException;
import com.aahan.onlineBookStore.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.aahan.onlineBookStore.util.ConversionUtil.convert;

@Controller
public class InitController extends AbstractUiController {

	@Autowired
	private UserService service;
	@Autowired
	private InfoData info;

	@ApiOperation(value = "Initialize application")
	@RequestMapping(path = "/site/init", method = RequestMethod.GET)
	public ModelAndView showPage(UserForm form) throws ApiException {
		info.setMessage("");
		return mav("init.html");
	}

	@ApiOperation(value = "Initialize application")
	@RequestMapping(path = "/site/init", method = RequestMethod.POST)
	public ModelAndView initSite(UserForm form) throws ApiException {
		List<UserPojo> list = service.getAll();
		if (list.size() > 0) {
			info.setMessage("Application already initialized. Please use existing credentials");
		} else {
			form.setRole("ADMIN");
			UserPojo p = convert(form);
			service.add(p);
			info.setMessage("Application initialized");
		}
		return mav("init.html");
	}

}
