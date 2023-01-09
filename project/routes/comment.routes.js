const { Router } = require("express");
const _ = require("lodash"); 

const Comment = require("../model/comment.model");
const CommentController = require("../controller/comment.controller");

const authenticated = require("../middleware/auth.middleware");

const router = Router();

router

    .post("/add/:id", authenticated, (req, res) => {
        const {id} = _.pick(req.params, ["id"]);
        const {body} = _.pick(req.body, ["body"]);

        CommentController.addComment(id, body, req.user._id)
            .then((comment) => {
                res.send(comment);
            })
            .catch((error) => {
                res.send(error);
            })
    })