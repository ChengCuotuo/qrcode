## 生成二维码

写的一个小工具，用来生成二维码，调用的是谷歌的 zxing 

工具支持单个生成和批量生成，批量生成允许从 excel 文件中读取和从mysql 数据库中读取。

生成的图片的格式为 jpg

运行的速度收到电脑处理速度，处理的数据量，content 数据的复杂度，如果从数据库中读取数据还受到网速的影响，使用学习记录里面的 excel 测试数据 1000 条数据 content 长度为 20 字符串，耗时 一分半。

#### 从 excel 文件中读取

使用 POI 进行读取

文件需要有三列数据，第一列为 id 列为 content 也就是带生成二维码的数据，第三列为 name 给二维码文件命名。_（默认从第二行开始读取数据，第一行留作名称；读取前三列进行操作，列名称没有限定，id 列允许为空）_

文件扩展名为 xls 也就是 excel 的 07 版本之前的高版本的 excel 可以通过另存为，生成 xls 文件。

####从数据库中读取

使用 JDBC 做数据库信息操作

只支持从 MySQL  数据库中读取数据，在正确连接之后选择对应的表以及content 字段和 name 字段就可以了。_（字段名称并没有限定）_



#### 文件介绍

exe 文件夹是使用 idea 将 javafx 项目生成的 exe 文件，window 下点击 qrcode_gui.exe 就可执行

qrcode_gui 是源代码

学习记录 有设计的思路，ppt 画的 ui ，学习记的笔记，还有一个 excel 测试数据



#### 技术点

* JavaFX
* ZXing
* poi （使用的是之前写的模板 https://github.com/ChengCuotuo/Poi/tree/master/src/main/java/cn/nianzuochen/reportform/util  工具类中可以找到，将读取文件的方式从 MultipartFile改为 file） 
* JDBC

 

贴张图：

![hello](C:\Users\-pc\Desktop\qrcode\qrcode\hello.png)