package in.gav.tetris.scenes.menu

import indigo.*
import indigo.scenes.{Lens, Scene, SceneName}
import in.gav.tetris.model.{Model, StartUpData}
import in.gav.tetris.view.View

class MenuScene() extends Scene[StartUpData, Model, View] :
  override type SceneModel = MenuModel
  override type SceneViewModel = MenuView

  override def name: SceneName = MenuScene.Name

  override def modelLens: Lens[Model, MenuModel] = Lens(_.menuModel, (m, mm) => m.copy(menuModel = mm))

  override def viewModelLens: Lens[View, MenuView] = Lens(_.menuView, (v, m) => v.copy(menuView = m))

  override def eventFilters: EventFilters = EventFilters.Restricted

  override def subSystems: Set[SubSystem] = Set.empty[SubSystem]

  override def updateModel(context: FrameContext[StartUpData], model: MenuModel): GlobalEvent => Outcome[MenuModel] =
    _ => Outcome(model)

  override def updateViewModel(context: FrameContext[StartUpData], model: MenuModel, viewModel: MenuView): GlobalEvent => Outcome[MenuView] =
    viewModel.update(context, _)

  override def present(context: FrameContext[StartUpData], model: MenuModel, viewModel: MenuView): Outcome[SceneUpdateFragment] =
    viewModel.draw(context)

object MenuScene:
  final val Name: SceneName = SceneName("Menu")
