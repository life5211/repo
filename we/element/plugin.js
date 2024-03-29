questionList = log.log.entries.map(e => e.response.content.text).filter(e => e).map(e => JSON.parse(e));
answerList = questionList.map(e => {
  return {q: e.title, ans: e.questionItems.filter(i => i.isRight).map(i => i.content)}
})


function queryAnswer(text) {
  if (!text) {
    return null;
  }

  for (let ele of answerList) {
    // 精确匹配
    if (text === ele.q) {
      return ele.a;
    }
    // 模糊匹配
    if (ele.q.includes(text) || ele.a.replaceAll(/\s/, "").includes(text.replaceAll(/\s/, ""))) {
      return ele.a;
    }

  }
  return null;

}


// Greasy Fork
// 登录
// 简体中文 (zh-CN)
// 脚本列表 论坛 站点帮助 更多
// 信息
// 代码
// 历史版本
// 反馈 (1)
// 统计数据
// 国家中小学智慧教育平台电子课本教材下载 最新版[直接下载pdf 跳过浏览器默认预览]
// 在国家中小学智慧教育平台网站中添加电子课本下载按钮，在列表中无需跳转，无需登录，批量下载
//
// 重新安装 1.7 版本?
//     提问、发表评价，或者 举报这个脚本。
// ==UserScript==
// @name         国家中小学智慧教育平台电子课本教材下载 最新版[直接下载pdf 跳过浏览器默认预览]
// @namespace    https://greasyfork.org/zh-CN/scripts/469898-smartedutextbookdownloader
// @version      1.7
// @description  在国家中小学智慧教育平台网站中添加电子课本下载按钮，在列表中无需跳转，无需登录，批量下载
// @author       @topjohncian
// @require      https://unpkg.com/idb@7/build/umd.js
// @require      https://unpkg.com/coco-message@2.0.3/coco-message.min.js
// @match        *://basic.smartedu.cn/*
// @connect      r1-ndr.ykt.cbern.com.cn
// @connect      r2-ndr.ykt.cbern.com.cn
// @connect      r3-ndr.ykt.cbern.com.cn
// @license      MIT
// @grant        window.onurlchange
// ==/UserScript==
window.materialInfo = [];
function ramdomItem(arr) {
  return arr[Math.floor(Math.random() * arr.length)];
}
async function onDownloadClick(event) {
  event.preventDefault();
  event.stopPropagation();
  const target = event.target;
  const detailHost = [
    "//s-file-1.ykt.cbern.com.cn",
    "//s-file-2.ykt.cbern.com.cn",
  ];
  const downloadHost =
      "https://r1-ndr.ykt.cbern.com.cn,https://r2-ndr.ykt.cbern.com.cn,https://r3-ndr.ykt.cbern.com.cn".split(
          ","
      );
  const materialId = target.dataset.materialId;
  const fileName = target.dataset.materialTitle + ".pdf";
  //   alert("下载" + fileName + "\n" + target.dataset.materialId);
  const cancel = window.cocoMessage.loading(`正在下载 ${fileName}`);
  const detail = await (
      await fetch(
          ramdomItem(detailHost) +
          `/zxx/ndrv2/resources/tch_material/details/${materialId}.json`
      )
  ).json();
  const pdfDetail =
      detail.ti_items.find((item) => item.ti_format === "pdf") ?? null;
  if (pdfDetail === null) {
    throw new Error("未找到pdf文件");
  }
  const downloadURL = pdfDetail.ti_storage.replace(
      /^cs_path:\${ref-path}/,
      ramdomItem(downloadHost)
  );
  const blob = await (
      await fetch(downloadURL, {
        referrer: "https://basic.smartedu.cn/",
        referrerPolicy: "strict-origin-when-cross-origin",
        body: null,
        method: "GET",
        mode: "cors",
      })
  ).blob();
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = fileName;
  a.target = "_blank";
  a.click();
  URL.revokeObjectURL(url);
  cancel();
  window.cocoMessage.success(`下载完成 ${fileName}`);
}
let retryTimes = 0;
function tchMaterialHook() {
  // var script = document.createElement("script");
  // script.src = "https://cdn.jsdelivr.net/npm/idb@7/build/umd.js";
  // document.head.appendChild(script);
  // const { unProxy } = window.ah.proxy({
  //   onResponse: (response, handler) => {
  //     if (
  //       new URL(response.config.url.replace(/^(\/\/)/, "https://")).pathname ===
  //       "/proxy/cloud/v1/res_stats/actions/query"
  //     ) {
  //       window.materialInfo = JSON.parse(response.response);
  //     }
  //     handler.next(response);
  //   },
  // });
  const materialUlElement = document.querySelector(
      "#main-content > div.content > div.fish-spin-nested-loading.x-edu-nested-loading > div > div:nth-child(2) > div > div:nth-child(2) > div:nth-child(2) > ul"
  );
  if (materialUlElement === null) {
    if (retryTimes >= 60) {
      return;
    } else {
      retryTimes += 1;
      setTimeout(() => tchMaterialHook(), 1000);
    }
    return;
  }
  retryTimes = 0;
  const config = { attributes: true, childList: true, subtree: true };
  // Callback function to execute when mutations are observed
  const callback = (mutationList, observer) => {
    console.log(
        "[SMARTEDU-DOWNLOADER] Mutation Observer Updated",
        mutationList,
        observer
    );
    const needUpdate = mutationList
        .flatMap((mutation) => [...mutation.addedNodes, ...mutation.removedNodes])
        .every((element) => !(element instanceof HTMLButtonElement));
    if (needUpdate) {
      hook().then();
    }
  };
  const observer = new MutationObserver(callback);
  observer.observe(materialUlElement, config);
  hook().then();
  async function hook() {
    if (window.materialInfo.length === 0) {
      await getMaterialInfo();
    }
    const materialSpanDivs = materialUlElement.querySelectorAll(
        "li > div:nth-child(2) >  div:nth-child(1)"
    );
    const versionLabelSpan = document.querySelector(
        "#main-content > div.content > div.fish-spin-nested-loading.x-edu-nested-loading > div > div:nth-child(2) > div > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) >  label.fish-radio-tag-wrapper-checked > span:nth-child(2)"
    );
    const versionLabel = versionLabelSpan?.innerText ?? "";
    materialSpanDivs.forEach((materialSpanDiv) => {
      materialSpanDiv.querySelector("button")?.remove();
    });
    for (const materialSpanDiv of materialSpanDivs) {
      const materialName = materialSpanDiv.querySelector("span").innerText;
      const materials = window.materialInfo.filter(
          (m) => m.title === materialName
      );
      const material =
          versionLabel !== ""
              ? materials.find((m) =>
              m.tag_list.some((tag) => tag.tag_name === versionLabel)
          ) ?? materials[0]
              : materials[0];
      const button = document.createElement("button");
      button.dataset.materialId = material.id;
      button.dataset.materialTitle = material.title;
      button.innerText = `下载 ${material.title}.pdf`;
      button.setAttribute("style", "z-index: 999;");
      button.onclick = onDownloadClick;
      materialSpanDiv.appendChild(button);
    }
  }
}
function tchMaterialDetailHook() {
  const contentId = new URLSearchParams(location.search).get("contentId");
  if (contentId === null) {
    return;
  }
  const materialSpanDiv = document.querySelector(
      `#main-content > div.content > div:last-child > div > div > div:nth-child(1)`
  );
  const materialTitle = materialSpanDiv?.querySelector("h3")?.innerText ?? "";
  if (materialSpanDiv === null || materialTitle === "") {
    if (retryTimes >= 60) {
      return;
    } else {
      retryTimes += 1;
      setTimeout(() => tchMaterialDetailHook(), 1000);
    }
    return;
  }
  retryTimes = 0;
  // const material = window.materialInfo.find((m) => m.id === contentId);
  const button = document.createElement("button");
  button.dataset.materialId = contentId;
  button.dataset.materialTitle = materialTitle;
  button.innerText = `下载 ${materialTitle}.pdf`;
  button.setAttribute("style", "z-index: 999;");
  button.onclick = onDownloadClick;
  materialSpanDiv.appendChild(button);
}
async function getMaterialInfo() {
  const db = await idb.openDB("content-library_ncet-xedu");
  window.materialInfo = await db
      .transaction("NDR_TchMaterial", "readonly")
      .objectStore("NDR_TchMaterial")
      .getAll();
}
async function main() {
  "use strict";
  if (new URL(location.href).pathname === "/tchMaterial") {
    tchMaterialHook();
  } else if (new URL(location.href).pathname === "/tchMaterial/detail") {
    tchMaterialDetailHook();
  }
}
(async function () {
  // @ts-expect-error
  if (window.onurlchange === null) {
    // feature is supported
    window.addEventListener("urlchange", (info) => {
      console.log(info);
      main();
    });
  }
  await main();
})();

