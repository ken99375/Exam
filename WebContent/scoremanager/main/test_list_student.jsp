<%-- 学生一覧jsp --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（${tes}）</h2>

		<form action = "TestListSubjectExecute.action"method="get">

                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                <div class="col-2">科目情報</div>

                    <div class="col-2">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="ent_year">
							<option value="">------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year }" <c:if test="${year == f1 }">selected</c:if>>${year }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="class_num">
							<option value="">------</option>
                            <c:forEach var="num" items="${c_list}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num.classNum }" <c:if test="${num.classNum == f2 }">selected</c:if>>${num.classNum }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label" for="student-f2-select">科目</label>
                        <select class="form-select" id="student-f2-select" name="cd">
							<option value="">------</option>
                            <c:forEach var="num" items="${sub_list}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num.cd }" >${num.name }</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">検索</button>
                    </div>
                    <c:if test="${not empty errors['filter']}">
					  <div class="mt-2 text-warning">
					    ${errors['filter']}
					  </div>
					</c:if>

                </div>
            </form>
            <form action = "TestListStudentExecute.action"method="post">

            	<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
            		<div class="col-2">学生情報</div>
            		<div class="col-4">
            			<label class="form-label" for="student-f2-select">学生番号</label>
            			<input class="form-control" type = "text" name ="cd" placeholder="学生番号を入力してください">
            		</div>
                    <div class="col-2 text-center">
                        <input type = "submit" class="btn btn-secondary" id="filter-button" value = "検索">
                    </div>
            	</div>
            </form>
            <hr>
           <c:if test="${not empty errors.ets1}">
           	<div class = "col-3">氏名：大原一郎（${param.cd}）</div>
			  <p>${errors.ets1}</p>
			</c:if>
			<c:if test="${not empty errors.ets2}">
           	<div class = "col-3">氏名：${student.name}（${param.cd}）</div>
			  <p>${errors.ets2}</p>
			</c:if>
			<c:if test = "${not empty errors.etsub1}">

		    	<p>${errors.etsub1}</p>
		    </c:if>
		<c:choose>
    		<c:when test="${stu_list.size() > 0}">
    			<p>氏名：${item}(${param.cd})</p>
        		<table class="table table-hover">
		            <tr>
		                <th>科目名</th>
		                <th>科目コード</th>
		                <th>回数</th>
		                <th>点数</th>
		            </tr>
            		<c:forEach var="test_stu" items="${stu_list}">
		                <tr>
		                    <td>${test_stu.subjectName}</td>
		                    <td>${test_stu.subjectCd}</td>
                    		<td>${test_stu.num}</td>
                    		<td>${test_stu.point}</td>
		                </tr>
		            </c:forEach>
		        </table>
		    </c:when>
		    <c:when test="${sub_li.size() > 0}">
		    	<p>科目：${item}</p>
		    	<table class="table table-hover">
		            <tr>
		                <th>入学年度</th>
		                <th>クラス</th>
		                <th>学生番号</th>
		                <th>氏名</th>
		                <th>1回</th>
		                <th>2回</th>
		            </tr>
            		<c:forEach var="test_sub" items="${sub_li}">
		                <tr>
		                    <td>${test_sub.entYear}</td>
		                    <td>${test_sub.classNum}</td>
                    		<td>${test_sub.studentNo}</td>
                    		<td>${test_sub.studentName}</td>
                    		<td><c:out value = "${test_sub.getPoint(1)}" default = "ー"/></td>
                    		<td><c:out value = "${test_sub.getPoint(2)}" default = "ー"/></td>
		                </tr>
		            </c:forEach>
		        </table>
		    </c:when>

		</c:choose>
	</section>
   </c:param>
</c:import>