package in.gav.tetris.scenes.play.view

import indigo.*
import in.gav.tetris.assets.Assets
import in.gav.tetris.model.StartUpData
import in.gav.tetris.scenes.play.model.{Block, BlockState, Board, PieceShape, PlayModel}

case class PlayView():
  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[PlayView] = event match
    case _ =>
      Outcome(this)


  def draw(context: FrameContext[StartUpData], model: PlayModel): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment(
      Layer(BindingKey("Score"),
        Text("Hello, world!\nThis is some text!", Assets.Text.Key, Material.Bitmap(Assets.Text.Font))),
      Layer(BindingKey("Play"), drawBoard(context, model.board)).withMagnification(2),
      Layer(BindingKey("Background"), context.startUpData.block.boardEdge).withMagnification(2)
    ))

  // todo: preload materials with colours and ghosts
  def drawBoard(context: FrameContext[StartUpData], board: Board): List[Graphic[Material.ImageEffects]] =
    val block = context.startUpData.block
    board.grid.blocks.flatMap {
      case Block(point, BlockState.Active(shape)) =>
        List(block.atPoint(point.x, point.y)
          .withMaterial(block.graphic.material
            .withTint(rgbaFromShape(shape))))
      case Block(point, BlockState.Ghost(shape)) =>
        List(block.atPoint(point.x, point.y)
          .withMaterial(block.graphic.material
            .withTint(rgbaFromShape(shape))
            .withAlpha(0.5)))
      case Block(point, BlockState.Solid(shape)) =>
        List(block.atPoint(point.x, point.y)
          .withMaterial(block.graphic.material
            .withTint(rgbaFromShape(shape))))
      case _ =>
        Nil
    }.toList

  def rgbaFromShape(shape: Option[PieceShape]): RGBA = shape.fold(RGBA.White) {
    case PieceShape.IShape => RGBA.Cyan
    case PieceShape.LShape => RGBA.Blue
    case PieceShape.JShape => RGBA.Orange
    case PieceShape.OShape => RGBA.Yellow
    case PieceShape.TShape => RGBA.Purple
    case PieceShape.SShape => RGBA.Green
    case PieceShape.ZShape => RGBA.Red
  }

object PlayView:
  val Init: PlayView = PlayView()