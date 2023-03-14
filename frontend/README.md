# frontend

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).


## 개발일지

#### 2023.03.07
카카오 로그인 기능 연동
환경변수에 api키 숨김처리 기능 적용(예정)
모바일 웹이라는 상황으로 인한 API KEY 유출 문제 해결을 위한 방안 - 'https://www.youtube.com/watch?v=wAWOOBUAclc'

#### 2023.03.08
아직도 카카오 로그인
환경변수는 나중에 

### 2023.03.14
메인 기능 페이지 만드는 중
라우터 구성을 3개로 생각했는데, 로딩창을 모달로 만드는 편이 더 나을 것 같다.
페이지는 이미지 업로드 - 로딩 - 결과 로 구성되는 게 나을듯

이미지 업로드 창:
    - 이미지 input 
    - 이미지 미리보기
    - 서버에 파일 전송(axios)
    - .then으로 파일 전송 되면 state에 이미지 파일 저장
    - .then으로 제일 높은 판별 결과 state에 저장

결과 창:
    - getter 이용해서 이미지랑 판별 결과 get, 
    - watermakr.js 이용해서 이미지에 워터마크랑 판별 결과 set
    = 결과 공유하기
    = 결과 계정에 저장하기
    = etc...
