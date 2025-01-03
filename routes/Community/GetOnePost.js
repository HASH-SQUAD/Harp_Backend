const authUtil = require('../../response/authUtil.js');
const { Community, Wish, Comments, Users, sequelize } = require('../../models');

const GetOnePost = async (req, res) => {
  const communityId = req.params.id;

  try {
    const community = await Community.findOne({
      where: { communityId },
      attributes: [
        'communityId',
        'title',
        'des',
        'tag',
        'images',
        'createdAt',
        'updatedAt',
        [sequelize.fn('COUNT', sequelize.col('Wishes.wishId')), 'wishCount']
      ],
      include: [
        {
          model: Wish,
          attributes: [],
          required: false
        },
        {
          model: Users,
          as: 'creator',
          attributes: ['userId', 'nickname', 'profileImg'],
        }
      ],
      group: [
        'Community.communityId',
        'Community.title',
        'Community.des',
        'Community.tag',
        'Community.images',
        'Community.createdAt',
        'Community.updatedAt',
        'creator.userId',
        'creator.nickname',
        'creator.profileImg'
      ],
      raw: true
    });

    if (!community) {
      return res
        .status(404)
        .send(authUtil.successTrue(404, '게시글을 찾을 수 없습니다.'));
    }

    const comments = await Comments.findAll({
      where: { communityId },
      attributes: [
        'commnetsId',
        'des',
        'isCommentForComment',
        'parentComment',
        'createdAt',
        'updatedAt',
        'likes'
      ],
      include: [
        {
          model: Users,
          as: 'author',
          attributes: ['userId', 'nickname', 'profileImg']
        }
      ],
      order: [['createdAt', 'ASC']],
      raw: true,
      nest: true
    });

    let CommentsCount = 0

    const structuredComments = comments.reduce((acc, comment) => {
      if (!comment.parentComment) {
        const replies = comments.filter(reply => reply.parentComment === comment.commnetsId);
        const repliesCount = replies.length;
        CommentsCount += repliesCount

        acc.push({
          ...comment,
          repliesCount,
          isCommentForComment: repliesCount > 0,
          replies: replies.map(reply => ({
            ...reply,
            isCommentForComment: true
          }))
        });
      }
      return acc;
    }, []);

    const formattedResponse = {
      ...community,
      tag: community.tag,
      images: JSON.parse(community.images || '[]'),
      wishCount: parseInt(community.wishCount),
      createdAt: community.createdAt,
      updatedAt: community.updatedAt,
      comments: structuredComments.map(comment => ({
        ...comment,
        createdAt: comment.createdAt,
        updatedAt: comment.updatedAt
      }))
    };

    const totalCommentsCount = structuredComments.reduce((acc, comment) => {
      return acc + 1 + (comment.replies ? comment.replies.length : 0);
    }, 0);

    formattedResponse.CommentsCount = totalCommentsCount;

    return res
      .status(200)
      .send(authUtil.successTrue(200, '게시글 찾음', { post: formattedResponse }));
  } catch (error) {
    console.error(error);
    return res.status(500).send(authUtil.unknownError({ error: error.message }));
  }
};

module.exports = GetOnePost;