<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">

  <c:param name="title">
  成績参照
  </c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-4">
		<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
      <!-- 科目情報検索フォーム -->
      <form action="TestListSubjectExecute.action" method="get" class="mb-4">
        <div class="row border mx-3 mb-0 py-2 h-50 align-items-center rounded">
        <div class="col-2">
      	<label class="form-label">科目情報</label>
      	</div>
          <div class="col-2">
            <label for="f1" class="form-label">入学年度</label>
            <select id="f1" name="f1" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="y" items="${ent_year_set}">
                <option value="${y}" <c:if test="${y == selected_f1}">selected</c:if>>${y}</option>
              </c:forEach>
            </select>
          </div>
			<%--クラス表示--%>
          <div class="col-2">
            <label for="f2" class="form-label">クラス</label>
            <select id="f2" name="f2" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="c" items="${c_list}">
                <option value="${c}" <c:if test="${c == selected_f2}">selected</c:if>>${c}</option>
              </c:forEach>
            </select>
          </div>
			<%--科目表示--%>
          <div class="col-4">
            <label for="f3" class="form-label">科目</label>
            <select id="f3" name="f3" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="s" items="${sub_list}">
                <option value="${s.cd}" <c:if test="${s.cd == selected_f3}">selected</c:if>>${s.name}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2 text-center">
            <button type="submit" class="btn btn-secondary mt-4">検索</button>
          </div>
        </div>
      </form>

      <!-- 学生情報検索フォーム -->
      <form action="<c:url value='/TestListStudentExecute.action'/>" method="get" class="mb-4">
        <div class="row border mx-3 mb-3 py-2 h-50 align-items-center rounded">
        	<div class="col-2">
      		<label class="form-label">学生情報</label>
      		</div>
          <div class="col-4">
            <label class="form-label" for="subject-id-input">学生番号</label>
            <input type="text" id="studentNo" name="studentNo" class="form-control" placeholder="学生番号を入力してください" value="${studentNo}" maxlength="10">
          </div>
          <div class="col-2 text-center">
            <button type="submit" class="btn btn-secondary">検索</button>
          </div>
        </div>
      </form>

      <!-- エラー表示 -->
      <c:if test="${errors.filter_error != null}">
        <div class="text-danger mx-3 mb-3">${errors.filter_error}</div>
      </c:if>

      <!-- ヒント文 -->
      <p><font color="aqua">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</font></p>

    </section>
  </c:param>
</c:import>
