

	<!-- S : 회원정보 수정/탈퇴 페이지 진입 시 쿠키 초기화 및 저장 -->
	$.cookie('kakaoUserSecedeAuth', null, {path:"/"});
	$.cookie('kakaoUserEditAuth', null, {path:"/"});

	<!-- E : 회원정보 수정/탈퇴 페이지 진입 시 쿠키 저장 -->

	// 사용할 앱의 JavaScript 키를 설정해 주세요.
	Kakao.init('36c28184a9bcf51bb25ed2eed79d8cfa');

	/**
	 * 카카오 로그인
	 */
	function loginWithKakao() {
		$.cookie('kakao_prev_uri', '/users/login', {path:"/"});	// 카카오 호출 전 현재 페이지 저장

		Kakao.Auth.authorize({
			redirectUri : 'https://www.jestina.co.kr' + '/sns-user/kakao-callback'
		});
	}

	/**
	 * 카카오 로그인 인증하기
	 */
	function authWithKakao() {
		$.cookie('kakao_prev_uri', '/users/login', {path:"/"});	// 카카오 호출 전 현재 페이지 저장

		Kakao.Auth.authorize({
			redirectUri : 'https://www.jestina.co.kr' + '/sns-user/kakao-callback'
		});
	}

	/**
	 * 카카오싱크 회원가입
	 */
	function joinWithKakao() {
		$.cookie('kakao_prev_uri', '/users/login', {path:"/"});	// 카카오 호출 전 현재 페이지 저장
		$.cookie('joinWithKakao', 'Y', {path:"/"});

		Kakao.Auth.authorize({
			redirectUri : 'https://www.jestina.co.kr' + '/sns-user/kakao-callback'
		});
	}

	/**
	 * 카카오 로그인 연동
	 * @param snsId
	 */
	function connectKakao() {
		$.cookie('kakao_prev_uri', '/users/login', {path:"/"});	// 카카오 호출 전 현재 페이지 저장
		$.cookie('kakaoConnectType', 'Y', {path:"/"});	// 연동(Y)/해제(N) 구분

		Kakao.Auth.authorize({
			redirectUri : 'https://www.jestina.co.kr' + '/sns-user/kakao-connect-callback'
		});
	}

	/**
	 * 카카오 로그인 연동 해제
	 * @param snsId
	 */
	function disconnectKakao(snsId) {
		if (!confirm("SNS의 연결해제를 진행하시겠습니까?")) {
			return false;
		}

		var param = {'snsType' : 'kakao', 'snsDetailId' : snsId};

		$.post("/sns-user/checkSnsJoinAndConnect", param, function(response){
			if (response.status == "01") {
				alert(response.message);
			} else {
				$.cookie('kakao_prev_uri', '/users/login', {path:"/"});	// 카카오 호출 전 현재 페이지 저장
				$.cookie('kakaoConnectType', 'N', {path:"/"});	// 연동(Y)/해제(N) 구분

				Kakao.Auth.authorize({
					redirectUri : 'https://www.jestina.co.kr' + '/sns-user/kakao-connect-callback'
				});
			}
		});
	}
