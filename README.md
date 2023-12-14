# 지리 기반 맛집 추천 서비스
![title](./readme_source/title/logo.png)
## 팀 소개
<div align="center">

| <img src="./readme_source/team/team1.png" width="140" height="140">  |        <img src="./readme_source/team/team2.png" width="140" height="140">         |  <img src="./readme_source/team/team3.png" width="140" height="140">  |  <img src="./readme_source/team/team4.png" width="140" height="140">  |  
|:--------------------------------------------------------------------:|:----------------------------------------------------------------------------------:|:---------------------------------------------------------------------:|:---------------------------------------------------------------------:|  
|                               Back-End                               |                                      Back-End                                      |                               Back-End                                |                               Back-End                                |                                                                                                 |
|                  [이준규](https://github.com/junkyu92)                  |                         [이현정](https://github.com/12hyeon)                          |                  [최승호](https://github.com/madst0614)                  |                  [조현수](https://github.com/HyunsooZo)                  |

</div>

## 목차
- [개요](#개요)
- [사용기술](#사용기술)
- [느낀 점](#느낀-점)
- [API 문서](#API-문서)
- [구현 기능](#구현기능)
- [시스템 구성도](#시스템-구성도)
- [ERD](#ERD)



## 개요

본 서비스는 공공 데이터를 활용하여, 지역 음식점 목록을 자동으로 앱에 업데이트 합니다 .<br>
사용자는 `현재 위치` 또는 `원하는 위치`를 설정 하여 `맛집 및 메뉴를 추천` 받을 수 있습니다. <br>
또한 `리뷰 시스템`을 통해 더 나은 음식 경험을 제공하고, 다른 사람과 음식에 관해 소통을 하고 공유를 받는 경험을 할 수 있습니다.<br>


## 프로젝트 일정
[![Notion](https://img.shields.io/badge/Notion_문서로_확인하기_(클릭!)-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)](https://www.notion.so/43d02b90078343ffbf2d8988d81b67c5?v=bcf39f768aeb4ce180c288b5edfe43ef)

## 사용기술

#### 개발환경
<img src="https://img.shields.io/badge/java-007396?&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring boot-6DB33F?&logo=Spring boot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?&logo=gradle&logoColor=white">
<br>
<img src="https://img.shields.io/badge/MariaDB-003545?&logo=mariaDB&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/Spring JPA-6DB33F?&logo=Spring JPA&logoColor=white"> <img src="https://img.shields.io/badge/querydsl-2599ED?&logo=querydsl&logoColor=white">  <img src="https://img.shields.io/badge/SMTP-CC0000?&logo=Gmail&logoColor=white">
<br>
<img src="https://img.shields.io/badge/AssertJ-25A162?&logo=AssertJ&logoColor=white"> <img src="https://img.shields.io/badge/Mockito-008D62?&logo=Mockito&logoColor=white">
<br>
<img src="https://img.shields.io/badge/intellijidea-000000?&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/postman-FF6C37?&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/swagger-85EA2D?&logo=swagger&logoColor=white">

#### 배포환경
<img src="https://img.shields.io/badge/aws-232F3E?&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/ec2-FF9900?&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/rds-527FFF?&logo=amazonrds&logoColor=white">
<br>
<img src="https://img.shields.io/badge/github-181717?&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/github actions-2088FF?&logo=githubactions&logoColor=white" alt="actions">

#### 협업도구
<img src="https://img.shields.io/badge/discord-4A154B?&logo=discord&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?&logo=notion&logoColor=white">
<br/>

## 느낀 점
두 번째 팀 프로젝트는 Auth 기능과 User 기능을 맡았다. 스프링 시큐리티와 Jwt Token 방식 Auth 기능 구현은 처음이였기에 이전의 팀원의 코드를 참고하여 최대한 학습하였다.  
프로젝트를 진행하면서 User 도메인을 팀원들과 공유할 일이 있었는데 후에 User 도메인 네이밍을 수정하는 과정에서 팀원들과 코드가 엇갈렸고 해당 도메인 네임을 원상복구 했다.  
개발은 나 혼자 하는 일이 아니라는 것을 깨닫고 깊이 반성했다. 또한 코드 리뷰를 받으면서 팀원의 관점을 공유 했고 부족한 나의 관점을 채워나갈 수 있었다. 

## API 문서
[![Swagger](https://img.shields.io/badge/swagger_문서로_확인하기_(클릭!)-85EA2D?&logo=swagger&logoColor=white)](http://52.79.93.98:8080/swagger-ui/index.html#/)


| API Type           | Http Method | URL                                  | Description   |
|--------------------|-------------|--------------------------------------|---------------|
| **Auth API**       | POST        | `/api/v1/auth/token/access`          | 엑세스토큰 재발급     | 
| **User API**       | POST        | `/api/v1/users/sign-up`              | 회원가입          |
| **User API**       | POST        | `/api/v1/users/sign-in`              | 로그인           |
| **User API**       | POST        | `/api/v1/users/sign-out`             | 로그아웃          |
| **User API**       | PATCH       | `/api/v1/users/update-loc`           | 위치 업데이트       |
| **User API**       | PATCH       | `/api/v1/users/lunch-served`         | 점심 제공 여부 업데이트 |
| **User API**       | GET         | `/api/v1/users/info`                 | 사용자 정보 가져오기   |
| **Restaurant API** | GET         | `/api/v1/restaurants`                | 맛집 리스트        |
| **Restaurant API** | GET         | `/api/v1/restaurants/{restaurantId}` | 맛집 상세정보       |
| **Review API**     | POST        | `/api/v1/reviews`                    | 리뷰 등록         |
| **SggLatLon API**  | Post        | `/api/v1/files`                      | csv 업데이트      |
| **SggLatLon API**  | GET         | `/api/v1/files/template`             | csv 양식 다운로드   |
| **SggLatLon API**  | GET         | `/api/v1/files`           | 시군구 목록 조회     |


## 구현기능

<details>
  <summary>회원가입 및 로그인, 로그아웃 기능</summary>

- **구현 기능** <br>
    - 사용자 회원가입 및 로그인, 로그아웃 기능

- **구현 방법** <br>
    - 회원가입: 사용자 회원 양식을 받아 DB에 저장
    - 로그인: 사용자 로그인 양식을 받아 DB에 비밀번호와 비교한 후, Access Token, Refresh Token 발급
    - 로그아웃: 로그아웃 요청 시, Redis에 저장된 Refresh 토큰을 제거
</details>

<details>
  <summary>Spring Security, JWT 토큰</summary>

- **구현 기능** <br>
    - Spring Security 와 JWT

- **구현 방법** <br>
    - 사용자 로그인 시, 발급한 Refresh Token을 Redis에 저장
    - Access Token 재발급 시, Redis에 저장된 사용자 Refresh Token과 비교
    - 로그아웃 시, Redis에서 발급한 Refresh Token 제거
</details>

<details>
  <summary>회원 정보 업데이트 및 조회 기능</summary>

- **구현 기능** <br>
    - 회원 위치 정보와 점심 제공 여부를 업데이트 기능
    - 비밀번호를 제외한 회원 정보 조회 기능

- **구현 방법** <br>
    - 적절한 양식을 통해 회원 위치 정보와 점심 제공 여부를 DB에 업데이트
    - 회원 정보 조회 요청 시, 발급한 Access Token에서 id를 추출하여 DB 조회
</details>

<details>
  <summary>위치 기반 맛집 리스트 조회 기능</summary>

- **구현 기능** <br>
    - 특정 위치에서 범위 내 맛집을 조회합니다.

- **구현 방법**<br>
    - 위도, 경도, 범위를 입력받아 해당 좌표에서 범위 내 맛집을 조회합니다.
    - 거리 계산은 하버사인 공식을 이용해 쿼리내에서 진행하였습니다.
    - 기본 정렬은 거리 가까운 순으로 제공하고, 정렬 조건이 rate일 경우에 평점 높은 순으로 정렬하여 조회합니다.
    - 기본 15개로 페이징되어 제공되고 size, page 파라미터로 페이징 컨트롤 가능합니다.
</details>
<details>
  <summary>맛집 상세 정보 조회 기능</summary>

- **구현 기능** <br>
    - 맛집의 상세정보를 조회합니다.

- **구현 방법**<br>
    - 맛집ID로 맛집의 상세정보, 전체 리뷰 리스트를 조회합니다.
    - fetch join을 사용해 한번에 하위 항목까지 모두 조회합니다.
    - Redis를 사용해 캐싱을 적용하였습니다.
    - Redis에 데이터가 존재하면 Redis에서 데이터를 반환하고 존재하지 않으면 DB에서 조회하여 Redis에 저장 후 데이터를 반환합니다.

</details>
<details>
  <summary>리뷰 등록 기능</summary>

- **구현 기능** <br>
    - 리뷰를 등록합니다.

- **구현 방법**<br>
    - 평점과 리뷰 내용을 등록합니다.
    - 리뷰 등록시 맛집의 평점과 리뷰수를 업데이트 합니다.
    - 리뷰 등록시 Redis에 캐싱되어있는 해당맛집의 데이터를 삭제하여 최신화되도록 했습니다.

</details>


<details>
  <summary>식당 정보 가져오는 스케쥴링</summary>

- **구현 기능** <br>
    - 식당 정보 가져오는 스케쥴링 기능 구현

- **구현 방법** <br>
    - 총 5개 외부 api를 호출합니다.(경기도 일반음식점 _ 패스트푸드,중식,양식,뷔페,일식)
    - 모든 값을 그대로 저장하되 null값은 데이터 타입에 따라 `데이터없음`, `0` , `0.0` 으로 전처리
    - 유일키는 식당이름+지번주소 에 공백을 제거하여 사용
    - 폐업상태 식당의 경우 저장하지 않음.
    - 매일 `23:59` 스케줄링 동작
    - 저장시점에 저장 식당 종류, 시간을 로깅
    - 이미 저장된 식당의 경우 업데이트 진행
</details>
<details>
  <summary>점심추천에 동의한 고객들에게 메세지 전송하는 기능</summary>

- **구현 기능** <br>
    - 점심추천에 동의한 고객들에게 메세지 전송하는 기능 추가

- **구현 방법** <br>
    - 점심약속에 동의한 고객들의 목록을 조회
    - 고객의 좌표에 가까우며 별점이 가장높은 5개 카테고리의 식당 5개씩, 총 25개 조회(`QueryDSL`,하버사인 공식 사용)
    - 5개씩 한 `embed`에 묶어 `DiscordWebhook` 으로 전송할 메세지 객체 생성
    - 조회된 고객순서대로 메세지 전송 `DiscordWebhook` 호출
        - 메시지 예시(각 카테고리별 5개씩)
          > 오늘의 추천 일식
          <br><strong>산(뼈찜,뼈곰탕)</strong>
          <br>경기도 평택시 탄현로1번길 11, 101,102호 (장당동, 엘림하우스)
          <br> <strong>스고이</strong>
          <br> 경기도 평택시 고덕갈평7길 10, 1층 (고덕동)
          <br> <strong>광명회수산</strong>
          <br> 경기도 평택시 현촌4길 2-33, 101호 (용이동)
          <br> <strong>오늘은참치</strong>
          <br> 경기도 시흥시 옥구천동로 449, 부성파스텔아파트 상가동 1층 105호 (정왕동)
          <br> <strong>장군수산</strong>
          <br> 경기도 오산시 오산로160번길 5-6, 102,103,104호 (원동, 건정프라자)
</details>
<details>
  <summary>시군구 데이터를 csv파일로 업데이트하는 기능</summary>

- **구현 기능** <br>
    - 시군구 데이터를 `.csv`파일로 업데이트하는 기능 추가
    - 시군구 데이터양식 `.csv`파일을 다운로드하는 기능 추가

- **구현 방법** <br>
    - **파일업로드**<br>
      a. 각 라인이 null이 아닐때까지 읽어 가며 각 셀을 "," 로 구분하여 배열로 변환<br>
      b. 배열의 각 요소로 SggLatLon 객체를 생성해 저장<br>
      c. 예외 발생시 로그 적재
    - **파일다운로드** <br>
      a. 도,시,위도,경로 로 이루어진 양식을 생성<br>
      b. 해당파일을 InputStream으로 변환<br>
      c. InputStream을 다시 InputStreamResource로 변환<br>
      d. sgg-template.csv 파일 반환
</details>

<details>
  <summary>시군구 목록 조회 기능</summary>

- **구현 기능** <br>
    - 시군구 목록 조회기능 추가 (캐싱 적용)

- **구현 방법** <br>
    - 성능 개선 및 동시성 처리 목적으로 캐싱 적용.
    - 만료일은 1일, 키는 `String`으로 직렬화, 값은 `Json`으로 직렬화
    - 필요한 메서드에서 어노테이션을 사용해 캐싱 진행
    - 시군구 목록을 조회해오는 기능을 추가 (캐싱적용 `@Cacheable`,`@CacheEvict`)
    - 해당 캐시는 1일 유효하며 만약 CSV파일이 업로드 될 시 캐시 초기화
</details>


<details>
  <summary>CI 구축</summary>

- **구현 기능** <br>
    - Github Actions를 통해 main의 pr과 push, dev의 pr 생성시 빌드, 테스트 자동화

- **구현 방법**<br>
![CI1](/readme_source/ci_cd/CI1.png)
</details>

<details>
  <summary>CD 구축</summary>

- **구현 기능** <br>
    - main branch의 push 동작이 발생하면, aws 인증 후 s3를 통해 배포

- **구현 방법**<br>
    - CodeDeploy를 통해 배포 자동화
![cd1](/readme_source/ci_cd/CD1.png)

    - 위 설정을 통해 jar 파일 실행
    ![cd2](/readme_source/ci_cd/CD2.png)

</details>

## 시스템 구성도
![시스템 구성도](./readme_source/system_diagram/system_diagram.png)

## ERD
![ERD](./readme_source/erd/tastyspot.png)



