package mirosimo.car_showroom2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import mirosimo.car_showroom2.service.MenuService;

/* 
 * Used for adding root menuItem attribute into model 
 * 
 * */
public class MenuInterceptor  implements HandlerInterceptor {
	@Autowired
	private MenuService menuService;
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
            ModelAndView modelAndView) throws Exception {
        request.setAttribute("menuItem", menuService.getMenuById(1)); 

    }
}
