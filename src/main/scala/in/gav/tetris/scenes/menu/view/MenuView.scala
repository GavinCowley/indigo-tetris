package in.gav.tetris.scenes.menu.view

import indigo.*
import indigo.shared.scenegraph.Graphic
import indigo.shared.scenegraph.Shape.Box
import in.gav.tetris.assets.Assets
import in.gav.tetris.model.{Block, StartUpData}
import in.gav.tetris.scenes.loading.model.LoadingModel
import in.gav.tetris.scenes.play.model.{PieceAngle, PieceShape}
import indigoextras.ui._

final case class MenuView():
  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[MenuView] =
    Outcome.Error(new RuntimeException("blach==h"))

  def draw(context: FrameContext[StartUpData]): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment.empty)
    //    SceneUpdateFragment(Layer(BindingKey("Menu"), ???))

object MenuView:
  val Init: MenuView = MenuView()
