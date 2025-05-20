<%-- クラス一覧jsp --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>

            <!-- 新規登録リンク（右寄せ） -->
            <div class="my-2 text-end px-4">
                <a href="ClassCreate.action">新規登録</a>
            </div>

            <!-- クラスリスト表示 -->
            <c:choose>
                <c:when test="${c_list.size() > 0}">
                    <div>クラス一覧：${c_list.size()}件</div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>クラスコード</th>
                                <th class="ps-5">クラス名</th>
                                <th class="text-end"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="classItem" items="${c_list}">
                                <tr>
                                    <td>${classItem.classNum}</td>
                                    <td class="ps-5">${classItem.name}</td>
                                    <td class="text-end">
                                        <a href="ClassUpdate.action?classnum=${classItem.classNum}" style="margin-right: 100px;">変更</a>
                                        <a href="ClassDelete.action?classnum=${classItem.classNum}" style="margin-right: 50px;">削除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>クラスコード</th>
                                <th class="ps-5">クラス名</th>
                                <th class="text-end"></th>
                            </tr>
                        </thead>
                    </table>
                    <div class="mt-2">クラス情報が存在しませんでした。</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
