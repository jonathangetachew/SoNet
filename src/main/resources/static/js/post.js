'use strict';

const posts = [
    {
        id: 1,
        author: {
            name: 'James',
            email: '@jokerjames',
            imageUrl: 'https://semantic-ui.com/images/avatar2/large/matthew.png',
        },
        text: "If you don't succeed, dust yourself off and try again.",
        likes: 10,
    },
    {
        id: 2,
        author: {
            name: 'Fatima',
            email: '@fantasticfatima',
            imageUrl: 'https://semantic-ui.com/images/avatar2/large/molly.png',
        },
        text: 'Better late than never but never late is better.',
        likes: 12,
    },
    {
        id: 3,
        author: {
            name: 'Xin',
            email: '@xeroxin',
            imageUrl: 'https://semantic-ui.com/images/avatar2/large/elyse.png',
        },
        text: 'Beauty in the struggle, ugliness in the success.',
        likes: 18,
    }
];

const template = `
<div class="box" style="margin-top: 0px;">
  <article class="media">
    <div class="media-left">
      <figure class="image is-64x64">
        <img :src="post.author.imageUrl" alt="Image">
      </figure>
    </div>
    <div class="media-content">
      <div class="content">
        <p>
          <strong>{{post.author.name}}</strong> <small>{{post.author.email}}</small>
          <br>
          {{post.text}}
        </p>
      </div>
      <nav class="level is-mobile">
        <div class="level-left">
          <a class="level-item" aria-label="reply">
            <span class="icon is-small">
              <i class="fa fa-reply" aria-hidden="true"></i>
            </span>
          </a>
          <a class="level-item" aria-label="retweet">
            <span class="icon is-small">
              <i class="fa fa-retweet" aria-hidden="true"></i>
            </span>
          </a>
          <a class="level-item" aria-label="like">
            <span class="icon is-small">
              <i class="fa fa-heart" aria-hidden="true"></i>
              <span class="likes">{{post.likes}}</span>
            </span>
          </a>
        </div>
      </nav>
    </div>
  </article>
</div>
`;

function initializeVue() {
    Vue.component('post-component', {
        template,
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
                const response = await fetch(`${window.location.origin}/posts`);
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

        const data = formToJSON(document.querySelector("form"));

        const response = await postData('post', {data});

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
            // const post = {
            //     id: 4,
            //     author: {
            //         name: 'Yadir',
            //         email: '@yadirhb',
            //         imageUrl: 'https://semantic-ui.com/images/avatar2/large/matthew.png',
            //     },
            //     text: 'Beauty in the struggle, ugliness in the success.',
            //     likes: 200,
            // };
            vueApp.posts.unshift(response);

            // Clear the elements
            const els = document.querySelectorAll("textarea[name='text']");
            els.forEach((el) => el.value = "");

            window.closeModals();
        }
        console.log(response);
    });

});