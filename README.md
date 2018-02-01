# 毕业设计：基于java的日志监控分析工具的设计与实现
### 致即将大学毕业的孩子们

毕业季就要到了，虽然已毕业，但还是挂念着未毕业的学弟学妹们，眼看着就到要交毕业设计的时节，作为学长非常懂得你们此刻的心情，你们的毕设肯定还八字没一撇呢吧？

我闲来无事，翻了翻电脑里的文档，发现当年毕业设计相关的论文、答辩ppt等材料还都保留着。想了一想，既然放着也是放着，不如传上来，也许能对学弟学妹们有所帮助。

这个项目就是题目所描述的：基于java的日志监控分析工具的设计与实现。这个题目是当年在公司实习的时候，在导师的催促下拍脑门定的。在开始设计的时候我就后悔了，后悔为啥不找个能随便搭一搭环境、用一用框架就能完成的题目......不过后悔也没用了，就硬着头皮用当时仅剩的那点儿校园时光开发完了这个项目。

在设计上借鉴了flume里的source、channel、sink等模型，完成了日志的收集、上报、持久化等功能，算是flume的简陋版吧。额外还有一些web端展示的功能。当时确实能力有限、时间有限，构思的时候很丰满，实现的时候很骨感。

不管怎么样，[Hawkeye](https://github.com/LittleLory/Hawkeye)，这里就是我当时的全部心血了，学弟学妹们收好，不谢！

### 相关代码

* [client](https://github.com/LittleLory/Hawkeye/tree/master/client)：日志收集客户端
* [front](https://github.com/LittleLory/Hawkeye/tree/master/front)：日志查询的web页面
* [remote](https://github.com/LittleLory/Hawkeye/tree/master/remote)：Remote组件
* [router](https://github.com/LittleLory/Hawkeye/tree/master/router)：Router组件
* [server](https://github.com/LittleLory/Hawkeye/tree/master/server)：日志收集服务端
* [web](https://github.com/LittleLory/Hawkeye/tree/master/web)：日志查询的web服务

> PS：
> 1. 代码的具体作用在论文里有介绍。
> 2. 当时没有考虑并发问题，小心有坑。

### 相关文档

文档在[document](https://github.com/LittleLory/Hawkeye/tree/master/document)中，这里包含了：

* [论文]([document](https://github.com/LittleLory/Hawkeye/tree/master/document/基于java的日志监控分析工具的设计与实现.docx))：这就是毕业论文了。
* [程序包](https://github.com/LittleLory/Hawkeye/tree/master/document/程序包)：我不敢保证能顺利运行，当然了，当时是没有问题的。
* [答辩PPT](https://github.com/LittleLory/Hawkeye/tree/master/document/答辩ppt.ppt)：如果你是一个有审美追求的人，就忽略它。
* [ElasticSearch索引](https://github.com/LittleLory/Hawkeye/tree/master/document/ElasticSearch索引.txt)：如名字。
* [mysql数据库表](https://github.com/LittleLory/Hawkeye/tree/master/document/mysql数据库表)：如名字。

### 对学弟学妹的忠告

1. 可以参考，可以借鉴，但最好不要copy，自己完成会很有成就感。
2. 珍惜剩余的校园时光。
3. 毕业快乐。