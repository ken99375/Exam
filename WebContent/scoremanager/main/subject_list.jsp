<%-- 科目一覧jsp --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>

            <!-- 新規登録リンク（右寄せ） -->
            <div class="my-2 text-end px-4">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

			<!-- 科目リスト表示 -->
            <c:choose>
                <c:when test="${subject_list.size() > 0}">
                    <div>科目一覧：${subject_list.size()}件</div>
						<table class="table table-hover">
						    <thead>
						        <tr>
						            <th>科目コード</th>
						            <th class="ps-5">科目名</th>
						            <th class="text-end"></th>
						        </tr>
						    </thead>
						    <tbody>
						        <c:forEach var="subject" items="${subject_list}">
						            <tr>
						                <td>${subject.cd}</td>
						                <td class="ps-5">${subject.name}</td>
						                <td class="text-end">
						                    <a href="SubjectUpdate.action?cd=${subject.cd}" style="margin-right: 100px;">変更</a>
						                    <a href="SubjectDelete.action?cd=${subject.cd}"style="margin-right: 50px;">削除</a>
						                </td>
						            </tr>
						        </c:forEach>
						    </tbody>
						</table>
                </c:when>
                <c:otherwise>
                    <div>科目は登録されていません。</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>