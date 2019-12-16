'use strict';
const apiUrl = `${window.location.origin}/api/v1`;
const singleCommentTemplate = `
    <div class="content" style="border-bottom: 1px solid #ececec; margin-bottom: 5px;">
        <div class="media" style="padding-top: 10px;">
            <div class="media-left" style="margin-right: 0px;">
                <figure class="image is-32x32">
                    <img class="is-rounded" :src="comment.author.imageUrl" alt="Image">
                </figure>
            </div>
            <div class="media-content">
                <a :href="'./showProfile?email='+comment.author.email">
                    <strong>{{comment.author.name}}</strong>
                </a>
                <time datetime="2016-1-1">
                    {{comment.creationDate | formatDate }}
                </time>
                <br>
                <span>{{comment.text}}</span>
            </div>
        </div>
    </div>
`;

const singlePostTemplate = `
<div v-if="loaded">
    <div class="card" style="margin-bottom: 10px">
      <div class="content" style="margin-bottom: 5px;">
            <div class="media" style="padding-top: 10px;">
              <div class="media-left" style="margin-right: 0px;">
                <figure class="image is-48x48">
                  <img class="is-rounded" :src="post.author.imageUrl" alt="Image">
                </figure>
              </div>
              <div class="media-content">
                <a :href="'./showProfile?email='+post.author.email">
                <strong>{{post.author.name}}</strong> <small>{{post.author.email}}</small>
                </a>
                <br>
                <time datetime="2016-1-1">{{post.creationDate | formatDate}}</time>
              </div>
              <div v-if="showDetailsLink" class="media-right" style="margin: 1rem;">
                <a v-bind:href="'post/' + post.id">Details</a>
              </div>
            </div>
        </div>
      <div v-if="!!post.contentUrl" class="card-image">
        <figure class="image">
          <object :data="post.contentUrl" width="100%" height="100%" style="min-height:400px; max-height: 400px;"/>
        </figure>
      </div>
      <div class="card-content" style="padding: 5px 1.5rem;">
        <div class="content">
        {{post.text}}
        </div>
      </div>
      <footer class="card-footer">
        <a class="card-footer-item" aria-label="reply">
                <span class="icon is-small">
                  <i class="fa fa-reply" aria-hidden="true"></i>
                </span>
              </a>
              <a class="card-footer-item" aria-label="retweet">
                <span class="icon is-small">
                  <i class="fa fa-retweet" aria-hidden="true"></i>
                </span>
              </a>
              <a class="card-footer-item" aria-label="like">
                <span class="icon is-small">
                  <i class="fa fa-heart" aria-hidden="true"></i>
                  <span class="likes">{{post.likeCount}}</span>
                </span>
              </a>
      </footer>
      <div class="is-divider" style="margin: 0px;"></div>
      <div v-if="comments && comments.length > 0">
            <comment-list v-for="comment in comments" :comment="comment" v-bind:key="comment.id"/>
            <div v-if="hasMoreComments" class="is-centered has-text-centered" style="padding: 5px;">
                <a @click="loadCommentsAsync()">Load more</a>
            </div>
      </div>
      <div class="content" style="margin-bottom: 10px; background-color:#ececec;">
        <form @submit="submitForm">
            <div class="media" style="padding: 5px 10px 5px 0px;">
                <div class="media-left" style="margin-right: 0px;">
                    <figure class="image is-32x32">
                        <img class="is-rounded" :src="post.author.imageUrl" alt="Image">
                    </figure>
                </div>
                <div class="media-content">
                    <p class="control">
                        <input class="input is-rounded" v-model="newComment.text" name="text" type="text" required
                               placeholder="Add your comment..."/>
                   </p>
                </div>
                <div class="media-right" style="margin-left: 0px;">
                    <figure class="image is-32x32">
                        <input type="submit" class="button is-rounded" value="Post"/>
                    </figure>
                </div>
            </div>
        </form>
      </div>
    </div>
</div>    
`;

