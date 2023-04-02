package com.patandmat.otmz.domain.codiBoard.api;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class LiveCodiBoardSharingController {


    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    private final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";

    private final Map<String, String> activeInviteCodes = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    @PostMapping("/codiboard/live/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    @PostMapping("/codiboard/live/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    @PostMapping("/codiboard/live/sessions/{sessionId}/inviteCodes")
    public ResponseEntity<String> createInviteCode(@PathVariable("sessionId") String sessionId) {
        var stringLength = 6;
        StringBuilder randomCode;

        while (true) {
            randomCode = new StringBuilder();

            for (int i = 0; i < stringLength; i++) {
                int index = (int) Math.floor(Math.random() * CHARS.length());
                randomCode.append(CHARS.charAt(index));
            }

            if (!activeInviteCodes.containsKey(randomCode.toString())) {
                activeInviteCodes.put(randomCode.toString(), sessionId);
                break;
            }
        }

        return new ResponseEntity<>(randomCode.toString(), HttpStatus.OK);
    }

    @GetMapping("/codiboard/live/inviteCodes/{inviteCode}")
    public ResponseEntity<String> getSessionId(@PathVariable("inviteCode") String inviteCode) {

        String sessionId = activeInviteCodes.get(inviteCode);

        if (sessionId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    @DeleteMapping("/codiboard/live/inviteCodes/{inviteCode}")
    public ResponseEntity<Void> deleteInviteCode(@PathVariable("inviteCode") String inviteCode) {
        activeInviteCodes.remove(inviteCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}