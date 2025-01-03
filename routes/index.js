const express = require('express');
const router = express.Router();

// 유저 관련 라우터
const userRouter = require('./Auth')
router.use('/auth', userRouter)

// JWT 인증 라우터
const jwtRouter = require('./Jwt');
router.use('/jwt', jwtRouter);

// 이미지 업로드 라우터
const uploadRouter = require('./Upload');
router.use('/upload', uploadRouter);

// 채팅 라우터
const ChattingRouter = require('./Chatting');
router.use('/chat', ChattingRouter);

// 일정 라우터
const PlanRouter = require('./Plan')
router.use('/plan', PlanRouter)

// 커뮤니티
const CommunityRouter = require('./Community')
router.use('/community', CommunityRouter)

module.exports = router;
