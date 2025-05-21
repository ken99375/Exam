<%-- 学生一覧jsp --%>
<%@page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<c:import url = "/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class ="me-4">
		<h2 class ="h3 mb-3 fw-nor
			ma bg-secondary bg-opacity-10 py-2 px-4">科目情報復元</h2>
			<form action = "SubjectRestoreExecute.action" method = "get">
			<br/>
				<p>「${subject.name}(${subject.cd})」を復元してもよろしいですか</p>

				<input type ="hidden" name ="cd" value ="${subject.cd}">
				<input type = "hidden" name ="name" value ="${subject.name}">
				<input type="submit" class="btn btn-sm btn-success" value="復元">
			</form>

			<br/>
			<br/>

			<a href = "SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>