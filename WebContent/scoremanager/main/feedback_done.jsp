<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">フィードバック完了</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-success bg-opacity-10 py-2 px-4">フィードバック送信完了</h2>
            <div class="mx-3 mb-3 py-2">
                <p>以下の内容で送信されました。</p>
                <ul>
                    <li><strong>お名前：</strong> ${name}</li>
                    <li><strong>送信時刻：</strong> ${createdAt}</li>
                    <li><strong>内容：</strong><br>
                        <pre class="border rounded bg-light p-2">${message}</pre>
                    </li>
                </ul>
                <a href="Menu.action" class="btn btn-outline-primary mt-3">メニューに戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
