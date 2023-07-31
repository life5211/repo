// ==UserScript==
// @name         officeOnline在线打开
// @namespace    com.life5211.officeOnline
// @version      0.1
// @description  try to take over the world!
// @author       life5211
// @match        http://ncpta.e21cn.com/*
// @match        http://*.e21cn.com/*
// @match        http://*.ncpta.cn/*
// @icon         https://www.google.com/s2/favicons?domain=csdn.net
// @grant        none
// ==/UserScript==

(function () {
  'use strict';
  Array.from(document.getElementsByTagName('a')).forEach(aTag => {
    if (aTag && aTag.href && aTag.href.includes('e21cn.com/tools/file.ashx?')) {
      var clonedNode = aTag.cloneNode(true); // 克隆节点
      var aTagEncodeUri = encodeURIComponent(`http://static.sz.e21cn.com/${aTag.href.split('id=')[1]}`);
      clonedNode.setAttribute("href", `https://view.officeapps.live.com/op/view.aspx?src=${aTagEncodeUri}`);
      clonedNode.innerText = '在线打开';
      clonedNode.style.color = 'green';
      clonedNode.style.fontWeight = 'bold';
      clonedNode.style.marginRight = '40px';
      // clonedNode.style.paddingRight = '60';
      aTag.parentNode.insertBefore(clonedNode, aTag); // 在父节点插入克隆的节点
      // aTag.parentNode.insertBefore(document.createElement('br'), aTag);
      // http://static.e21cn.com/tools/file.ashx?id=0286726627daf08d5f34f529c24c1736.xls
    }

  });

})();
