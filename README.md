BorderlessScene
=================

A simple, single file that can be dropped into any Java FX project and provides the functionality needed for an undecorated stage. The BorderlessScene allows you to fully customize the look of your application and automatically handles the resizing events for you.  A dropshadow is also added by default.


**1.** Resizing controls automatically added

**2.** Drop shadow support

**3.** Supports transparent windows without having the shadow bleed through

**4.** Maximize method implemented for you

**5.** You control which node takes drag events for moving the window


I would not have thought to use an AnchorPane for the resize controls had I not found this project: https://github.com/goxr3plus/FX-BorderlessScene


### Usage

``` Java
public void start(Stage stage) {
  BorderPane componentLayout = new BorderPane();
  BorderlessScene scene = new BorderlessScene(stage, componentLayout, 640, 480);
  
  scene.setDragControl(headerNode);
}
```


### I'm currently using this in my media application, Echo.

![First](https://i.imgur.com/qzjbzck.jpg)
