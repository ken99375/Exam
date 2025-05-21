<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報復元</h2>

            <c:choose>
                <c:when test="${c_list.size() > 0}">
                    <div class="px-4 mb-3">削除済みクラス一覧：${c_list.size()}件</div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>クラスコード</th>
                                <th class="ps-5">クラス名</th>
                                <th class="text-end">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="classItem" items="${c_list}">
                                <tr>
                                    <td>${classItem.classNum}</td>
                                    <td class="ps-5">${classItem.name}</td>
                                    <td class="text-end">
                                        <form action="ClassRestore2.action" method="post" style="display:inline;">
                                            <input type="hidden" name="classnum" value="${classItem.classNum}">
                                            <input type="submit" value="復元" class="btn btn-sm btn-success">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="mt-2 px-4">削除済みのクラスはありません。</div>
                </c:otherwise>
            </c:choose>

            <div class="mt-4 px-4">
                <a href="ClassList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
