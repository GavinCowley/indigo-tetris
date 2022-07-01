package in.gav.tetris

import in.gav.tetris.assets.Assets
import in.gav.tetris.model.{BootData, Model, StartUpData}
import in.gav.tetris.scenes.loading.LoadingScene
import in.gav.tetris.scenes.menu.MenuScene
import in.gav.tetris.scenes.play.PlayScene
import in.gav.tetris.view.View
import indigo.scenes.{Scene, SceneName}
import indigo.*
import indigoextras.subsystems.FPSCounter

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object Tetris extends IndigoGame[BootData, StartUpData, Model, View] :

  override def scenes(bootData: BootData): NonEmptyList[Scene[StartUpData, Model, View]] =
    NonEmptyList(LoadingScene(bootData.screenDimensions), MenuScene(), PlayScene())

  override def initialScene(bootData: BootData): Option[SceneName] = Some(LoadingScene.Name)

  override def eventFilters: EventFilters = EventFilters.BlockAll

  override def boot(flags: Map[String, String]): Outcome[BootResult[BootData]] = Outcome {
    val assetPath: String = flags.getOrElse("baseUrl", "")

    val config =
      GameConfig.default
        .withClearColor(RGBA.White)
        .withViewport(GameViewport.at1080p)

    BootResult(
      config,
      BootData(assetPath, config.screenDimensions),
      Set.empty,
      Assets.bootAssets(assetPath),
      Set.empty,
      Set(FPSCounter(Point(10, 10), FPS.Default, BindingKey("fps"))),
      Set.empty
    )
  }

  override def setup(bootData: BootData, assetCollection: AssetCollection, dice: Dice): Outcome[Startup[StartUpData]] =
    StartUpData.setup(bootData, assetCollection)

  override def initialModel(startupData: StartUpData): Outcome[Model] =
    Outcome(Model.Init)

  override def initialViewModel(startupData: StartUpData, model: Model): Outcome[View] =
    Outcome(View.Init)

  override def updateModel(context: FrameContext[StartUpData], model: Model): GlobalEvent => Outcome[Model] =
    _ => Outcome(model)

  override def updateViewModel(context: FrameContext[StartUpData], model: Model, viewModel: View): GlobalEvent => Outcome[View] =
    _ => Outcome(viewModel)

  override def present(context: FrameContext[StartUpData], model: Model, viewModel: View): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment.empty)
