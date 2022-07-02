package in.gav.tetris.model

import in.gav.tetris.scenes.play.PlayScene
import indigo.{AssetName, Depth, Graphic, Material, Point, RGBA, SceneNode, Size, IndigoLogger}
import indigoextras.ui.{Button, ButtonAssets}
import indigo.scenes.SceneEvent.JumpTo

final case class ButtonGraphics(graphic: Graphic[Material.ImageEffects]):

  private val up: Graphic[Material.ImageEffects] = graphic
  private val over: Graphic[Material.ImageEffects] = graphic.withMaterial(graphic.material.withTint(RGBA.SlateGray).withAlpha(1.25))
  private val bottom: Graphic[Material.ImageEffects] = graphic.withMaterial(graphic.material.withTint(RGBA.Silver).withAlpha(2))

  private val baseButton = Button(ButtonAssets(up, over, bottom), graphic.bounds, Depth(1))

  val start: Button = baseButton.withClickActions(JumpTo(PlayScene.Name))
  val exit: Button = baseButton.withClickActions(JumpTo(PlayScene.Name)).moveBy(0, 40)

/*

graphic.crop: Rectangle(Point(0,0),Size(20,20))
graphic.ref: Point(10,10)
graphic.position: Point(480,270)
graphic.bounds: Rectangle(Point(470,260),Size(20,20))
button.bounds: Rectangle(Point(470,260),Size(20,20))

top left 460/250 -> 480/270
*/
