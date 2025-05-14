<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム - 更新完了</c:param>
    <c:param name="scripts"></c:param>

<c:param name="content">
    <section class="me-4">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

        <div class="alert alert-success text-dark text-center mb-4" role="alert">
            変更が完了しました
        </div>

        <div class="mb-3">
        	<!-- <a href="history.back()">戻る</a> -->
            <!-- <button class="btn btn-secondary" onclick="history.back()">戻る</button> -->
            <a href="StudentList.action" class="ms-3">学生一覧</a>
        </div>
    </section>
</c:param>
</c:import>