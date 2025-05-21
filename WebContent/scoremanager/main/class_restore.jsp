<%-- クラス削除確認 JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報復元</h2>

            <form action="ClassRestoreExecute.action" method="post">
                <br/>
                <p>「${classInfo.name}（${classInfo.classNum}）」を復元してもよろしいですか？</p>

                <input type="hidden" name="classnum" value="${classInfo.classNum}">
                <input type="hidden" name="name" value="${classInfo.name}">
                <input type="submit" class="btn btn-sm btn-success" value="復元">
            </form>

            <br/><br/>

            <a href="ClassList.action">戻る</a>
        </section>
    </c:param>
</c:import>
