package in.gav.tetris.model

import indigo.*
import indigo.json.Json
import in.gav.tetris.assets.Assets

final case class StartUpData(block: Block)

object StartUpData:

  def setup(bootData: BootData, assetCollection: AssetCollection): Outcome[Startup[StartUpData]] =
    for {
      block <- startBlock(bootData, assetCollection)
      font <- startFont(bootData, assetCollection)
    } yield {
      Startup.Success(StartUpData(block), Set(), Set(font), Set())
    }

  private def startBlock(bootData: BootData, assetCollection: AssetCollection): Outcome[Block] =
    assetCollection.findImageDataByName(Assets.Block.Unit) match {
      case Some(value) =>
        val rect = Rectangle(Point.zero, Size(value.width, value.height))
        val sd = bootData.screenDimensions
        lazy val horizontalQuarter: Int = sd.x + (sd.width / 4)
        lazy val verticalQuarter: Int = sd.y + (sd.height / 4)
        lazy val quarter = Point(horizontalQuarter, verticalQuarter)
        val graphic = Graphic(rect, 1, Material.ImageEffects(Assets.Block.Unit))
          .withRef(rect.center)
          .moveTo(quarter.moveBy((-11 * value.width) / 2, (-21 * value.height) / 2))
        Outcome(Block(graphic))
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
