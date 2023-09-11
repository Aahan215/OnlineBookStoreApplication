package com.aahan.onlineBookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

	@RequestMapping(value = "/ui/book")
	public ModelAndView book() {
		return mav("book.html");
	}

	@RequestMapping(value = "/ui/createOrder")
	public ModelAndView createOrder() {
		return mav("createOrder.html");
	}

	@RequestMapping(value = "/ui/order")
	public ModelAndView viewOrder() {
		return mav("order.html");
	}

	@RequestMapping(value = "/ui/cart")
	public ModelAndView cart() {
		return mav("cart.html");
	}
}
