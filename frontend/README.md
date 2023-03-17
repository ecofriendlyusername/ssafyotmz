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
- 결과 공유하기
- 결과 계정에 저장하기
- etc...

### 2023.03.15
몸이...아프다...

죽겠네

워터마크js 이용해서 이미지에 워터마크 찍어보는 중

잘 되지 않는다.

### 2023.03.16
차근차근 canvas 이용해서 먼저 워터마크를 찍어 보았다.

텍스트 외에 입력하기가 쉽지 않다.

한번 해 보니 워터마크 js 사용이 수월해졌다.

상태를 굳이 사용하지 않고, 시작 페이지에서 모달로 결과창을 띄우는 방식을 생각해 봤다. 그렇게 한다면 store에 저장하고 불러오는 것 없이 한번에 처리할 수 있을 것이다. 비쥬얼적으로도 나쁘지 않을 듯?