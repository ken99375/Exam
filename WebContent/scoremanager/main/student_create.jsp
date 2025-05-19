<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me=4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

            <!-- 入学年度 -->
            <form action="StudentCreateExecute.action" method="get">
                <div class="mx-3 mb-3 py-2 rounded" id="registrationForm">
                    <div class="mb-3">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="entYear">
                            <option value="0">-------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <%--現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year}"<c:if test="${param.entYear == year.toString()}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                            <c:if test="${not empty errors.entyear}">
        					<div class="text-warning small mt-1">${errors.entyear}</div>
    						</c:if>
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f2-select">学生番号</label>
                        <input type="text" class=" form-control" placeholder="学生番号を入力してください" name="no" required value="${param.no }" maxlength="10">
						    <c:if test="${not empty errors.no}">
        						<div class="text-warning small mt-1">${errors.no}</div>
    						</c:if>
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f3-select">氏名</label>
                        <input type="text" class=" form-control" placeholder="氏名を入力してください" name="name" required value="${param.name }" maxlength="10">
                            <c:if test="${not empty errors.name}">
						        <div class="text-warning small mt-1">${errors.name}</div>
						    </c:if>
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f4-select">クラス</label>
                        <select class="form-select" id="student-f4-select" name="classNum">
                            <option value="">-------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <%--現在のnumと選択されたf2が一致されていた場合selectedを追記 --%>
                                 <option value="${num.classNum}" <c:if test="${param.classNum == num}">selected</c:if>>${num.classNum}</option>
                            </c:forEach>
                        </select>
                            <c:if test="${not empty errors.classnum}">
						        <div class="text-warning small mt-1">${errors.classnum}</div>
						    </c:if>
                    </div>
                    <p></p>
                    <div class="mb-3 text-left">
                        <button class="btn btn-secondary" id="filter-button">登録して終了</button>
                    </div>
                </div>
            </form>
            <a href="StudentList.action">戻る</a>
        </section>
    </c:param>
</c:import>