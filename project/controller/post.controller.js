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

