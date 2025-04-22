package scoremanager;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import tool.Action;

public class LoginAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		try(Connection con = new Dao().getConnection()){
			// JSPへforward
			req.getRequestDispatcher("login.jsp").forward(req, res);
		}catch(Exception e){
			e.printStackTrace();
		}
		//JSPへフォワード 7
//		req.getRequestDispatcher("login.jsp").forward(req, res);
	}
}