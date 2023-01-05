const User = require("../model/user.model")
const Post = require("../model/posts.model")

let PostController = {
    findPost: async(req, res) => {
        let found = await Post.find({name: req.params.title});
        res.json(found);
    },
    createPost: async(req, res) => {
        let newPost = new Post(req.body);
        let savedPost = await newPost.save();
        res.json(savedPost);
    }
}

const createPost = (title, body, user) => {
    return new Promise((resolve, reject) => {
        const newPost = new Post({
            title,
            body,
            user
        });

        newPost.save((error) => {
            if (error) { return reject(error);}
            return resolve(newPost);
        })
    })
}

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

module.exports = { createPost, findAllPost };

