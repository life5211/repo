// ==UserScript==
// @name         文档在线预览
// @namespace    com.life5211.officeOnline
// @version      1.0
// @description  用于在线预览文档
// @author       life5211
// @match        http://ncpta.e21cn.com/*
// @match        http://*.e21cn.com/*
// @match        http://*.ncpta.cn/*
// @match        *.gov.cn/*
// @match        *.edu.cn/*
// @icon         http://static.e21cn.com/ncStatic/images/logo.png
// ==/UserScript==

(function () {
  'use strict';
  const fileTypes2312 = ("doc,docx,wps,odt,rtf,xls,xlsx,et,ods,csv,ppt,pptx,dps,odp,pdf,ofd,txt,jpg,jpeg,gif,png,bmp,tif,tiff," +
      "mp3,m4a,mid,midi,wma,mp4,mov,zip,rar,tar,7z,dwg,dxf,dwf,xml,js,css,java,cpp,cs,sql,bat,json,conf").split(',');

  setTimeout(() => Array.from(document.getElementsByTagName('a')).forEach(aTag => {
    if (!aTag || !aTag.href || !aTag.href.includes('.')) return;
    // http://static.e21cn.com/tools/file.ashx?id=0286726627daf08d5f34f529c24c1736.xls
    // const fileType = aTag.href.match(/\.([^.]+)$/)[1];
    // const fileType = aTag.href.split('.').pop().toLocaleLowerCase();
    // console.log(`信息========${aTag.href}`);
    if (aTag.href.includes('e21cn.com/tools/file.ashx?')) {
      nodeCloneWeb2312(aTag, `http://static.sz.e21cn.com/${aTag.href.split('id=')[1]}`);
    } else if (fileTypes2312.includes(aTag.href.match(/\.([^.]+)$/)[1])) {
      nodeCloneWeb2312(aTag, aTag.href);
    } else if (aTag.innerText && aTag.innerText.startsWith('附件')) {
      nodeCloneWeb2312(aTag, aTag.href);
    }

  }), 500);

  function nodeCloneWeb2312(aTag, href) {
    const encodeUri = encodeURIComponent(href);
    // const encodeBaseEncode = encodeURIComponent(Base64.encode(href));
    // nodeCloneA2312(aTag, 'kkFileView', `https://file.kkview.cn/onlinePreview?url=${encodeBaseEncode}`);
    nodeCloneA2312(aTag, 'idoc在线__', `https://api.idocv.com/view/url?url=${encodeUri}`);
    nodeCloneA2312(aTag, 'office在线', `https://view.officeapps.live.com/op/view.aspx?src=${encodeUri}`);
    nodeCloneA2312(aTag, 'p_file在线', `http://www.pfile.com.cn/api/profile/onlinePreview?url=${encodeUri}`);
    nodeCloneA2312(aTag, 'x_doc在线_', `https://view.xdocin.com/view?src=${encodeUri}`);
  }

  function nodeCloneA2312(aTag, name, href) {
    const clonedNode = document.createElement('a');
    clonedNode.innerText = name;
    [clonedNode.style.color, clonedNode.style.fontWeight, clonedNode.style.marginRight] = ['blue', 'bold', '5px'];
    clonedNode.setAttribute("href", href);
    clonedNode.setAttribute("target", "_blank");
    aTag.parentNode.insertBefore(clonedNode, aTag); // 在父节点插入克隆的节点
  }
})();
