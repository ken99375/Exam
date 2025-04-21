<%------------------------- 未完成 ------------------------%>





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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生新規作成</h2>

            <form method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value="0">------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year }" <c:if test="${year == f1 }">selected</c:if>>${year }</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">学生番号</label>
                        	<input class="form-control px-5 fs-5" autocomplete="off"
								id="id-input" maxlength="20" name="no"
								style="ime-mode: disabled" type="text" value="${no}" required />
                    </div>

                    <div class="col-4">
                        <label class="form-label" for="student-f1-select">氏名</label>
                        	<input class="form-control px-5 fs-5" autocomplete="off"
								id="id-input" maxlength="20" name="no"
								style="ime-mode: disabled" type="text" value="${name}" required />
                    </div>

                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="class_num">
                            <option value="0">------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num }" <c:if test="${num == f2 }">selected</c:if>>${num }</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">登録</button>
                    </div>
                    <a href="StudentList.action">戻る</a>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>
	</section>
   </c:param>
</c:import>