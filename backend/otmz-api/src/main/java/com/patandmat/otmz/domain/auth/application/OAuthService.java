package com.patandmat.otmz.domain.auth.application;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.patandmat.otmz.domain.auth.api.model.MemberInfoFromKakao;
import com.patandmat.otmz.domain.auth.api.model.TokenResponse;
import com.patandmat.otmz.domain.imageFile.application.ImageFileService;
import com.patandmat.otmz.domain.imageFile.entity.ImageFile;
import com.patandmat.otmz.domain.item.application.InitItemCopyService;
import com.patandmat.otmz.domain.item.entity.Item;
import com.patandmat.otmz.domain.item.repository.ItemRepository;
import com.patandmat.otmz.domain.member.application.MemberService;
import com.patandmat.otmz.domain.member.entity.Member;
import com.patandmat.otmz.domain.member.repository.MemberRepository;
import com.patandmat.otmz.global.auth.JwtUtil;
import com.patandmat.otmz.global.exception.NoSuchMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


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

    private final InitItemCopyService initItemCopyService;

    private final ItemRepository itemRepository;

    private final MemberService memberService;

    private final ImageFileService imageFileService;

    public TokenResponse getKakaoTokens(String code, String redirectUri) {
        if (redirectUri == null) {
            redirectUri = this.redirectUri;
        }

        String accessToken = null;
        String refreshToken = null;

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

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("access_token: {}", accessToken);
            log.info("refresh_token: {}", refreshToken);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TokenResponse(accessToken, refreshToken);
    }


    public MemberInfoFromKakao getKakaoUser(String token) {
        try {
            URL url = new URL(authorizationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty(JwtUtil.HEADER_KEY, JwtUtil.TOKEN_PREFIX + token);

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

            return MemberInfoFromKakao
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
    public Member loginOrJoin(MemberInfoFromKakao memberInfoFromKakao) {
        Member joinedMember = memberRepository.findByAuthId(memberInfoFromKakao.getAuthId());

        if (joinedMember == null) {
            //회원가입
            joinedMember = memberRepository.save(memberInfoFromKakao.toEntity());

            try {
                initItemCopyService.setTestItemsForThisUser(joinedMember);
            } catch (IOException e) {
                // not critical
                System.out.println("Failed to initialize test data");
            }
        } else if (joinedMember.isDeleted()) {
            joinedMember.restore();
            joinedMember.setNickname(memberInfoFromKakao.getNickname());
            memberRepository.save(joinedMember);

            try {
                initItemCopyService.setTestItemsForThisUser(joinedMember);
            } catch (IOException e) {
                // not critical
                System.out.println("Failed to initialize test data");
            }
            //(재가입)로그인
        }

        return joinedMember;
    }
}
