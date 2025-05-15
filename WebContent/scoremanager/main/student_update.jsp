<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

            <form method="post" action="StudentUpdateExecute.action" class="px-4">
                <input type="hidden" name="no" value="${student.no}" />

                <div class="mb-3">
				    <label for="entYear" class="form-label">入学年度</label>
				    <p class="form-control-plaintext">${student.entYear}</p>
				    <input type="hidden" name="entYear" value="${student.entYear}" />
                </div>

                <div class="mb-3">
				    <label for="no_display" class="form-label">学生番号</label>
				    <p class="form-control-plaintext">${student.no}</p>
				    <input type="hidden" name="no" value="${student.no}"/>
                </div>

 				<div class="mb-3">
				    <label for="name" class="form-label">氏名</label>
				    <input type="text" class="form-control" id="name" name="name"
				           placeholder="氏名を入力してください"
				           value="${student.name}" required maxlength="10"/>
				    <c:if test="${not empty errors.name}">
				        <div class="text-warning small mt-1">${errors.name}</div>
				    </c:if>
				</div>


                <div class="mb-3">
                    <label for="classNum" class="form-label">クラス</label>
                    <select class="form-select" id="classNum" name="classNum">
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="attend" name="attend" value="true"
                        <c:if test="${student.attend}">checked</c:if> />
                    <label class="form-check-label" for="attend">在学中</label>
                </div>

                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">変更</button>
                </div>
                <div>
                    <a href="StudentList.action">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>