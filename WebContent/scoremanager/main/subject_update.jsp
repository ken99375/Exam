<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
            <form method="post" action="SubjectUpdateExecute.action" class="px-4">
                <input type="hidden" name="no" value="${student.no}" />

                <div class="mb-3">
    				<label for="cd" class="form-label">科目コード</label>
    				<div>${subject.cd}</div>
    				<input type="hidden" class="form-control" id="cd" name="cd" value="${subject.cd}" />
				</div>

                <div class="mb-3">
                    <label for="name" class="form-label">科目名</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="科目名を入力してください" required />
                </div>

                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">変更</button>
                </div>
                <div>
                    <a href="SubjecttList.action">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>