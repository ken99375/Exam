<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス情報登録</h2>


            <form action="ClassCreateExecute.action" method="post">
                <div class="mx-3 mb-3 py-2 rounded" id="registrationForm">
                    <div class="mb-3">
                        <label class="form-label" for="classnum">クラスコード</label>
                        <input
                            type="text"
                            class="form-control"
                            id="classnum"
                            name="classnum"
                            placeholder="クラスコードを入力してください"
                            maxlength="5"
                            value="${classnum}"
                            required>
                        <c:if test="${not empty errors.cd}">
                            <div class="text-warning small mt-1">${errors.cd}</div>
                        </c:if>
                        <c:if test="${not empty errors.duplication}">
                            <div class="text-warning small mt-1">${errors.duplication}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="name">クラス名</label>
                        <input
                            type="text"
                            class="form-control"
                            id="name"
                            name="name"
                            placeholder="クラス名を入力してください"
                            maxlength="20"
                            value="${name}"
                            required>
                        <c:if test="${not empty errors.name}">
                            <div class="text-warning small mt-1">${errors.name}</div>
                        </c:if>
                    </div>

                    <div class="mb-3 text-left">
                        <button class="btn btn-primary" type="submit">登録</button>
                    </div>
                </div>
            </form>
            <a href="ClassList.action">戻る</a>
        </section>
    </c:param>
</c:import>
