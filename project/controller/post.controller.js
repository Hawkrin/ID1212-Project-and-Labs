const User = require("../model/user.model")
const Post = require("../model/posts.model")

/**
 * Creates a new post
 * Returning a promise, resolve if successfully created the post.
 * Reject if problem occurs while creating the post.
 * 
 * @param {String} title 
 * @param {String} description 
 * @param {String} body 
 * @param {String} user 
 * @returns {Promise}
 */
const createPost = (title, description, body, user) => {
    return new Promise((resolve, reject) => {
        const newPost = new Post({
            title,
            description,
            body,
            user
        });

        newPost.save((error) => {
            if (error) { return reject(error);}
            return resolve(newPost);
        })
    })
}

/**
 * Checks database for the posts
 * Returns a promise if the posts are found
 * Otherwise rejects the promise
 * 
 * @returns {Promise}
 */
const findAllPost = () => {
    return new Promise((resolve, reject) => {
        Post.find()
            .then((posts) => {
                return resolve(posts);
            })
            .catch((error) => {
                return reject(error);
            })
    })
}

const findPost = (id) => {
    return new Promise((resolve, reject) => {

    })
} 

module.exports = { createPost, findAllPost, findPost};

