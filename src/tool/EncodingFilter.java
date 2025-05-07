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
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns={"/scoremanager/main/*"})
public class EncodingFilter implements Filter {
	public void doFilter(
			ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

			HttpServletRequest h_req = (HttpServletRequest) req;
			HttpServletResponse h_res = (HttpServletResponse) res;

			// サーバ側の処理をする前処理でログイン状態のチェック
			HttpSession session = h_req.getSession(false);
			System.out.println("ログインチェックを行います。");
			if (session == null || session.getAttribute("user") == null){
				System.out.println("セッションが有効ではありません。");
				h_res.sendRedirect(h_req.getContextPath() + "/login.jsp");
				return;
			}
			System.out.println("ログインチェックをしました。");
			System.out.println("現在のコンテキストパス："+ h_req.getContextPath());
			System.out.println("サーブレットの共通処理のためフィルタの前処理を作成しました");

			chain.doFilter(h_req, h_res);

			System.out.println("サーブレットの共通処理のためフィルタの後処理を作成しました");
		}

	public void init(FilterConfig filterConfig) {}
	public void destroy() {}
	}
