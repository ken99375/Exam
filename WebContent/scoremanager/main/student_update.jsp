<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目新規登録</h2>

      <!-- 全体エラー（重複など） -->
      <c:if test="${not empty errors.duplication}">
        <div class="alert alert-danger">${errors.duplication}</div>
      </c:if>

      <form action="SubjectCreateExecute.action" method="post" class="px-4">

        <div class="mb-3">
          <label for="cd" class="form-label">科目コード</label>
          <input type="text" class="form-control" id="cd" name="cd"
                 placeholder="例：ENG101"
                 value="${cd}" required />
          <c:if test="${not empty errors.cd}">
            <div class="text-warning small mt-1">${errors.cd}</div>
          </c:if>
        </div>

        <div class="mb-3">
          <label for="name" class="form-label">科目名</label>
          <input type="text" class="form-control" id="name" name="name"
                 value="${name}" required />
          <c:if test="${not empty errors.name}">
            <div class="text-warning small mt-1">${errors.name}</div>
          </c:if>
        </div>

        <div class="mb-3">
          <button type="submit" class="btn btn-primary">登録</button>
          <a href="SubjectList.action" class="btn btn-secondary">戻る</a>
        </div>
      </form>
    </section>
  </c:param>
</c:import>
