package in.gav.tetris.model

import indigo.*
import indigo.json.Json
import in.gav.tetris.assets.Assets

final case class StartUpData(blockGraphics: BlockGraphics, buttonGraphics: ButtonGraphics)

object StartUpData:

  def setup(bootData: BootData, assetCollection: AssetCollection): Outcome[Startup[StartUpData]] =
    for {
      block <- startBlock(bootData, assetCollection)
      font <- startFont(bootData, assetCollection)
      buttons <- startButton(bootData, assetCollection)
    } yield {
      Startup.Success(StartUpData(block, buttons), Set(), Set(font), Set())
    }

  private def startBlock(bootData: BootData, assetCollection: AssetCollection): Outcome[BlockGraphics] =
    assetCollection.findImageDataByName(Assets.Block.Unit) match {
      case Some(value) =>
        val rect = Rectangle(Point.zero, Size(value.width, value.height))
        val sd = bootData.screenDimensions
        lazy val horizontalQuarter: Int = sd.x + (sd.width / 4)
        lazy val verticalQuarter: Int = sd.y + (sd.height / 4)
        lazy val quarter = Point(horizontalQuarter, verticalQuarter)
        val graphic = Graphic(rect, 1, Material.ImageEffects(Assets.Block.Unit))
          .withRef(rect.center)
          .scaleBy(0.2, 0.2)
          .moveTo(quarter.moveBy((-11 * rect.width) / 10, (-21 * rect.height) / 10))
        IndigoLogger.info(s"rect: ${rect}")
        IndigoLogger.info(s"graphic.position: ${graphic.position}")
        IndigoLogger.info(s"graphic.scale: ${graphic.scale}")
        IndigoLogger.info(s"graphic.crop: ${graphic.crop}")

        Outcome(BlockGraphics(graphic))
      case None => Outcome.Error(new NoSuchElementException("No Block image found"))
    }

  private def startFont(bootData: BootData, assetCollection: AssetCollection): Outcome[FontInfo] =
    val maybeFontInfo: Option[FontInfo] =
      for {
        json <- assetCollection.findTextDataByName(Assets.Text.Font)
        chars <- Json.readFontToolJson(json)
        unknownChar <- chars.find(_.character == "?")
      } yield FontInfo(
        fontKey = Assets.Text.Key,
        fontSheetBounds = Size(450, 189 * 2),
        unknownChar = unknownChar,
        fontChars = chars,
        caseSensitive = true
      )

    maybeFontInfo match
      case Some(fontInfo) => Outcome(fontInfo)
      case None => Outcome.Error(new NoSuchElementException("Font error"))

  private def startButton(bootData: BootData, assetCollection: AssetCollection): Outcome[ButtonGraphics] =
    assetCollection.findImageDataByName(Assets.Button.Graphic) match
      case Some(value) =>
        val rect = Rectangle(Size(value.width, value.height))
        val graphic = Graphic(rect, Material.ImageEffects(Assets.Button.Graphic))
          .moveTo(bootData.screenDimensions.center)
          .scaleBy(4, 1)
        Outcome(ButtonGraphics(graphic))
      case None =>
        Outcome.Error(new NoSuchElementException("No Button image found"))
