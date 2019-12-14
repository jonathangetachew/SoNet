'use strict';

const singleCommentTemplate = `
    <div class="card" style="margin-bottom: 10px">
      {{comment.text}}
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