/**
 * 备用域名
 */
const domainList = ['dwawdwawdaw.com', 'dwawdwa.com', 'dwawdwawadd.com'];
const retry = {};
// eslint-disable-next-line no-undef
window.addEventListener(
  'error',
  (e) => {
    if (e instanceof ErrorEvent || e.target.tagName !== 'SCRIPT') {
      return;
    }
    retryResource(e.target);
  },
  true,
);

function retryResource(target) {
  const originalUrl = target.src;
  const url = new URL(originalUrl);
  const key = url.pathname; // /dwadwa/dwawd.js

  if (!(key in retry)) {
    retry[key] = 0;
  }

  const index = retry[key];
  if (index >= domainList.length) {
    return; // 超出域名限制
  }

  const host = domainList[index];
  url.host = host;

  retry[key]++;

  // eslint-disable-next-line no-undef
  const script = document.createElement('script');
  script.src = url.toString();
  script.defer = true;

  target.replaceWith(script);

  // 移除失败标签
  // target.remove();

  // document.body.appendChild(script);
}
