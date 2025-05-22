package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.FeedbackMessage;
import dao.FeedbackMessageDao;
import tool.Action;


public class FeedbackSendAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String message = req.getParameter("message");

        boolean hasError = false;

        // エラー格納用マップ
        java.util.Map<String, String> errors = new java.util.HashMap<>();

        // messageが空ならエラー（必須）
        if (message == null || message.trim().isEmpty()) {
            errors.put("message", "内容を入力してください");
            hasError = true;
        }

        // 名前が空の場合は「匿名」とする（エラーにはしない）
        if (name == null || name.trim().isEmpty()) {
            name = "匿名";
        }

        if (hasError) {
            req.setAttribute("errors", errors);
            req.setAttribute("param.name", name);
            req.setAttribute("param.message", message);
            req.getRequestDispatcher("feedback_form.jsp").forward(req, res);
            return;
        }

        FeedbackMessage msg = new FeedbackMessage();
        msg.setName(name);
        msg.setMessage(message);

        FeedbackMessageDao dao = new FeedbackMessageDao();
        dao.save(msg);

        req.setAttribute("name", name);
        req.setAttribute("message", message);
        req.setAttribute("createdAt", new java.sql.Timestamp(System.currentTimeMillis()));
        req.getRequestDispatcher("feedback_done.jsp").forward(req, res);
    }
}
