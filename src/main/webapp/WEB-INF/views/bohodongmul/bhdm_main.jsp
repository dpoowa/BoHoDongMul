<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoHoDongMul :: 보호동물</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${apiKey }&libraries=services"></script>
<link rel="stylesheet" href="/static/css/BHDM/BHDM_main.css" />
<link rel="icon" href="/static/icon/favicon.ico" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
</head>
<body>
	<!-- 입양센터 메인 -->
	<div id="adoption-main" class="on">
		<!-- 필터 div -->
		<div id="filter_form">
			<div>
				<span>필터로 인연 찾아보기 </span> <br /> <br />
				<form id="paramFilter" action="#">
					<!-- 도시 -->
					<select name="upr_cd" id="upr_cd">
						<option value='any' selected>시, 도</option>
						<option value='6110000'>서울특별시</option>
						<option value='6260000'>부산광역시</option>
						<option value='6270000'>대구광역시</option>
						<option value='6280000'>인천광역시</option>
						<option value='6290000'>광주광역시</option>
						<option value='5690000'>세종특별자치시</option>
						<option value='6300000'>대전광역시</option>
						<option value='6310000'>울산광역시</option>
						<option value='6410000'>경기도</option>
						<option value='6530000'>강원특별자치도</option>
						<option value='6430000'>충청북도</option>
						<option value='6440000'>충청남도</option>
						<option value='6540000'>전북특별자치도</option>
						<option value='6460000'>전라남도</option>
						<option value='6470000'>경상북도</option>
						<option value='6480000'>경상남도</option>
						<option value='6500000'>제주특별자치도</option>
					</select>
					<!-- 시/군/구 선택 -->
					<select id="org_cd" name="org_cd">
						<option value="any" selected>시, 군, 구</option>
						<option value="any">지역을 먼저 골라주세요</option>
					</select>
	
					<!-- 동물 종류 선택 -->
					<select id="upkind" name="upkind">
						<option value="any" selected>동물종류</option>
						<option value="417000">강아지</option>
						<option value="422400">고양이</option>
						<option value="429900">기타</option>
					</select>
		
					<!-- 검색 버튼 -->
					<button type="button" id="filterSubmit">검색</button>
					<button type="button" id="filterReset">선택 초기화</button>
				</form>
			</div>
		</div>
		<div class="adoption-container" id="adoptionContainer">
			<!-- 데이터 테이블 -->
		</div>
	
		<div class="pagination" id="pagination">
			<!-- 페이징 -->
		</div>
	</div>
	<!-- 입양센터 메인 종료 -->

	<!-- 입양센터 상세페이지 -->
	<div id="adoption-detail" class="off">
		<!-- 안내 텍스트 -->
		<div id="infoText">
			<!-- 클릭한 동물이 보호중인 보호소 안내 -->
		</div>
	
		<div id="animalImage">
			<!-- 클릭한 동물의 이미지 -->
		</div>
	
		<!-- 상세 정보 테이블 -->
		<table id="selectedAnimalTable">
			<!-- 클릭한 동물의 상세 정보 테이블 -->
		</table>
		
		<!-- 목록으로 돌아가는 버튼 -->
		<button type="button" id="goMain">목록으로</button>
	</div>
	<!-- 입양센터 상세페이지 종료 -->
	<script type="module" src="/static/js/BHDM/BHDM_main.js"></script>
</body>
</html>