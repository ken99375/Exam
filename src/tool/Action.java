package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 抽象クラス
// 完成していないクラス
//   →使用する場合は継承しなければならない
public abstract class Action {

	// メソッド名:execute
	public abstract void execute(
			HttpServletRequest request, HttpServletResponse response
		)throws Exception;
}
