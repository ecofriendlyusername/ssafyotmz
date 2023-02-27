# Git-flow 전략

프로젝트를 진행하면서 git 브랜치 전략으로 git-flow 전략을 사용하게 되었다. 알고 있는 개념이지만 팀원들과의 정보 공유를 위해 간단히 정리해봤다.

### Branch 전략?

브랜치 전략이란 **브랜치 생성에 규칙을 만들어서 협업을 유연하게 하는 방법론**이다.

이전 프로젝트를 진행하면서 빠른 개발을 위해서 Git 브랜치 전략을 무시한 채로 개발을 진행했다가 수많은 컨플릭트에 휩싸여 오히려 이슈를 해결하는데 더 많은 시간을 쏟은 슬픈 사연이 있다.

이처럼 Branch 전략은 Git을 사용하는 협업 환경에서 올 수 있는 혼란스러운 문제를 최대한 줄이는데 필수적이라고 할 수 있다.

### Git-flow 전략?

브랜치 전략에는 몇 가지 전략이 있는데 이 글에서는 Git-flow 전략에 대해서만 알아보기로 하자.

Git-flow에는 5가지 종류의 브랜치가 존재한다.

항상 유지되는 메인 브랜치들(master, develop)과 일정 기간 동안만 유지되는 보조 브랜치들(feature, release, hotfix)이 있다.

- master : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치

![git-flow_overall_graph](https://techblog.woowahan.com/wp-content/uploads/img/2017-10-30/git-flow_overall_graph.png)

git-flow 전략은 다음과 같은 플로우로 진행된다.

- develop 브랜치는 master에서부터 시작된 브랜치로, develop 브랜치에서는 상시로 버그를 수정한 커밋들이 추가됨
- 새로운 기능 추가 작업이 있는 경우 develop 브랜치에서 feature 브랜치를 생성함
- feature 브랜치는 언제나 develop 브랜치에서부터 시작함
- 기능 추가 작업이 완료되었다면 feature 브랜치는 develop 브랜치로 merge됨
- develop에 이번 버전에 포함되는 모든 기능이 merge 되었다면 QA를 하기 위해 develop 브랜치에서부터 release 브랜치를 생성함
- QA를 진행하면서 발생한 버그들은 release 브랜치에 수정됨
- QA를 무사히 통과했다면 release 브랜치를 master와 develop 브랜치로 merge
- 마지막으로 출시된 master 브랜치에서 버전 태그를 추가

### 끝으로...

사실 개발을 하는 과정에 이렇게 정해진 브랜치대로 이뤄질지는 모르겠다. 여러 시행 착오를 통해서 맞춰가야하는 부분이 분명 존재하고, 너무 얽매일 필요없이 **효율적인 협업**이라는 목표에 포커싱하여 유도리있게 진행할 필요도 있을 것 같다.

### [참고]

- https://inpa.tistory.com/entry/GIT-%E2%9A%A1%EF%B8%8F-github-flow-git-flow-%F0%9F%93%88-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EC%A0%84%EB%9E%B5
- https://techblog.woowahan.com/2553/
