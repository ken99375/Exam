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
                        <select class="form-select" id="student-f1-select" name="ent_year">
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
                        <select class="form-select" id="student-f2-select" name="class_num">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${c_list}">
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
                            <select class="form-select" id="student-f3-select" name="cd">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${sub_list}">
                                <option value="${subject.cd }" <c:if test="${subject == f3 }">selected</c:if>>${subject.name }</option>
                            </c:forEach>
                        </select>
                        <c:if test="${errors.f3 !=null }">
                        	<div class="text-danger small">${errors.f3 }</div>
                        </c:if>


                    </div>
                    <div class="col-2">
                        <label class="form-label" for="student-f4-select">回数</label>
                        <select class="form-select" id="student-f4-select" name="times">
                            <option value="0">--------</option>
                            <option value= "1" >1</option>
                            <option value = "2">2</option>
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

			<form action = "TestRegistExecute.action" method = "get">
	            	<input type = "hidden" name = "ent_year" value="${param.ent_year}"/>
	            	<input type = "hidden" name = "class_num" value="${param.class_num}"/>
	            	<input type = "hidden" name = "cd" value="${param.cd}"/>
	            	<input type = "hidden" name = "times" value="${param.times}"/>
	            	<c:choose>
			    		<c:when test="${test_li.size() > 0}">
			    		<p>科目：${subject.name} (${times} 回) </p>
			        		<table class="table table-hover">
					            <tr>
					                <th>入学年度</th>
					                <th>クラス</th>
					                <th>学生番号</th>
					                <th>氏名</th>
					                <th>点数</th>
					            </tr>
			            		<c:forEach var="test" items="${test_li}" varStatus = "st">
					                <tr>
					                    <td>${test.student.entYear}</td>
					                    <td>${test.classNum}</td>
			                    		<td>${test.student.no}<input type = "hidden" name = "studentNo" value = "${test.student.no}" /></td>
			                    		<td>${test.student.name}</td>
			                    		<td>
			                    			<input type = "text" name = "po" value = "${test.point}">
			                    			<c:set var = "key" value= "${'po'}${st.index}" />
			                    			<c:if test = "${not empty errors[key]}"><span class ="text-danger">${errors[key]}</span></c:if>
			                    		</td>
					                </tr>
					                <input type = "hidden" name = "subjectCd" value = "${test.subject.cd}" >
			                    	<input type = "hidden" name = "no" value = "${test.no}" >
					            </c:forEach>
					        </table>
					        <div class="col-2 text-center">
	                        	<button  type ="submit" class="btn btn-secondary" id="filter-button">登録して終了</button>
	                    	</div>
					    </c:when>
			    		<c:otherwise>
			        		<div>検索情報が存在しません。</div>
			    		</c:otherwise>
					</c:choose>
	            </form>
	</section>
   </c:param>
</c:import>