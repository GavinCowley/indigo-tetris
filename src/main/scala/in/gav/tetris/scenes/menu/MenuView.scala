package in.gav.tetris.scenes.menu

import in.gav.tetris.assets.Assets
import in.gav.tetris.model.{BlockGraphics, StartUpData}
import in.gav.tetris.scenes.loading.LoadingModel
import in.gav.tetris.scenes.play.model.{PieceAngle, PieceShape}
import indigo.*
import indigo.shared.events.MouseEvent.Click
import indigoextras.ui.*

final case class MenuView(startButton: Button, exitButton: Button):
  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[MenuView] = event match
    case Click(position) =>
      startButton.update(context.inputState.mouse)
      IndigoLogger.info(s"bounds: ${startButton.bounds}")
      IndigoLogger.info(s"mouse: ${context.inputState.mouse.position}")
      Outcome(this)
    case FrameTick =>
      for {
        start <- startButton.update(context.inputState.mouse)
        exit <- exitButton.update(context.inputState.mouse)
      } yield
        MenuView(start, exit)
    case _ =>
      Outcome(this)

  def draw(context: FrameContext[StartUpData]): Outcome[SceneUpdateFragment] = Outcome {
    SceneUpdateFragment(Layer(BindingKey("Menu"), startButton.draw, exitButton.draw))
  }

object MenuView:
  def init(startupData: StartUpData): MenuView = MenuView(startupData.buttonGraphics.start, startupData.buttonGraphics.exit)