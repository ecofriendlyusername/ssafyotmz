# Git-flow

## 왜 사용하는가?

협업 과정 중 개발자들 간의 코드 충돌을 최소화 하기 위해서


<hr>

## 구조
5가지의 브랜치를 사용해서 운영: master, develop, feature, release, hotfix

- master : 기준, 제품 배포
- develop : 개발 브랜치, 이 브랜치를 기준으로 각자 작업한 기능들 Merge
- feature : 단위 기능을 개발 브랜치, 완료되면 develop 브랜치에 Merge
- release : master 브랜치로 보내기 전에 먼저 품질검사를 하기위한 브랜치
- hotfix : 배포 후 긴급 수정


<hr>

## 사용법

```
# git flow 시작
git flow init -d

# feature 시작/완료/develop에 병합
git flow feature start {branch_name}
git flow feature finish {branch_name}
git flow feature publish {branch_name}

# release, hotfix 시작/완료
git flow {release/hotfix} start {branch_name}
git flow {release/hotfix} finish {branch_name}
```

<hr>

## 참고 

https://techblog.woowahan.com/2553/

https://blog.naver.com/comstering/222730571692