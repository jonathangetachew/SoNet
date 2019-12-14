'use strict';

const singlePostTemplate = `
<div>
    <div class="card" style="margin-bottom: 10px">
      <div class="content" style="margin-bottom: 5px;">
            <div class="media" style="padding-top: 10px;">
              <div class="media-left" style="margin-right: 0px;">
                <figure class="image is-48x48">
                  <img class="is-rounded" :src="post.author.imageUrl" alt="Image">
                </figure>
              </div>
              <div class="media-content">
                <strong>{{post.author.name}}</strong> <small>{{post.author.email}}</small>
                <br>
                <time datetime="2016-1-1">{{post.creationDate | formatDate}}</time>
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
                  <span class="likes">{{post.likes}}</span>
                </span>
              </a>
      </footer>
      <div class="is-divider" style="margin: 0px;"></div>
      <div>
        <comment-list v-for="comment in post.comments" :comment="comment"/>
      </div>
    </div>
</div>    
`;

function initializeVue() {
    Vue.filter('formatDate', (value) => value ? moment(value).format('MM/DD/YYYY hh:mm') : '');
    Vue.component('post-list', {
        template: singlePostTemplate,
        props: {
            post: Object
        }
    });

    const vueApp = new Vue({
        el: '#root',
        data() {
            return {
                posts: []
            }
        },
        methods: {
            async getInitialPost() {
                const response = await fetch(`${window.location.origin}/user/posts`);
                const result = await response.json();
                this.posts.push(...result);
            }
        },
        beforeMount() {
            this.getInitialPost();
        }
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
    const submit = document.querySelector("#submit.button");

    submit.addEventListener('click', async (e) => {
        e.preventDefault();

        const data = new FormData();
        data.append("text", textArea.value);
        if (file.files[0]) {
            data.append("contentFile", file.files[0]);
        }

        const request = await fetch(`${window.location.origin}/user/post`, {
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
            vueApp.posts.unshift(response);

            // Clear the elements
            const els = document.querySelectorAll("textarea[name='text']");
            els.forEach((el) => el.value = "");

            window.closeModals();
        }
        console.log(response);
    });

});