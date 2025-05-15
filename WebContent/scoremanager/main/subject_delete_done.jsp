<%-- 学生一覧jsp --%>
<%@page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<c:import url = "/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class ="me-4">
		<h2 class ="h3 mb-3 fw-nor
			ma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<div class="alert" role="alert"
     		style="display: block; padding: 5px 10px; border-radius: 0; text-align: center; color: black; font-weight: normal; background-color: #b7dfc1;">
    		削除が完了しました
		</div>

			<br/>
			<br/>
			<br/>

			<a href = "SubjectList.action">科目一覧</a>
		</section>
	</c:param>
</c:import>