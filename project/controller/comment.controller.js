const _ = require("lodash");

const Comment = require("../model/comment.model");
const Post = require("../model/posts.model");

const addComment = (id, body, user) => {
    return new Promise((resolve, reject) => {
        Post.findById(id)
            .then((post) => {
                if (post == null) { return reject("Post not found"); }

                const newComment = new Comment({
                    post: id,
                    body,
                    user
                })

                newComment.save((error) => {
                    if (error) { return reject(error); }
                    Post.findOneAndUpdate(
                        {_id: id},
                        {$push: { comments: newComment._id}},
                        function(error, success) {
                            if (error) { return reject(error); }
                            resolve(newComment);
                        } 
                    )
                })
            })
    })
}

module.exports = { addComment }