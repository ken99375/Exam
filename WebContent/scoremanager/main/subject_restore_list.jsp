<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報復元</h2>

            <c:choose>
                <c:when test="${subject_list.size() > 0}">
                    <div class="px-4 mb-3">削除済み科目一覧：${subject_list.size()}件</div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>科目コード</th>
                                <th class="ps-5">科目名</th>
                                <th class="text-end"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="subjectItem" items="${subject_list}">
                                <tr>
                                    <td>${subjectItem.cd}</td>
                                    <td class="ps-5">${subjectItem.name}</td>
                                    <td class="text-end">
                                        <form action="SubjectRestore2.action" method="post" style="display:inline;">
                                            <input type="hidden" name="cd" value="${subjectItem.cd}">
                                            <input type="submit" value="復元" class="btn btn-sm btn-success"style="margin-right: 100px;">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="mt-2 px-4">削除済みの科目はありません。</div>
                </c:otherwise>
            </c:choose>

            <div class="mt-4 px-4">
                <a href = "SubjectList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
