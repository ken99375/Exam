<%-- 成績登録 --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <form method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-2">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year }" <c:if test="${year == f1 }">selected</c:if>>${year }</option>
                            </c:forEach>
                        </select>
                        <c:if test="${errors.f1 != null}">
                            <div class="text-danger small">${errors.f1}</div>
                        </c:if>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num}" <c:if test="${num == f2 }">selected</c:if>>${num }</option>
                            </c:forEach>
                        </select>
                        <c:if test="${errors.f2 != null}">
                            <div class="text-danger small">${errors.f2}</div>
                        </c:if>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="student-f3-check">科目</label>
                            <select class="form-select" id="student-f3-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${subject}">
                                <option value="${subject }" <c:if test="${subject == f3 }">selected</c:if>>${num }</option>
                            </c:forEach>
                        </select>
                        <c:if test="${errors.f3 !=null }">
                        	<div class="text-danger small">${errors.f3 }</div>
                        </c:if>


                    </div>
                    <div class="col-2">
                        <label class="form-label" for="student-f4-select">回数</label>
                        <select class="form-select" id="student-f4-select" name="f4">
                            <option value="0">--------</option>
                            <c:forEach var="count" items="${count}">
                                <option value="${count }" <c:if test="${count == f4 }">selected</c:if>>${num }</option>
                            </c:forEach>
                        </select>
                        <c:if test="${errors.f4 != null}">
                            <div class="text-danger small">${errors.f4}</div>
                        </c:if>

                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">検索</button>
                    </div>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>
		<c:choose>

    		<c:when test="${students.size() > 0}">
        		<div>科目：${subject} (${count }件)</div>
        		<table class="table table-hover">
		            <tr>
		                <th>入学年度</th>
		                <th>クラス</th>
		                <th>学生番号</th>
		                <th>氏名</th>
		                <th>点数</th>

		                <th></th>
		            </tr>

           			<c:forEach var="student "items="${students}">
		                <tr>
		                    <td>${student.entYear}</td>
		                    <td>${student.classNum}</td>
		                    <td>${student.no}</td>
		                    <td>${student.name}</td>
		                    <td>${student.point }</td>
                    		<td class="text-center">
                    		<c:if test="${errors.point != null}">
                    		<%-- <div class="mt-2 text-danger small">${errors.point}</div> --%>
                    		</c:if></td>
		                </tr>
		            </c:forEach>

		        </table>
		        <div class="mb-3 text-left">
		        		<input type="radio" class="btn-check" name="options" id="option4" autocomplete="off">
                        <button class="btn btn-secondary" id="filter-button">登録して終了</button>
                </div>

		    </c:when>
    		<c:otherwise>
        		<div>学生情報が存在しませんでした。</div>
    		</c:otherwise>
		</c:choose>
	</section>
   </c:param>
</c:import>