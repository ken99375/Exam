<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム - 登録完了</c:param>
    <c:param name="scripts"></c:param>

<c:param name="content">
    <section class="me-4">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

        <div class="alert" role="alert"
     		style="display: block; padding: 5px 10px; border-radius: 0; text-align: center; color: black; font-weight: normal; background-color: #b7dfc1;">
            登録が完了しました
        </div>

        <div class="mb-3">
            <a href="StudentCreate.action" style="margin-right: 60px;">戻る</a>
            <a href="StudentList.action">学生一覧</a>
       </div>
    </section>
</c:param>
</c:import>