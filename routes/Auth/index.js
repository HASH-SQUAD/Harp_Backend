const express = require('express');
const router = express.Router();

// 간편로그인 라우터
const GoogleRouter = require('./GoogleAuth');
router.use('/', GoogleRouter);
const KakaoRouter = require('./KakaoAuth');
router.use('/', KakaoRouter);

// 자체로그인 라우터
const SignIn = require('./SelfSign')
router.use('/', SignIn)

// 새로운 유저생성 라우터
const NewAccountSignUpRouter = require('./NewAccountSignUp')
router.use('/newaccount', NewAccountSignUpRouter)

// 회원탈퇴 라우터
const DeleteAccount = require('./DeleteAccount')
router.use('/deleteuser', DeleteAccount)

// 유저정보수정 라우터
const UpdateUserRouter = require('./UpdateUser');
router.use('/updateuser', UpdateUserRouter);

// 설문조사 라우터
const SurveyRouter = require('./Survey');
router.use('/survey', SurveyRouter);

// 어드민 권한 라우터
const AdminRouter = require('./Admin');
router.use('/admin', AdminRouter);

// 유저 정보 라우터
const AllAuthState = require('./AllAuthState');
router.use('/authstate', AllAuthState);


module.exports = router;