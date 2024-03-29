package cn.luokaiii.auth.provider.config.web;

import cn.luokaiii.common.model.ResponseData;
import cn.luokaiii.user.api.ResponseCode;
import cn.luokaiii.user.api.model.MovieClientDetails;
import cn.luokaiii.user.client.MovieClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"authorizationRequest"})
public class MvcController {

    private final MovieClientDetailsService movieClientDetailsService;

    @Autowired
    public MvcController(MovieClientDetailsService movieClientDetailsService) {
        this.movieClientDetailsService = movieClientDetailsService;
    }

    /**
     * 登出回调
     */
    @RequestMapping("/backReferer")
    public void sendBack(HttpServletRequest request, HttpServletResponse response) {
        try {
            String referer = request.getHeader("referer");
            if (referer != null) {
                int index = referer.indexOf("?");
                if (index != -1) {
                    referer = referer.substring(0, index);
                }
                response.sendRedirect(referer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 授权页面
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView authorizePage(Map<String, Object> model) {
        // 获取用户名
        String userName = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
        model.put("userName", userName);
        return new ModelAndView("authorize", model);
    }

    /**
     * 主页，未从客户端跳转直接登陆会显示
     */
    @RequestMapping("/")
    public ModelAndView indexPage(Map<String, Object> model) {
        // 获取用户名
        String userName = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
        model.put("userName", userName);
        // 获取全部客户端应用
        ResponseData<List<MovieClientDetails>> allClient = movieClientDetailsService.getAllClient();
        if (ResponseCode.SUCCESS.getCode().equals(allClient.getCode())) {
            model.put("client", allClient.getData());
        } else {
            model.put("client", new ArrayList<>());
        }
        return new ModelAndView("index", model);
    }
}
