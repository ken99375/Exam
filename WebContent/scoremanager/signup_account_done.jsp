<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム - サインアップ完了</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="no">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">サインアップ完了</h2>

            <div class="alert" role="alert"
                style="display: block; padding: 5px 10px; border-radius: 0; text-align: center; color: black; font-weight: normal; background-color: #b7dfc1;">
                サインアップが正常に完了しました。
            </div>

            <div class="mt-3">
                <a href="Login.action" class="btn btn-primary">ログイン画面へ</a>
            </div>
        </section>
    </c:param>
</c:import>
