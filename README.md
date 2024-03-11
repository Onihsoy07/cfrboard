# CFR 게시판
네이버 CFR(CLOVA Face Recognition) API를 활용한 닮은 유명인 확인 및 공유를 위한 사이트입니다.


## 프로젝트 소개
네이버에서 개발한 CLOVA Face Recognition(유명인 얼굴 인식)이 얼마나 정확한지 확인하다가 이것을 활용하여 무엇을 만들까 생각하였습니다.
친구들끼리 해보고 공유하면 누가 얼마나 닮았는지 확인하면 재미있을 것 같아 만들었습니다.

## 개발 기간
 - 2023.07.09 ~ 2023.11.03


## 멤버구성
- 박준혁 : 프론트(Thymeleaf) 및 백엔드(Spring Boot 2)


## 개발환경
- java 11
- IDE : IntelliJ IDEA Community Edition 2023.2.1
- Framework : Spring Boot 2.7.13
- Database : MySQL 8.0.32


## 목차
[1. 시스템 아키텍처](#시스템-아키텍처)

[2. 엔티티 다이어그램](#엔티티-다이어그램)

[3. 주요 기능 시퀀스 다이어그램](#주요-기능-시퀀스-다이어그램)

[4. 주요 기능](#주요-기능)

[5. 에러 해결](#문제-해결)

[6. 웹 사이트 화면](#웹-사이트-화면)




## 시스템 아키텍처
![스크린샷 2024-02-25 110319](https://github.com/Onihsoy07/cfrboard/assets/84126411/340a7500-0e01-44b0-a04c-b8dec882e00b)



## 엔티티 다이어그램
![image](https://github.com/Onihsoy07/cfrboard/assets/84126411/dbd11283-2cd5-4d31-91f0-fbfd71698fca)



## 주요 기능 시퀀스 다이어그램

- 로그인부터 닮은 유명인 얼굴 확인까지

![image](https://github.com/Onihsoy07/cfrboard/assets/84126411/3348712c-c709-4b90-9cb1-fc5f5613809e)



## 주요 기능
### 로그인 및 회원가입
- Spring Seucrity FormLogin 및 Session 사용

### CFR-닮은 유명인 확인
- RestTemplate으로 API 전송

### 게시글 및 문의글
- JPA를 사용한 CRUD
- QueryDSL을 사용한 검색(동적 쿼리)
- 스케줄러를 사용한 오늘 조회수 초기화



## 문제 해결

<details>
 <summary>Jasypt 복호화 문제</summary>

 ### 증상 : 복호화가 안되어 Database의 password를 바인딩 못함 발생

  ``` text
 Description:
 
 Failed to bind properties under 'spring.datasource.password' to java.lang.String:
 
     Reason: org.springframework.boot.context.properties.bind.BindException: Failed to bind properties under 'spring.datasource.password' to java.lang.String
 
 Action:
 
 Update your application's configuration
 ```

 jasypt 버전 3.0으로 바뀌면서 기본 알고리즘이 PBEWithMD5AndDES -> PBEWITHHMACSHA512ANDAES_256로 바뀌었으나 복호화 시 알고리즘 이슈 등의 문제로 기존 알고리즘을 사용하기 위해서 iv 생성기를 RandomIvGenerator -> NoIvGenerator로 변경을 하였습니다.

 해결 코드 : https://github.com/Onihsoy07/cfrboard/commit/96806b49147a4c1dcb81cde3407e9ca6e70ca05e
 
</details>

<details>
 <summary>Entity 기본 초기화 값 null 문제</summary>

### 증상 : @DynamicInsert에 @ColumnDefault를 Column 초기화를 해주었으나 null 저장 발생

@DynamicInsert에서 Insert 쿼리를 날릴 떄 value 값이 없으면 자동으로 그 Column은 제외하고 쿼리가 만들어지기 때문에 null 값으로 초기화 됩니다.

@PrePersist는 EntityManager의 persist가 호출되면 @PrePersist를 먼저 실행합니다.(Entity 영속화 직전 수행)

그래서 @PrePersist를 사용하여 Column을 초기화하였습니다.

해결 코드 : https://github.com/Onihsoy07/cfrboard/commit/62f18ca6476099368f2683f651720a2f53ed3190
 
</details>

<details>
 <summary>AWS RDS에 데이터 저장 시 시간 안 맞는 문제</summary>

### 증상 : AWS RDS의 생성 및 수정 시간이 현재 한국 시간과 9시간 차이가 발생

로컬 MySQL의 TimeZone은 정상이었으나 AWS RDS의 TimeZone은 기본 값이 UTC이기 때문에 시간 차이가 났습니다.

AWS RDS 및 SpringBoot의 TimeZone을 Asia/Seoul로 변경하였습니다.

해결 코드 : https://github.com/Onihsoy07/cfrboard/commit/95e182fdadaecc86c54fba150e0d25d59d357326
 
</details>







## 웹 사이트 화면

|메인페이지|회원가입|
|:---:|:---:|
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/615a16e9-4f71-492a-a0ec-d6b4134a3559" />|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/f0cd597d-a280-4c84-9010-0935bcfdee7d" />|
|사진 보내기|닮은 유명인 확인|
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/c14f17d1-7be0-435d-9258-64403aefc6e6" />|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/fbda3a3d-72cc-4632-95e2-3a814e11ed2f" />|
|게시판|문의글|
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/ad207561-d125-430d-9576-8c7320fc9ee0" />|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/3de0ed7b-7353-4462-9e06-d71c8a64264e" />|
|모바일 메인 네비|모바일 게시글|
|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/346f0edd-e391-4e76-a426-2b1670c4f4ef" />|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/73737ea5-55db-4679-a49b-2e82b176a687" />|
|모바일 게시판|모바일 게시글|
|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/b52e81aa-f16c-4ecb-879b-c6398accde6d" />|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/6efca2ca-7423-4707-a347-5dcb93e00598" />|
|에러 로그(Slack)||
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/3d8e4769-b214-4d8c-9664-092530f81871" />||



<!--
- 에러 로그(Slack)

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/3d8e4769-b214-4d8c-9664-092530f81871" />

- 로그인

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/7613d865-0120-4ba5-9429-b25103468e24" />

<br>

- 회원가입

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/f0cd597d-a280-4c84-9010-0935bcfdee7d" />

<br>

- 닮은 연예인 확인

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/61817783-3faf-4c2b-953d-238a85c4899a" />

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/c14f17d1-7be0-435d-9258-64403aefc6e6" />

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/fbda3a3d-72cc-4632-95e2-3a814e11ed2f" />

<br>

- 문의

<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/3de0ed7b-7353-4462-9e06-d71c8a64264e" />

<br>

- 모바일 웹 반응

<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/346f0edd-e391-4e76-a426-2b1670c4f4ef" />
<br>
<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/73737ea5-55db-4679-a49b-2e82b176a687" />
<br>
<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/6efca2ca-7423-4707-a347-5dcb93e00598" />
<br>
<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/b52e81aa-f16c-4ecb-879b-c6398accde6d" />
<br>

-->
