<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">フィードバック</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">フィードバックフォーム</h2>
            <form action="FeedbackSend.action" method="post">
                <div class="mx-3 mb-3 py-2 rounded" id="feedbackForm">
                    <!-- 名前入力（匿名可） -->
                    <div class="mb-3">
                        <label class="form-label" for="feedback-name">お名前（匿名可）：</label>
                        <input type="text" id="feedback-name" class="form-control"
                               placeholder="お名前を入力してください（匿名可）"
                               name="name" value="${param.name}" maxlength="20">
                        <c:if test="${not empty errors.name}">
                            <div class="text-warning small mt-1">${errors.name}</div>
                        </c:if>
                    </div>

                    <!-- 内容入力 -->
                    <div class="mb-3">
                        <label class="form-label" for="feedback-message">内容：</label>
                        <textarea id="feedback-message" name="message" class="form-control" rows="5" required>${param.message}</textarea>
                        <c:if test="${not empty errors.message}">
                            <div class="text-warning small mt-1">${errors.message}</div>
                        </c:if>
                    </div>

                    <!-- 送信ボタン -->
                    <div class="mb-3 text-left">
                        <button class="btn btn-secondary" type="submit">送信</button>
                    </div>
                </div>
            </form>
            <a href="Menu.action">戻る</a>
        </section>
    </c:param>
</c:import>
