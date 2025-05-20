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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績ダッシュボード</h2>
			<form action = "DashBordExecute.action" method="get">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-3">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="filter-ent-year" name="ent_year">

                            <c:forEach var="year" items="${ent_year_set}">
                                <%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
                                <option value="${year }" <c:if test="${year == f1 }">selected</c:if>>${year }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="filter-class-num" name="class_num">
                            <c:forEach var="num" items="${c_list}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num.classNum }" <c:if test="${num.classNum == f2 }">selected</c:if>>${num.classNum }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-3">
                        <label class="form-label" for="student-f2-select">科目</label>
                        <select class="form-select" id="filter-subject" name="cd">

                            <c:forEach var="num" items="${sub_list}">
                                <%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
                                <option value="${num.cd }" >${num.name }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="student-f2-select">回数</label>
                        <select class="form-select" id="filter-times" name="times">
                                <option value= "1" >1</option>
                                <option value = "2">2</option>
                        </select>
                    </div>

                    <div class="col-2 text-center">
                        <button class="btn btn-secondary" id="filter-button">検索</button>
                    </div>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>


			<!-- Main Content -->
	            <div id="content">
	                <!-- Begin Page Content -->
	                <div class="container-fluid">
	                    <!-- Content Row -->
	                    <div class="row">

	                        <div class="col-xl-8 col-lg-7">
	                            <!-- Bar Chart -->
	                            <div class="card shadow mb-4">
	                                <div class="card-header py-3">
	                                    <h6 class="m-0 font-weight-bold text-primary">点数別人数</h6>
	                                </div>
	                                <div class="card-body">
	                                    <div class="chart-bar">
	                                        <canvas id="myBarChart"></canvas>
	                                    </div>
	                                    <hr>
										<p>平均点：${barDashbord.avg}点</p><p>最高点：${barDashbord.max}点</p><p>最下点：${barDashbord.min}点</p>
	                                </div>
	                            </div>
	                        </div>

	                        <!-- Donut Chart -->
	                        <div class="col-xl-4 col-lg-5">
	                            <div class="card shadow mb-4">
	                                <!-- Card Header - Dropdown -->
	                                <div class="card-header py-3">
	                                    <h6 class="m-0 font-weight-bold text-primary">人数割合</h6>
	                                </div>
	                                <!-- Card Body -->
	                                <div class="card-body">
	                                    <div class="chart-pie pt-4">
	                                        <canvas id="myPieChart"></canvas>
	                                    </div>
	                                    <hr>
										<p>平均点：${paiDashbord.avg}点</p><p>最高点：${paiDashbord.max}点</p><p>最下点：${paiDashbord.min}点</p>
	                                </div>
	                            </div>
	                        </div>

	                    </div>
	                </div>
	                <!-- /.container-fluid -->
	            </div>
	            <!-- End of Main Content -->
            <!-- Page level plugins -->
		    <script src="../../assets/vendor/chart.js/Chart.min.js"></script>


		    <!-- Page level custom scripts -->
		    <script>
			 // Set new default font family and font color to mimic Bootstrap's default styling
			    Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
			    Chart.defaults.global.defaultFontColor = '#858796';

			    // Pie Chart Example
			    var ctx = document.getElementById("myPieChart");
			    var myPieChart = new Chart(ctx, {
			      type: 'doughnut',
			      data: {
			        labels: [
			        	<c:forEach var = "rc" items = "${paiDashbord.distribution}" varStatus = "st">
			        		'${rc.lower}~${rc.upper}点'<c:if test = "${!st.last}">, </c:if>
			        	</c:forEach>
			        ],
			        datasets: [{
			        	data: [
				        	  <c:forEach var ="rc" items = "${paiDashbord.distribution}" varStatus = "st">
				        		${rc.count}<c:if test = "${!st.last}">, </c:if>
				        	</c:forEach>
				          ],
				          backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
				          hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
				          hoverBorderColor: "rgba(234, 236, 244, 1)"
			        }],
			        },
			      options: {
			        maintainAspectRatio: false,
			        tooltips: {
			          backgroundColor: "rgb(255,255,255)",
			          bodyFontColor: "#858796",
			          borderColor: '#dddfeb',
			          borderWidth: 1,
			          xPadding: 15,
			          yPadding: 15,
			          displayColors: false,
			          caretPadding: 10,
			        },
			        legend: {
			          display: false
			        },
			        cutoutPercentage: 80,
			      },
			    });
		    </script>
		    <script>
			 // Set new default font family and font color to mimic Bootstrap's default styling
			    Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
			    Chart.defaults.global.defaultFontColor = '#858796';
			    function number_format(number, decimals, dec_point, thousands_sep) {
			      // *     example: number_format(1234.56, 2, ',', ' ');
			      // *     return: '1 234,56'
			      number = (number + '').replace(',', '').replace(' ', '');
			      var n = !isFinite(+number) ? 0 : +number,
			        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
			        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
			        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
			        s = '',
			        toFixedFix = function(n, prec) {
			          var k = Math.pow(10, prec);
			          return '' + Math.round(n * k) / k;
			        };
			      // Fix for IE parseFloat(0.55).toFixed(0) = 0;
			      s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
			      if (s[0].length > 3) {
			        s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
			      }
			      if ((s[1] || '').length < prec) {
			        s[1] = s[1] || '';
			        s[1] += new Array(prec - s[1].length + 1).join('0');
			      }
			      return s.join(dec);
			    }

			    // Bar Chart Example
			    var ctx = document.getElementById("myBarChart");
			    var myBarChart = new Chart(ctx, {
			      type: 'bar',
			      data: {
			        labels: [
			        	<c:forEach var ="rc" items = "${barDashbord.distribution}" varStatus = "st">
			        		'${rc.lower}~${rc.upper}点'<c:if test = "${!st.last}">, </c:if>
			        	</c:forEach>
			        ],
			        datasets: [{
			          label: "Revenue",
			          backgroundColor: "#4e73df",
			          hoverBackgroundColor: "#2e59d9",
			          borderColor: "#4e73df",
			          data: [
			        	  <c:forEach var ="rc" items = "${barDashbord.distribution}" varStatus = "st">
			        		${rc.count}<c:if test = "${!st.last}">, </c:if>
			        	</c:forEach>
			          ]
			        }]
			      },
			      options: {
			        maintainAspectRatio: false,
			        layout: {
			          padding: {
			            left: 10,
			            right: 25,
			            top: 25,
			            bottom: 0
			          }
			        },
			        scales: {
			          xAxes: [{
			            time: {
			              unit: 'month'
			            },
			            gridLines: {
			              display: false,
			              drawBorder: false
			            },
			            ticks: {
			              maxTicksLimit: 6
			            },
			            maxBarThickness: 25,
			          }],
			          yAxes: [{
			            ticks: {
			              min: 0,
			              max: 60,
			              maxTicksLimit: 5,
			              padding: 10,
			              // Include a dollar sign in the ticks
			              callback: function(value, index, values) {
			                return number_format(value);
			              }
			            },
			            gridLines: {
			              color: "rgb(234, 236, 244)",
			              zeroLineColor: "rgb(234, 236, 244)",
			              drawBorder: false,
			              borderDash: [2],
			              zeroLineBorderDash: [2]
			            }
			          }],
			        },
			        legend: {
			          display: false
			        },
			        tooltips: {
			          titleMarginBottom: 10,
			          titleFontColor: '#6e707e',
			          titleFontSize: 14,
			          backgroundColor: "rgb(255,255,255)",
			          bodyFontColor: "#858796",
			          borderColor: '#dddfeb',
			          borderWidth: 1,
			          xPadding: 15,
			          yPadding: 15,
			          displayColors: false,
			          caretPadding: 10,
			          callbacks: {
			            label: function(tooltipItem, chart) {
			              var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
			              return '人数： ' + number_format(tooltipItem.yLabel) + '人';
			            }
			          }
			        },
			      }
			    });
		</script>
	</section>
   </c:param>
</c:import>