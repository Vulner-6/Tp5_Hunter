前言
===============================
这里采用的是单线程同步验证。在其他的项目里，我又重写了这里的漏洞验证程序，实现了多线程异步验证漏洞。极大的的提高了扫描效率。

功能介绍
===============================
一、帮助信息
--------------
![帮助界面截图](https://github.com/Vulner-6/Tp5_Hunter/raw/master/Resources/1_help.png)<br>

二、fofa联动
--------------
支持fofa，但是需要fofa账号和API_KEY
![fofa使用截图1](https://github.com/Vulner-6/Tp5_Hunter/raw/master/Resources/2_fofa.png)<br>
![fofa使用截图2](https://github.com/Vulner-6/Tp5_Hunter/raw/master/Resources/3_fofa.png)<br>
![fofa使用截图2](https://github.com/Vulner-6/Tp5_Hunter/raw/master/Resources/6_fofaResults.png)<br>

三、代理功能
--------------
支持socks代理，这里使用的是本地SSR代理：<br>
![ssr代理使用截图1](https://github.com/Vulner-6/Tp5_Hunter/raw/master/Resources/4_ssr.png)<br>
![ssr代理使用截图2](https://github.com/Vulner-6/Tp5_Hunter/raw/master/Resources/5_ssr.png)<br>

四、txt读取url批量验证
-------------------
支持读取本地txt文件，对txt文件中的网站进行批量验证。截图和fofa扫描的截图差不多。<br>
因此，这里就不上图了。README.md文档写起来不方便，不想写了。

注意事项
===============================
1.url填入最好以标准格式【协议+域名+端口】填入，否则可能会出现意料之外的情况。<br>
----例如https协议的网站`https://a.com`，你填写`http://a.com`，那么必然会提示网站访问异常。<br>
2.批量扫描后，会生成结果txt文件，如果需要备份，请手动备份，否则下次新的扫描结果将覆盖旧的扫描结果。<br>
3.有的网站可能初次扫描时提示有漏洞，第二次发现无法访问，那是因为IP被封了，不是工具的原因。<br>
3.如果觉得好用，就点亮星星吧。如有漏洞，可以留言，我会酌情进行修改。<br>

------------------------------------------------
2019年11月3日更新：
---------------
1.一处提示文档有点废话，删除了。

