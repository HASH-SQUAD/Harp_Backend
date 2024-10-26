const express = require('express');
const router = express.Router();

const { validateToken } = require('../../middlewares/AuthMiddleware.js');

const createPost = require('./CreatePost.js');
router.post('/', validateToken, createPost);

const GetAllPost = require('./GetAllPost.js')
router.get('/', GetAllPost)

const GetAllPostForTag = require('./GetAllPostForTag.js')
router.post('/fortag', GetAllPostForTag)

const Wish = require('./Wish.js');
router.post('/wish/:id', validateToken, Wish);

const UpdatePost = require('./UpdatePost.js')
router.put('/update/:id', validateToken, UpdatePost);

const DeletePost = require('./DeletePost.js')
router.delete('/delete/:id', validateToken, DeletePost)

const AddComment = require('./AddComments.js')
router.post('/comment/', validateToken, AddComment)

module.exports = router;