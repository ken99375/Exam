<%-- 学生一覧jsp --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム：成績一覧（学生）</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-4">
      <!-- 1. 氏名表示 -->
      <div class="mx-3 mb-3">
        氏名：<strong>${studentName}</strong>（${studentNo}）
      </div>


      <c:choose>
        <c:when test="${not empty tests}">
          <table class="table table-hover mx-3">
            <thead>
              <tr>

                <th>科目名</th>

                <th>科目コード</th>

                <th>回数</th>

                <th>点数</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="t" items="${tests}">
                <c:if test="${t.score != null}">
                  <tr>
                    <td>${t.subjectName}</td>
                    <td>${t.subjectCd}</td>
                    <td>${t.count}</td>
                    <td>${t.score}</td>
                  </tr>
                </c:if>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <!-- 存在しない場合 -->
          <div class="mx-3 text-danger">成績情報が存在しませんでした</div>
        </c:otherwise>
      </c:choose>
    </section>
  </c:param>
</c:import>
