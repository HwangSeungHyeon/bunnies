# Bunnies

## 👨‍🏫 프로젝트 소개

우리가 익히 알고 있는 ‘당근마켓’에 영감을 받아 개발 해보고자 합니다.
모방은 창조의 어머니 라는 말이 있듯이 당근마켓을 모방해 봄으로서, 팀원들의 개인 능력과 역량을 올리는 것을 목표로 하고자 합니다.

## ⏲️ 개발기간
- 2024.01.22(월) ~ 2024.01.26(금)

## 📚️ 개발환경

### ✔️ Language
<img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">

### ✔️ Version Control
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

### ✔️ IDE
<img src="https://img.shields.io/badge/intellij idea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">

### ✔️ Framework
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">

### ✔️ DB
<img src="https://img.shields.io/badge/supabase-3FCF8E?style=for-the-badge&logo=supabase&logoColor=white">

## 와이어프레임

![와이어프레임](https://velog.velcdn.com/images/xlddy02/post/f9e9cea7-b747-4e26-b4f6-0f6f2090b3d1/image.png)

## API 명세서

### 게시글 API
![image](https://github.com/HwangSeungHyeon/bunnies/assets/149580488/46663ef1-1694-4837-a783-cc5585f31328)
![image](https://github.com/HwangSeungHyeon/bunnies/assets/149580488/3c4ef8e4-5624-4e68-a038-aa9c85ef9a46)

### 댓글 API
![image](https://github.com/HwangSeungHyeon/bunnies/assets/57141923/051d5fbf-85cf-4481-ae4e-524b5077f49d)

### 사용자 API
![image](https://github.com/HwangSeungHyeon/bunnies/assets/57141923/641a47e1-c6af-4245-82aa-9b485ef0a2de)

### 어드민 API
![image](https://velog.velcdn.com/images/xlddy02/post/8723fd23-1a93-4303-bf83-697047d39151/image.png)

## ERD

![ERD](https://velog.velcdn.com/images/xlddy02/post/b677b9e7-9dbf-4ea0-96d9-fa5d75e54e4d/image.png)

## 패키지 구조

```
bunnies
    ├─domain
    │  ├─admin
    │  │  ├─controller
    │  │  ├─dto
    │  │  │  ├─request
    │  │  │  └─response
    │  │  ├─model
    │  │  ├─repository
    │  │  └─service
    │  ├─comment
    │  │  ├─controller
    │  │  ├─dto
    │  │  ├─model
    │  │  ├─repository
    │  │  └─service
    │  ├─exception
    │  │  └─dto
    │  ├─post
    │  │  ├─controller
    │  │  ├─dto
    │  │  │  ├─request
    │  │  │  └─response
    │  │  ├─model
    │  │  ├─repository
    │  │  └─service
    │  └─user
    │      ├─controller
    │      ├─dto
    │      │  ├─request
    │      │  └─response
    │      ├─model
    │      ├─repository
    │      └─service
    └─infra
        ├─security
        │  └─jwt
        └─swagger
```

## 👨🏻‍💻 Built With

* [황승현](https://github.com/HwangSeungHyeon) - 팀장
* [최윤미](https://github.com/YunmiC/A7website) - 조원
* [강군호](https://github.com/9nh5) - 조원
* [한정민](https://github.com/jeongminy) - 조원
