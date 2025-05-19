<%-- 学生一覧jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>
            <div class="my-2 text-end px-4">
                <a href="ClassCreate.action">新規登録</a>
            </div>
		<c:choose>
    		<c:when test="${c_list.size() > 0}">

        		<table class="table table-hover">
		            <tr>
		                <th>クラスコード</th>
		                <th>クラス名</th>

		            </tr>
            		<c:forEach var="classItem" items="${c_list}">
		                <tr>
		                    <td>${classItem.classNum}</td>
		                    <td>${classItem.name}</td>

		                </tr>
		            </c:forEach>
		        </table>
		    </c:when>
    		<c:otherwise>
        		<div>クラス情報が存在しませんでした。</div>
    		</c:otherwise>
		</c:choose>
	</section>
   </c:param>
</c:import>