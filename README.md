# CFR 게시판
네이버 CFR(CLOVA Face Recognition) API를 활용한 닮은 유명인 확인 및 공유를 위한 게시판입니다.


## 프로젝트 소개
네이버에서 개발한 CLOVA Face Recognition(유명인 얼굴 인식)에 흥미가 있어 사용하다가 이것을 활용하여 무엇을 만들까 생각했습니다.
친구들끼리 공유하여 누가 얼마나 닮았는지 확인하면 재미있을 것 같아 만들었습니다.

## 개발 기간
 - 2023.07.09 ~ 2023.11.03


## 멤버구성
- 박준혁 : 프론트엔드(Thymeleaf) 및 백엔드(Spring Boot 2)


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

[5. 문제 해결](#문제-해결)

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

- [Jasypt 복호화 문제](https://github.com/Onihsoy07/cfrboard/wiki/Jasypt-%EB%B3%B5%ED%98%B8%ED%99%94-%EB%AC%B8%EC%A0%9C)

- [Column 기본 값 저장 안되는 문제(null 저장)](https://github.com/Onihsoy07/cfrboard/wiki/Column-%EA%B8%B0%EB%B3%B8-%EA%B0%92-null-%EB%AC%B8%EC%A0%9C)

- [AWS RDS에 데이터 저장 시 시간 안 맞는 문제](https://github.com/Onihsoy07/cfrboard/wiki/AWS-RDS%EC%97%90-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%A0%80%EC%9E%A5-%EC%8B%9C-%EC%8B%9C%EA%B0%84-%EC%95%88-%EB%A7%9E%EB%8A%94-%EB%AC%B8%EC%A0%9C)





## 웹 사이트 화면

|메인페이지|회원가입|
|:---:|:---:|
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/5b25f66a-6b78-484a-ad5d-a1dd571772b7" />|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/f0cd597d-a280-4c84-9010-0935bcfdee7d" />|
|사진 보내기|닮은 유명인 확인|
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/c14f17d1-7be0-435d-9258-64403aefc6e6" />|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/fbda3a3d-72cc-4632-95e2-3a814e11ed2f" />|
|게시판|문의글|
|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/daba619b-74bc-43ed-ab88-db037d0df04e" />|<img width="700" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/d7b0bfd8-b0eb-4454-92a7-46214bde570a" />|
|모바일 메인 네비|모바일 게시글|
|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/7208d492-df41-4739-ae25-22f6c56733fa" />|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/29e86f82-afb2-4ad4-a0b7-49e6aee43519" />|
|모바일 게시판|모바일 게시글 댓글|
|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/2bf57ec3-ca33-42cf-906a-087ab28b98bb" />|<img width="300" src="https://github.com/Onihsoy07/cfrboard/assets/84126411/667d10be-7d63-42d3-947c-3b2118365f6f" />|
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
