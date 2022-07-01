package in.gav.tetris.scenes.menu

import indigo.*
import indigo.scenes.{Lens, Scene, SceneName}
import in.gav.tetris.model.{Model, StartUpData}
import in.gav.tetris.scenes.menu.view.MenuView
import in.gav.tetris.view.View

class MenuScene() extends Scene[StartUpData, Model, View] :
  override type SceneModel = Unit
  override type SceneViewModel = MenuView

  override def name: SceneName = MenuScene.Name

  override def modelLens: Lens[Model, Unit] = Lens.unit[Model]

  override def viewModelLens: Lens[View, MenuView] = Lens(_.menuView, (v, m) => v.copy(menuView = m))

  override def eventFilters: EventFilters = EventFilters.Restricted

  override def subSystems: Set[SubSystem] = Set.empty[SubSystem]

  override def updateModel(context: FrameContext[StartUpData], model: Unit): GlobalEvent => Outcome[Unit] =
    _ => Outcome(model)

  override def updateViewModel(context: FrameContext[StartUpData], model: Unit, viewModel: MenuView): GlobalEvent => Outcome[MenuView] =
    viewModel.update(context, _)

  override def present(context: FrameContext[StartUpData], model: Unit, viewModel: MenuView): Outcome[SceneUpdateFragment] =
    viewModel.draw(context)

object MenuScene:
  final val Name: SceneName = SceneName("Menu")
