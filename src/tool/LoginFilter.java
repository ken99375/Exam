package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns={"/*"})
public class LoginFilter implements Filter {
	public void doFilter(
			ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

			HttpServletRequest h_req = (HttpServletRequest) req;
			HttpServletResponse h_res = (HttpServletResponse) res;

			h_req.setCharacterEncoding("UTF-8");
			h_res.setContentType("text/html; charset=UTF-8");

			chain.doFilter(h_req, h_res);
		}

	public void init(FilterConfig filterConfig) {}
	public void destroy() {}
	}