function initializeVue() {
    Vue.filter('formatDate', (value) => value ? moment(value).format('MM/DD/YYYY hh:mm') : '');

    Vue.component('CommentList', {
        template: singleCommentTemplate,
        props: {
            comment: Object
        }
    });

    Vue.component('SinglePost', {
        template: singlePostTemplate,
        props: {
            post: Object,
            loaded: Boolean,
            showDetailsLink: Boolean
        },
        data() {
            return {
                hasMoreComments: false,
                comments: [],
                newComment: {text: ""},
                page: 1
            }
        },
        methods: {
            loadNextPage() {
                this.page += 1;
            },
            async submitForm(e) {
                e.preventDefault();
                if (this.newComment.text) {
                    const data = new FormData();
                    data.append("text", this.newComment.text);

                    const request = await fetch(`${apiUrl}/user/post/${this.post.id}/comment`, {
                        method: 'POST',
                        body: data
                    });
                    const response = await request.json();
                    response.creationDate = new Date();
                    this.comments.unshift(response);
                    this.newComment.text = "";
                }
            },
            async loadPostAsync() {
                const response = await fetch(`${apiUrl}/user/post/${this.post.id}`);
                this.post = await response.json();
                this.loaded = true;
            },
            async loadCommentsAsync() {
                const response = await fetch(`${apiUrl}/user/post/${this.post.id}/comments?page=${this.page}`);
                const result = await response.json();
                this.page = result.nextPage;
                this.hasMoreComments = result.hasMore;
                this.comments.push(...result.data);
            }
        },
        beforeMount() {
            if (!this.loaded) this.loadPostAsync();
            this.loadCommentsAsync();
        }
    });

    Vue.component('PostList', {
        template: `<div v-if="posts.length > 0">
            <SinglePost v-for="post in posts" :post="post" v-bind:key="post.id" :loaded="true" :showDetailsLink="true"/>
        </div>`,
        data() {
            return {
                posts: [],
                page: 1
            }
        },
        methods: {
            loadNextPage() {
                this.page += 1;
            },
            async loadPostsAsync() {
                const response = await fetch(`${window.location.origin}/api/v1/user/posts`);
                const result = await response.json();
                this.posts.push(...result.data);
            }
        },
        beforeMount() {
            this.loadPostsAsync();
        }
    });

    const vueApp = new Vue({
        el: '#root',
    });

    return vueApp;
}

function clear(e) {
    //e.firstElementChild can be used.
    let child = e.lastElementChild;
    while (child) {
        e.removeChild(child);
        child = e.lastElementChild;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const vueApp = initializeVue();
    const textArea = document.querySelector("textarea[name='text']");
    const file = document.querySelector("[name='contentFile']");
    if (textArea) {
        textArea.addEventListener('input', () => {
            let textLn = textArea.value.length;
            if (textLn > 0) {
                const parent = textArea.parentNode;
                clear(parent);
                parent.appendChild(textArea);
                textArea.focus();
                textArea.classList.remove('is-danger');
            }
        });
    }
    const submit = document.querySelector("#submit.button");

    if (submit) {
        submit.addEventListener('click', async (e) => {
            e.preventDefault();

            const data = new FormData();
            data.append("text", textArea.value);
            if (file.files[0]) {
                data.append("contentFile", file.files[0]);
            }

            const request = await fetch(`${apiUrl}/user/post`, {
                method: 'POST',
                body: data
            });
            const response = await request.json();

            if (response.errorType) {
                if (response.errorType === 'validation') {
                    response.errors.forEach(({field, message}) => {
                        const el = document.querySelector(`form [name='${field}']`);
                        el.classList.add('is-danger');
                        const hasErrorEl = Array.prototype.slice.call(el.parentNode.childNodes).find(node => node.title === "error");

                        if (!hasErrorEl) {
                            const p = document.createElement("p");
                            p.setAttribute("title", "error");
                            p.classList.add('is-danger');
                            p.appendChild(document.createTextNode(message));
                            el.parentNode.appendChild(p);
                        }
                    });
                }
            } else {
                vueApp.$children[0].posts.unshift(response);

                // Clear the elements
                const els = document.querySelectorAll("textarea[name='text']");
                els.forEach((el) => el.value = "");

                window.closeModals();
            }
            console.log(response);
        });
    }
});