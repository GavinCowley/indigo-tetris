package in.gav.tetris.scenes.loading

import in.gav.tetris.assets.Assets
import in.gav.tetris.model.StartUpData
import in.gav.tetris.scenes.menu.MenuScene
import in.gav.tetris.scenes.play.PlayScene
import indigo.*
import indigo.scenes.SceneEvent.JumpTo
import indigo.shared.events.MouseEvent.Click
import indigoextras.subsystems.AssetBundleLoaderEvent

case class LoadingModel(loaded: Boolean, countdown: Seconds):

  def update(context: FrameContext[StartUpData], event: GlobalEvent): Outcome[LoadingModel] = event match

    //    case Click(position) =>
    //      IndigoLogger.info(s"Clicked: ${position}, start loading")
    //      Outcome(this, List(AssetBundleLoaderEvent.Load(BindingKey("reloading"), Assets.bootAssets(""))))
    //
    case FrameTick =>
      if countdown <= context.delta then
        IndigoLogger.info("Loading complete")
        Outcome(LoadingModel(true, Seconds(0)), List(JumpTo(PlayScene.Name)))
      else Outcome(LoadingModel(false, countdown - context.delta))

    //    case AssetBundleLoaderEvent.Started(key) =>
    //      println("Load started! " + key.toString())
    //      Outcome(this)
    //
    //    case AssetBundleLoaderEvent.LoadProgress(key, percent, completed, total) =>
    //      println(s"In progress...: ${key.toString()} - ${percent.toString()}%, ${completed.toString()} of ${total.toString()}")
    //      Outcome(this)
    //
    //    case AssetBundleLoaderEvent.Success(key) =>
    //      println("Got it! " + key.toString())
    //      Outcome(copy(loaded = true), List(JumpTo(MenuScene.Name)))
    //
    //    case AssetBundleLoaderEvent.Failure(key, message) =>
    //      println(s"Lost it... '$message' " + key.toString())
    //      Outcome(this)

    case _ =>
      Outcome(this)

object LoadingModel:
  val Init: LoadingModel = LoadingModel(false, Seconds(10))
