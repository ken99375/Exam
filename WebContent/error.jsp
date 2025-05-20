<%-- 学生一覧jsp --%>
<%@page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<head><link href="css/sb-admin-2.min.css" rel="stylesheet"></head>
<c:import url = "/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name = "styles">
		<style>
			.error {
			  color: #5a5c69;
			  font-size: 7rem;
			  position: relative;
			  line-height: 1;
			  width: 12.5rem;
			} /* 定義元: sb-admin-2.css :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1} */

			/* 左右のノイズ（青）のレイヤー */
			.error::before {
			  content: attr(data-text);
			  position: absolute;
			  left: -2px;
			  top: 0;
			  text-shadow: 1px 0 #4e73df;
			  color: #5a5c69;
			  background: #f8f9fc;
			  overflow: hidden;
			  clip: rect(0, 900px, 0, 0);
			  animation: noise-anim-2 3s infinite linear alternate-reverse;
			} /* 定義元: sb-admin-2.css :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3} */

			/* 左右のノイズ（赤）のレイヤー */
			.error::after {
			  content: attr(data-text);
			  position: absolute;
			  left: 2px;
			  top: 0;
			  text-shadow: -1px 0 #e74a3b;
			  color: #5a5c69;
			  background: #f8f9fc;
			  overflow: hidden;
			  clip: rect(0, 900px, 0, 0);
			  animation: noise-anim 2s infinite linear alternate-reverse;
			} /* 定義元: sb-admin-2.css :contentReference[oaicite:4]{index=4}&#8203;:contentReference[oaicite:5]{index=5} */

			/* ノイズアニメーション（赤レイヤー） */
			@keyframes noise-anim {
			  0%   { clip: rect(49px, 9999px, 40px, 0); }
			  5%   { clip: rect(75px, 9999px, 72px, 0); }
			  10%  { clip: rect(97px, 9999px, 93px, 0); }
			  /* …中略… */
			  100% { clip: rect(28px, 9999px, 53px, 0); }
			} /* 定義元: sb-admin-2.css :contentReference[oaicite:6]{index=6}&#8203;:contentReference[oaicite:7]{index=7} */

			/* ノイズアニメーション（青レイヤー） */
			@keyframes noise-anim-2 {
			  0%   { clip: rect(16px, 9999px, 10px, 0); }
			  5%   { clip: rect(22px, 9999px, 29px, 0); }
			  10%  { clip: rect(6px, 9999px, 68px, 0); }
			  /* …中略… */
			  100% { clip: rect(67px, 9999px, 68px, 0); }
			} /* 定義元: sb-admin-2.css :contentReference[oaicite:8]{index=8}&#8203;:contentReference[oaicite:9]{index=9} */
		</style>
	</c:param>


	<c:param name="content">
		<section class ="me-4">
		<!-- Begin Page Content -->
			<div id="content">
				<!-- Begin Page Content -->
					<div class="container-fluid">
		            	<!-- 404 Error Text -->
		                <div class="text-center">
		                	<div class="error mx-auto" data-text="404">404</div>
		                    <p class="lead text-gray-800 mb-5">Page Not Found</p>
		                    <p class="text-gray-500 mb-0">もう一度メニューからやり直してください...</p>
		                    <a href="Menu.action">&larr; メニューへ戻る</a>
		                </div>
		        	</div>
		    	<!-- /.container-fluid -->
			</div>
		</section>
		<!-- Bootstrap core JavaScript-->


	</c:param>
	<c:param name="scripts">
			<script src="assets/vendor/jquery/jquery.min.js"></script>
		    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
		    <!-- Core plugin JavaScript-->
		    <script src="assets/vendor/jquery-easing/jquery.easing.min.js"></script>
		    <!-- Custom scripts for all pages-->
		    <script src="assets/js/sb-admin-2.min.js"></script>
		</c:param>
</c:import>