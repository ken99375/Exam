<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
            <form action="SubjectCreateExecute.action" method="get">
                <div class="mx-3 mb-3 py-2 rounded" id="registrationForm">
                    <div class="mb-3">
                        <label class="form-label" for="student-f2-select">科目コード</label>
                        <input type="text" class=" form-control" placeholder="科目コードを入力してください" name="cd" required>
                    </div>
                    <p></p>
                    <div class="mb-3">
                        <label class="form-label" for="student-f3-select">科目名</label>
                        <input type="text" class=" form-control" placeholder="科目名を入力してください" name="name" required>
                    </div>
                    <p></p>
                    <div class="mb-3 text-left">
                        <button class="btn btn-primary" id="filter-button">登録</button>
                    </div>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>
            <a href="SubjectList.action">戻る</a>
        </section>
    </c:param>
</c:import>