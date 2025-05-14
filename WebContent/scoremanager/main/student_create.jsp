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

            <!-- エラー表示 -->
            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-3">
                    <c:forEach var="e" items="${errors}">
                        <div>${e.value}</div>
                    </c:forEach>
                </div>
            </c:if>

            <!-- 入学年度 -->
            <form action="StudentCreateExecute.action" method="get">
                <div class="mx-3 mb-3 py-2 rounded" id="registrationForm">

                    <div class="mb-3">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="entYear">
                            <option value="0">-------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <%--現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year}"<c:if test="${param.entYear == year}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f2-select">学生番号</label>
                        <input type="text" class=" form-control" placeholder="学生番号を入力してください" name="no" required value="${param.no }">
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f3-select">氏名</label>
                        <input type="text" class=" form-control" placeholder="氏名を入力してください" name="name" required value="${param.name }">
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f4-select">クラス</label>
                        <select class="form-select" id="student-f4-select" name="classNum">
                            <option value="0">-------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <%--現在のnumと選択されたf2が一致されていた場合selectedを追記 --%>
                                 <option value="${num}" <c:if test="${param.classNum == num}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <p></p>
                    <div class="mb-3 text-left">
                        <button class="btn btn-secondary" id="filter-button">登録して終了</button>
                    </div>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>
            <a href="StudentList.action">戻る</a>
        </section>
    </c:param>
</c:import>