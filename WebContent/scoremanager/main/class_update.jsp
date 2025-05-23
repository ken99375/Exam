<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム - クラス情報変更</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>

            <form method="post" action="ClassUpdateExecute.action" class="px-4">

            	<div class="mb-3">
	            	<c:if test="${ empty errors['dele']}">
	    				<label for="classnum" class="form-label">クラスコード</label>
	    				<div>${classInfo.classNum}</div>
	    					<input type="hidden" class="form-control" id="classnum" name="classnum" value="${classInfo.classNum}" />
						<c:if test="${errors.classnum != null}">
	                        <div class="text-danger">${errors.classnum}</div>
	                    </c:if>
					</c:if>
				</div>


                <c:if test="${not empty errors['dele']}">
					<div class="mb-3">
    					<label for="classnum" class="form-label">クラスコード</label>
	    				<div>${param.classnum}</div>
	    				<input type="hidden" class="form-control" id="classnum" name="classnum" value="${classInfo.classNum}" />
						<div class="mt-2 text-warning">
							 ${errors['dele']}
						</div>
					</div>
				</c:if>

                <div class="mb-3">
                    <label for="name" class="form-label">クラス名</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="${not empty name ? name : classInfo.name}"
                           placeholder="クラス名を入力してください" required maxlength="20" />
                    <c:if test="${errors.name != null}">
                        <div class="text-danger">${errors.name}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">変更</button>
                </div>
                <div>
                    <a href="ClassList.action">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>