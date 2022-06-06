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
* 카테고리 페이지
** 스터디, 식사, 등산 등 여러 활동들을 카테고리별로 볼 수 있도록 하였다. 페이징 처리는 무한스크롤로 구현했다.
<img src="https://user-images.githubusercontent.com/59406944/172187386-882ab063-444d-416c-bb5e-16f361ccd6b2.png" width="80%" height="60%">

* 카테고리 페이지(지역 선택시)
** 지역을 선택시 그 지역에 해당하는 글들을 불러오록 했다. 지역은 시,도 / 시,군,구 / 읍,면,동 으로 총 3가지를 선택할 수 있다.
<img src="https://user-images.githubusercontent.com/59406944/172187537-25eb1c44-4976-4bf8-8847-492c17d9f22c.png" width="80%" height="60%">

* 글 상세 페이지
** 글 상세 페이지에서는 북마크 기능과 댓글을 달 수 있도록 했다. 댓글에 답글 또한 달 수 있도록 구현했다.
<img src="https://user-images.githubusercontent.com/59406944/172189158-198994cc-9a6c-4af5-9e41-8bec6a248cc8.png" width="80%" height="60%">

* 내가 쓴 글 목록
** 내가 쓴 글 목록들을 불러와서 쓴 글들을 확인하기 쉽도록 했다.
<img src="https://user-images.githubusercontent.com/59406944/172188189-36a2b7eb-6255-4d20-b168-568661b3aeda.png" width="80%" height="60%">

* 내가 관심있는 글 목록(북마크)
** 내가 쓴 글 뿐만 아니라 관심있는 주제들의 글들은 북마크 기능을 통해 마이페이지에서 확인 가능하다.
<img src="https://user-images.githubusercontent.com/59406944/172188236-47829e32-6178-4f9d-9b3d-e6caa1ffbe9d.png" width="80%" height="60%">

* 회원정보 수정(1)
** 처음 회원정보 수정페이지에 들어갈 경우 input창들은 disalbed를 걸어놓았다.
<img src="https://user-images.githubusercontent.com/59406944/172188336-6472a95f-9749-493b-857e-3aa4543764fd.png" width="80%" height="60%">

* 회원정보 수정(2)
** 변경버튼을 누를경우 input창의 disabled가 풀린다. 
** 실수로 다른 정보들을 수정하는 것을 방지하기 위해 수정하고 싶은 정보들만 수정할 수 있도록 관련 정보 수정 버튼을 누를경우에만 수정하도록 하였다. 
<img src="https://user-images.githubusercontent.com/59406944/172188363-2328e873-4dfe-486e-b3ed-237694763770.png" width="80%" height="60%">

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
