# Game2048 Base on JavaFX

-

写这个JavaFX小程序，在于弥补java课程后半段抄作业犯下的错误

**在此我保证以后不再抄别人的代码**

-
程序的灵感来自于GitHub上的小游戏2048
> https://github.com/gabrielecirulli/2048

但是他是用js写的，我看不懂。在github上也找到了用javafx写的同类程序，然而我还是看不懂它的逻辑。知乎上看到了一些算法和牛人用很短的代码写的程序，可是不是很适用于我的思路

> 知乎上别人的算法 https://www.zhihu.com/question/23078049

所以，我就全部自己写了

## 另注
* **Eclipse里可能会出现中文乱码，不影响运行，看注释可以用其他编辑器打开**
* **核心代码在 *NumPane.class* 中**
* 移动方块和调用动画的方法为 `up();`` down(); ``left();`` right();` 在*NumPane.class* 第198行到第530行 具体方法可以看程序中的注释
* 判断游戏是否能继续的方法为 `gameManager(boolean isMoveLegal); ``isContinueable();`
* 计分器为*rankManager.class*

### 当前已知bug
我当然不会修复已存在的Bug啦
- 移动方块算法在计算方块的移动数量时会出错，所以调用的动画不是特别完美

## License
MIT