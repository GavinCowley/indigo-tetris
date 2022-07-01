package in.gav.tetris.scenes.loading.view

import in.gav.tetris.assets.Assets
import in.gav.tetris.model.{Block, StartUpData}
import in.gav.tetris.scenes.loading.model.LoadingModel
import in.gav.tetris.scenes.play.model.{PieceAngle, PieceShape}
import indigo.*
import indigo.shared.scenegraph.Graphic
import indigo.shared.scenegraph.Shape.Box

final case class LoadingView(pieceShape: PieceShape, angle: PieceAngle, nextChange: Seconds):
  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[LoadingView] = event match
    case FrameTick if nextChange < context.delta =>
      Outcome(LoadingView.init)
    case FrameTick =>
      Outcome(copy(nextChange = nextChange - context.delta))
    case _ =>
      Outcome(this)

  def draw(context: FrameContext[StartUpData], model: LoadingModel): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment(
      Layer(BindingKey("Loading"), context.startUpData.block.graphicsForShape(pieceShape, angle))
    ))

object LoadingView:
  private val nextChange = Seconds(1)

  def init: LoadingView = LoadingView(PieceShape.newRandom, PieceAngle.`0`, nextChange)

  val Init: LoadingView = LoadingView(PieceShape.IShape, PieceAngle.`0`, nextChange)
