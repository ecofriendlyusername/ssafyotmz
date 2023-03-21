package com.patandmat.otmz.domain.auth.application;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${oauth.kakao.authenticationUrl}")
    private String authenticationUrl;

    @Value("${oauth.kakao.authorizationUrl}")
    private String authorizationUrl;

    @Value("${oauth.kakao.grantType}")
    private String grantType;

    @Value("${oauth.kakao.clientId}")
    private String clientId;

    @Value("${oauth.kakao.redirectUri}")
    private String redirectUri;

    private final MemberRepository memberRepository;

    public Map<String, Object> getKakaoAccessToken(String code) {
        Map<String, Object> map = new HashMap<>();

        try {
            URL url = new URL(authenticationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            String queries =
                    "grant_type=" + grantType +
                    "&client_id=" + clientId +
                    "&redirect_uri=" + redirectUri +
                    "&code=" + code;

            bw.write(queries);
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("responseCode: {}", responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            log.info("response body: {}", result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            String accessToken = element.getAsJsonObject().get("access_token").getAsString();
            String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            map.put("access_Token", accessToken);
            map.put("refresh_Token", refreshToken);

            log.info("access_token: {}", accessToken);
            log.info("refresh_token: {}", refreshToken);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }


    public Member getKakaoUser(String token) {
        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(authorizationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.info("responseCode: {}", responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            log.info("response body: {}", result.toString());

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            long id = element.getAsJsonObject().get("id").getAsLong();
            String profileImg = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            log.info("id: {}", id);
            log.info("profileImg: {}", profileImg);
            log.info("nickname: {}", nickname);

            br.close();

            return Member
                    .builder()
                    .authId(id)
                    .nickname(nickname)
                    .profileImagePath(profileImg)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loginOrJoin(Member member) {
        Member joinedMember = memberRepository.findByAuthId(member.getAuthId());

        if (joinedMember == null) {
            //회원가입
            memberRepository.save(member);
        } else if (joinedMember.isDeleted()) {
            joinedMember.restore();
            joinedMember.setNickname(member.getNickname());
            memberRepository.save(joinedMember);
            //(재가입)로그인
        }
    }
}
