# Togetor-renewal
기존 동아리 프로젝트 togetor(node.js와 js를 이용)를 리뉴얼(java와 SpringBoot를 이용)한 프로젝트

## 목차
1. [서비스 소개](#서비스-소개)
2. [개발 환경](#개발-환경)
3. [제작 기간](#제작-기간)
4. [설계](#설계)
5. [프로젝트 화면](#프로젝트-화면)
6. [구현 목록](#구현-목록)
7. [개발 기록](#개발-기록)

## 서비스 소개
주변 동네에서 활동(ex - 등산, 축구, 식사 등)을 같이 할 사람들을 모집하는 서비스 

## 개발 환경
* IDE : Intelij(인텔리제이)
* DBMS : MySQL
* Language : Java(11)
* 프레임워크 : SpringBoot
* 기타 라이브러리 : lombok, thymeleaf, SpringDataJPA
* S3 (AWS)

## 제작 기간
2022.3.13 ~ 2022.4.12

## 설계 
### DB 모델링
![image](https://user-images.githubusercontent.com/59406944/172181229-cea2ad64-ed6b-41b1-bbdc-f9946bb1539e.png)

## 프로젝트 화면
![image](https://user-images.githubusercontent.com/59406944/172187386-882ab063-444d-416c-bb5e-16f361ccd6b2.png)
![image](https://user-images.githubusercontent.com/59406944/172187537-25eb1c44-4976-4bf8-8847-492c17d9f22c.png)
![image](https://user-images.githubusercontent.com/59406944/172188189-36a2b7eb-6255-4d20-b168-568661b3aeda.png)
![image](https://user-images.githubusercontent.com/59406944/172188236-47829e32-6178-4f9d-9b3d-e6caa1ffbe9d.png)
![image](https://user-images.githubusercontent.com/59406944/172188336-6472a95f-9749-493b-857e-3aa4543764fd.png)
![image](https://user-images.githubusercontent.com/59406944/172188363-2328e873-4dfe-486e-b3ed-237694763770.png)
![image](https://user-images.githubusercontent.com/59406944/172189158-198994cc-9a6c-4af5-9e41-8bec6a248cc8.png)

### 경계

<img src="https://user-images.githubusercontent.com/59406944/172187386-882ab063-444d-416c-bb5e-16f361ccd6b2.png" width="50%" height="50%">
<img src="https://user-images.githubusercontent.com/59406944/172187537-25eb1c44-4976-4bf8-8847-492c17d9f22c.png" width="50%" height="50%">
<img src="https://user-images.githubusercontent.com/59406944/172188189-36a2b7eb-6255-4d20-b168-568661b3aeda.png" width="50%" height="50%">
<img src="https://user-images.githubusercontent.com/59406944/172188236-47829e32-6178-4f9d-9b3d-e6caa1ffbe9d.png" width="50%" height="50%">
<img src="https://user-images.githubusercontent.com/59406944/172188336-6472a95f-9749-493b-857e-3aa4543764fd.png" width="50%" height="50%">
<img src="https://user-images.githubusercontent.com/59406944/172188363-2328e873-4dfe-486e-b3ed-237694763770.png" width="50%" height="50%">
<img src="https://user-images.githubusercontent.com/59406944/172189158-198994cc-9a6c-4af5-9e41-8bec6a248cc8.png" width="50%" height="50%">


## 구현 목록
- [X] 메인페이지
- [X] 카테고리 페이지
- [X] 카테고리 상세페이지
- [X] 로그인 페이지
- [X] 회원가입 페이지
- [X] 글쓰기 페이지
- [X] 마이 페이지
  - [X] 회원 정보 수정
  - [X] 회원 탈퇴
  - [X] 내가 쓴 게시글 보기
  - [X] 관심 있는 게시글 보기

## 개발 기록
[노션으로 작성한 Togetor 개발일지 및 배운점들](https://glowing-honesty-413.notion.site/Togetor_renewal-c898fb0445244357977a8936f11cc2fb)
