'use strict';

const singleCommentTemplate = `
    <div class="content" style="margin-bottom: 10px">
      <div class="media" style="padding-top: 10px;">
      <div class="media-left" style="margin-right: 0px;">
        <figure class="image is-32x32">
            <img class="is-rounded" :src="comment.author.imageUrl" alt="Image">
        </figure>
      </div>
      <div class="media-content">
        <strong>{{comment.author.name}}</strong>
        <br>
        {{comment.text}}
<!--        <time datetime="2016-1-1">{{comment.creationDate | formatDate}}</time>-->
      </div>
    </div>
    </div>
`;

document.addEventListener('DOMContentLoaded', function () {
    Vue.component('comment-list', {
        template: singleCommentTemplate,
        props: {
            comment: Object
        }
    });
});