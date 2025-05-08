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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

			<form action = "TestListSubjectExecute.action"method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="ent_year">

                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year }" <c:if test="${year == f1 }">selected</c:if>>${year }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="class_num">

                            <c:forEach var="num" items="${c_list}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num }" <c:if test="${num == f2 }">selected</c:if>>${num }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">科目</label>
                        <select class="form-select" id="student-f2-select" name="cd">

                            <c:forEach var="num" items="${sub_list}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num.cd }" >${num.name }</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">検索</button>
                    </div>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>
            <form action = "TestListStudentExecute.action"method="post">
            	<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
            		学生番号：<input type = "text" name ="cd">
                    <div class="col-2 text-center">
                        <input type = "submit" class="btn btn-secondary" id="filter-button" value = "検索">
                    </div>
            	</div>
            </form>
	</section>
   </c:param>
</c:import>