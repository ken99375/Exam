
<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<section class="w-75 text-center m-auto border pb-3">
			<form action = "SignUpExecute.action" method="post">
				<div id="wrap_box">
					<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">サインアップ</h2>
					<c:if test="${errors.size()>0}">
						<div>
							<ul>
								<c:forEach var="error" items="${errors}">
									<li>${error}</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<div>

					<!-- 学校コード -->
					<div class="form-floating mx-5">
						<input class="form-control px-5 fs-5" autocomplete="off"
							id="id-input" maxlength="3" name="school_cd" placeholder="半角でご入力下さい"
							style="ime-mode: disabled" type="text" value="${school_cd}" required />
						<label>学校コード</label>
					</div>

					<div class="mt-4">
							<input class="w-25 btn btn-lg btn-primary" type="submit" name="login" value="進む"/>
						</div>
					</div>
				</div>
			</form>
		</section>
	</c:param>
</c:import>
