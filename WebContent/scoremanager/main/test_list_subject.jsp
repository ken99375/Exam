<!-- 科目別成績一覧 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">成績一覧（科目）</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-4">


      <form action="TestListSubjectExecute.action" method="get" class="mb-4">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
          <div class="col-2">
            <label for="f1" class="form-label">入学年度</label>
            <select id="f1" name="f1" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="y" items="${ent_year_set}">
                <option value="${y}" <c:if test="${y == selected_f1}">selected</c:if>>${y}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-2">
            <label for="f2" class="form-label">クラス</label>
            <select id="f2" name="f2" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="c" items="${c_list}">
                <option value="${c}" <c:if test="${c == selected_f2}">selected</c:if>>${c}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-4">
            <label for="f3" class="form-label">科目</label>
            <select id="f3" name="f3" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="s" items="${sub_list}">
                <option value="${s.cd}" <c:if test="${s.cd == selected_f3}">selected</c:if>>
                  ${s.name}
                </option>
              </c:forEach>
            </select>
          </div>
          <div class="col-2 text-center">
            <button type="submit" class="btn btn-secondary">検索</button>
          </div>
        </div>
      </form>

      <c:if test="${errors.filter_error != null}">
        <div class="text-danger mx-3 mb-3">${errors.filter_error}</div>
      </c:if>

      <div class="mb-2 ms-3">科目：<strong>${subjectName}</strong></div>

      <table class="table table-hover mx-3">
        <thead>
          <tr>
            <th>入学年度</th>
            <th>クラス</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>1回</th>
            <th>2回</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="t" items="${tests}">
            <tr>
              <td>${t.entYear}</td>
              <td>${t.classNum}</td>
              <td>${t.no}</td>
              <td>${t.studentName}</td>
              <td>${t.score1 != null ? t.score1 : '-'}</td>
              <td>${t.score2 != null ? t.score2 : '-'}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- 学生情報が存在しない場合 -->
      <c:if test="${empty tests}">
        <div class="ms-3">学生情報が存在しませんでした</div>
      </c:if>

    </section>
  </c:param>
</c:import>
