package cn.luokaiii.auth.autoconfig.config;

import cn.luokaiii.auth.autoconfig.utils.AccessTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//@Component
//@ConfigurationProperties(prefix = "security")
public class AccessDecisionManagerIml implements AccessDecisionManager {

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    private AntPathMatcher matcher = new AntPathMatcher();

    private String[] ignoreds;

    @Value("${spring.application.name}")
    private String applicationName;

    private String url;

    private String httpMethod;

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
// 请求路径
        url = getUrl(o);
        // http 方法
        httpMethod = getMethod(o);

        // 不拦截的请求
        for (String path : ignoreds) {
            String temp = path.trim();
            if (matcher.match(temp, url)) {
                return;
            }
        }

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 获取请求中的url
     */
    private String getUrl(Object o) {
        //获取当前访问url
        String url = ((FilterInvocation) o).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            return url.substring(0, firstQuestionMarkIndex);
        }
        return url;
    }

    private String getMethod(Object o) {
        return ((FilterInvocation) o).getRequest().getMethod();
    }

    // 检查子模块权限
//    private boolean checkSubModule(List<BaseModuleResources> modules) {
//
//        Iterator<BaseModuleResources> iterator = modules.iterator();
//        while (iterator.hasNext())
//        {
//            BaseModuleResources e = iterator.next();
//            // 匹配当前应用的资源
//            if(applicationName.equals(e.getProjectName())) {
//                if (e.getIsOperating() == 1 && e.getActive() == 1 && e.getModulePath() != null && !"".equals(e.getModulePath())) {
//                    if (matchUrl(url, e.getModulePath()) && httpMethod.toUpperCase().equals(e.getHttpMethod().toUpperCase())) {
//                        return true;
//                    }
//                }
//
//                // 递归检查子模块的权限
//                if (e.getSubModules().size() > 0) {
//                    if (checkSubModule(e.getSubModules())) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    private boolean matchUrl(String url, String modulePath) {

        List urls = Arrays.stream(url.split("/")).filter(e -> !"".equals(e)).collect(Collectors.toList());
        Collections.reverse(urls);

        List paths = Arrays.stream(modulePath.split("/")).filter(e -> !"".equals(e)).collect(Collectors.toList());
        Collections.reverse(paths);

        // 如果数量不相等
        if (urls.size() != paths.size()) {
            return false;
        }

        for (int i = 0; i < paths.size(); i++) {
            // 如果是 PathVariable 则忽略
            String item = (String) paths.get(i);
            if (item.charAt(0) != '{' && item.charAt(item.length() - 1) != '}') {
                // 如果有不等于的，则代表 URL 不匹配
                if (!item.equals(urls.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setIgnored(String ignored) {
        if (ignored != null && !"".equals(ignored))
            this.ignoreds = ignored.split(",");
        else
            this.ignoreds = new String[]{};
    }
}
