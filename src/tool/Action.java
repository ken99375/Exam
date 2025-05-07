package tool;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public abstract class Action {
	public void errorBack(HttpServletRequest req, HttpServletResponse res, Map<String, String> errors, String errorPage
			) throws ServletException, IOException {
		req.setAttribute("errors", errors);
		req.getRequestDispatcher(errorPage).forward(req, res);
	}

	public abstract void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception;
}
